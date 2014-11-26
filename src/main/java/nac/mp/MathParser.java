/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nac.mp.ast.BasicScope;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.ast.WhereBlock;
import nac.mp.ast.expression.Assignment;
import nac.mp.ast.expression.Block;
import nac.mp.ast.expression.BooleanLiteral;
import nac.mp.ast.expression.EqualExpr;
import nac.mp.ast.expression.FloatLiteral;
import nac.mp.ast.expression.FunctionDeclExpr;
import nac.mp.ast.expression.FunctionExpr;
import nac.mp.ast.expression.FunctionOptsExpr;
import nac.mp.ast.expression.IntLiteral;
import nac.mp.ast.expression.LessThan;
import nac.mp.ast.expression.LessThanEqual;
import nac.mp.ast.expression.ListExpr;
import nac.mp.ast.expression.ListLiteralExpr;
import nac.mp.ast.expression.LogicalAnd;
import nac.mp.ast.expression.LogicalOr;
import nac.mp.ast.expression.MemberExpr;
import nac.mp.ast.expression.MethodExpr;
import nac.mp.ast.expression.MethodOptsExpr;
import nac.mp.ast.expression.MinusExpression;
import nac.mp.ast.expression.MoreThan;
import nac.mp.ast.expression.MoreThanEqual;
import nac.mp.ast.expression.NewExpr;
import nac.mp.ast.expression.NewOptsExpr;
import nac.mp.ast.expression.NotEqualExpr;
import nac.mp.ast.expression.ObjectDeclExpr;
import nac.mp.ast.expression.Parenthesis;
import nac.mp.ast.expression.PlusExpression;
import nac.mp.ast.expression.SelectExpression;
import nac.mp.ast.expression.SlashExpression;
import nac.mp.ast.expression.StarExpression;
import nac.mp.ast.expression.StringLiteral;
import nac.mp.ast.expression.VarExpr;
import nac.mp.ast.statement.Assert;
import nac.mp.ast.statement.AttributeDecl;
import nac.mp.ast.statement.Exit;
import nac.mp.ast.statement.FunctionDecl;
import nac.mp.ast.statement.IfStatement;
import nac.mp.ast.statement.Input;
import nac.mp.ast.statement.ModelDecl;
import nac.mp.ast.statement.ObjectDecl;
import nac.mp.ast.statement.OneToManyDecl;
import nac.mp.ast.statement.Print;
import nac.mp.ast.statement.Return;
import nac.mp.ast.statement.Save;
import nac.mp.ast.statement.TemplateDecl;
import nac.mp.ast.statement.VarDecl;
import nac.mp.ast.statement.WhileStatement;
import nac.mp.store.frostbyte.FrostByte;
import nac.mp.type.MPModel;
import nac.mp.type.MPObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TODO: assoc Token to AST nodes to improve debug TODO: TODO: review use of
 * expression(); TODO: separate expression per type TODO: escape quotes TODO:
 * file name in exceptions
 */
public class MathParser {

  private static final Logger log = LogManager.getLogger(MathParser.class);
  private final Tokenizer tokenizer = new Tokenizer();
  private final Scope globalScope = new BasicScope(null);
  private final Block fileBlock = new Block();
  private Token current = null;
  private Token next = null;
  private final ObjectStore objectStore = new FrostByte();
  private final Deque<List<Token>> tokenStack = new ArrayDeque<>();

  public void model(String path) throws ParseException, EvalException {
    model(new File(path));
  }

  public void model(Path path) throws ParseException, EvalException {
    model(path.toFile());
  }

  public void model(File path) throws ParseException, EvalException {
    startRecord();
    log.info("Parsing model " + path);
    tokenizer.setCurrentFile(path);

    next();
    while (next.type != TokenType.EOF) {
      switch (next.type) {
        case KW_MODEL:
          fileBlock.addStatement(modelDecl());
          break;
        case KW_REL:
          fileBlock.addStatement(relDecl());
          break;
        default:
          consume(TokenType.EOF);
          break;
      }
      next();
    }
    endRecord(null);
    //eval
    try {
      fileBlock.eval(globalScope, objectStore);
    } catch (EvalException ee) {
      ee.setTokenizer(tokenizer);
      throw ee;
    }

  }

  public void control(String path) throws ParseException, EvalException {
    control(new File(path));
  }

  public void control(Path path) throws ParseException, EvalException {
    control(path.toFile());
  }

  public void control(File path) throws ParseException, EvalException {
    startRecord();
    log.info("Parsing control " + path);
    tokenizer.setCurrentFile(path);

    for (MPModel model : objectStore.getModels()) {
      globalScope.declareVar(model.getName(), model);
    }

    next();
    while (next.type != TokenType.EOF) {
      fileBlock.addStatement(statement());
      next();
    }
    consume(TokenType.EOF);

    endRecord(null);
    //eval
    try {
      fileBlock.eval(globalScope, objectStore);
    } catch (EvalException ee) {
      ee.setTokenizer(tokenizer);
      throw ee;
    }
  }

  public MPObject getGlobal(String key) {
    return globalScope.getVar(key, objectStore);
  }

  public Tokenizer getTokenizer() {
    return tokenizer;
  }

  private void consume() throws ParseException {
    current = tokenizer.consume();
    List<Token> list = tokenStack.peekFirst();
    list.add(current);
  }

  private void next() throws ParseException {
    next = tokenizer.lookahead(1);
  }

  private void next(int l) throws ParseException {
    next = tokenizer.lookahead(l);
  }

  private void consume(TokenType t) throws ParseException {
    Token e = tokenizer.lookahead(1);
    if (e.type == t) {
      current = tokenizer.consume();
      List<Token> list = tokenStack.peekFirst();
      list.add(current);
    } else {
      throw new ParseException("Unexpected token", tokenizer, t, e);
    }
  }

  private Expression block() throws ParseException {
    startRecord();
    Block bl = new Block();
    next();
    if (next.type == TokenType.LBRACE) {
      consume();
      next();
      while (next.type != TokenType.RBRACE) {
        bl.addStatement(statement());
        next();
      }
      consume();
    } else {
      bl.addStatement(statement());
    }
    return endRecord(bl);
  }

  private Expression statement() throws ParseException {
    startRecord();
    Expression result = null;
    next();
    switch (next.type) {
      case KW_SAVE:
        consume();
        Expression sve = expression();
        consume(TokenType.SEMICOLON);
        result = new Save(sve);
        break;
      case KW_PRINT:
        consume();
        Expression ex1 = expression();
        consume(TokenType.SEMICOLON);
        result = new Print(ex1);
        break;
      case KW_INPUT:
        consume();
        consume(TokenType.IDENTIFIER);
        Input input = new Input(current.text);
        consume(TokenType.SEMICOLON);
        result = input;
        break;
      case KW_EXIT:
        consume();
        consume(TokenType.SEMICOLON);
        result = new Exit();
        break;
      case KW_ASSERT:
        consume();
        Expression ex2 = expression();
        consume(TokenType.SEMICOLON);
        result = new Assert(ex2);
        break;
      case KW_RETURN:
        consume();
        Expression ex = null;
        next();
        if (next.type != TokenType.SEMICOLON) {
          ex = expression();
        }
        consume(TokenType.SEMICOLON);
        result = new Return(ex);
        break;
      case KW_IF:
        consume();
        consume(TokenType.LPAREN);
        Expression ifCond = expression();
        consume(TokenType.RPAREN);
        Expression ifBody = block();
        IfStatement ifs = new IfStatement(ifCond, ifBody);
        next();
        if (next.type == TokenType.KW_ELSE) {
          consume();
          Expression elseBody = block();
          ifs.setElseBody(elseBody);
        }
        result = ifs;
        break;
      case KW_WHILE:
        consume();
        consume(TokenType.LPAREN);
        Expression cond = expression();
        consume(TokenType.RPAREN);
        Expression body = block();
        result = new WhileStatement(cond, body);
        break;
      case KW_VAR:
        result = varDecl();
        break;
      case KW_FUNC:
        result = funcDecl();
        break;
      case KW_OBJECT:
        result = objectDecl();
        break;
      case KW_TEMPLATE:
        result = classDecl();
        break;
      default:
        Expression se = expression();
        consume(TokenType.SEMICOLON);
        result = se;
    }
    return endRecord(result);
  }

  private Expression declaration() throws ParseException {
    startRecord();
    Expression result = null;
    next();
    switch (next.type) {
      case KW_VAR:
        result = varDecl();
        break;
      case KW_FUNC:
        result = funcDecl();
        break;
      case KW_OBJECT:
        result = objectDecl();
        break;
      case KW_TEMPLATE:
        result = classDecl();
        break;
      default:
        throw new ParseException("Declaration expected", tokenizer, next);
    }
    return endRecord(result);
  }

  private Expression attributeDecl() throws ParseException {
    startRecord();
    consume(TokenType.IDENTIFIER);
    String t = current.text;
    String mt = null;
    next();
    if (next.type == TokenType.COLON) {
      consume(TokenType.COLON);
      consume(TokenType.IDENTIFIER);
      mt = current.text;
    }
    consume(TokenType.IDENTIFIER);
    String i = current.text;
    AttributeDecl typedDecl = new AttributeDecl(t, mt, i);
    consume(TokenType.SEMICOLON);
    return endRecord(typedDecl);
  }

  private Expression varDecl() throws ParseException {
    startRecord();
    consume(TokenType.KW_VAR);
    consume(TokenType.IDENTIFIER);
    VarDecl varDecl = new VarDecl(current.text);
    next();
    if (next.type == TokenType.ASSIGN) {
      consume();
      varDecl.setDefaultValue(expression());
    }
    consume(TokenType.SEMICOLON);
    return endRecord(varDecl);
  }

  private Expression funcDecl() throws ParseException {
    startRecord();
    consume(TokenType.KW_FUNC);
    consume(TokenType.IDENTIFIER);
    FunctionDecl fd = new FunctionDecl(current.text);
    consume(TokenType.LPAREN);
    next();
    while (next.type != TokenType.RPAREN) {
      consume(TokenType.IDENTIFIER);
      fd.getArgNames().add(current.text);
      next();
      if (next.type == TokenType.RPAREN) {
        break;
      } else {
        consume(TokenType.COMMA);
      }
    }
    consume();
    Expression b = block();
    fd.setBody(b);
    return endRecord(fd);
  }

  private Expression objectDecl() throws ParseException {
    startRecord();
    consume(TokenType.KW_OBJECT);
    consume(TokenType.IDENTIFIER);
    ObjectDecl od = new ObjectDecl(current.text);
    consume(TokenType.LBRACE);
    next();
    while (next.type != TokenType.RBRACE) {
      od.getExpressions().add(declaration());
      next();
    }
    consume();
    return endRecord(od);
  }

  private Expression classDecl() throws ParseException {
    startRecord();
    consume(TokenType.KW_TEMPLATE);
    consume(TokenType.IDENTIFIER);
    String cl = current.text;
    next();
    Expression extExp = null;
    if (next.type == TokenType.KW_EXTENDS) {
      consume();
      extExp = expression();
    }
    TemplateDecl td = new TemplateDecl(extExp, cl);
    consume(TokenType.LBRACE);
    next();
    while (next.type != TokenType.RBRACE) {
      td.getDeclarations().add(declaration());
      next();
    }
    consume();
    return endRecord(td);
  }

  private Expression modelDecl() throws ParseException {
    startRecord();
    consume(TokenType.KW_MODEL);
    consume(TokenType.IDENTIFIER);
    String m = current.text;
    ModelDecl md = new ModelDecl(m);
    consume(TokenType.LBRACE);
    next();
    while (next.type != TokenType.RBRACE) {
      md.getAttributes().add(attributeDecl());
      next();
    }
    consume();
    return endRecord(md);
  }

  private Expression relDecl() throws ParseException {
    consume(TokenType.KW_REL);
    Expression left = expression();
    switch (next.type) {
      case ONE_TO_MANY:
        return oneToMany(left);
    }
    return null;
  }

  private Expression oneToMany(Expression left) throws ParseException {
    startRecord();
    consume(TokenType.ONE_TO_MANY);
    OneToManyDecl otm = new OneToManyDecl(left, expression());
    consume(TokenType.SEMICOLON);
    return endRecord(otm);
  }

  private Expression expression() throws ParseException {
    startRecord();
    Expression left = andor();
    while (true) {
      next();
      switch (next.type) {
        case ASSIGN:
          consume();
          Assignment as = new Assignment();
          as.setLeftValue(left);
          as.setRightValue(andor());
          left = as;
          break;
        default:
          return endRecord(left);
      }
    }
  }

  private Expression andor() throws ParseException {
    startRecord();
    Expression left = comparison();
    while (true) {
      next();
      switch (next.type) {
        case LOGICAL_AND:
          consume();
          LogicalAnd la = new LogicalAnd();
          la.setLeft(left);
          la.setRight(comparison());
          left = la;
          break;
        case LOGICAL_OR:
          consume();
          LogicalOr lo = new LogicalOr();
          lo.setLeft(left);
          lo.setRight(comparison());
          left = lo;
          break;
        default:
          return endRecord(left);
      }
    }
  }

  private Expression comparison() throws ParseException {
    startRecord();
    Expression left = additive();
    while (true) {
      next();
      switch (next.type) {
        case LT:
          consume();
          LessThan lt = new LessThan();
          lt.setLeft(left);
          lt.setRight(additive());
          left = lt;
          break;
        case LTE:
          consume();
          LessThanEqual lte = new LessThanEqual();
          lte.setLeft(left);
          lte.setRight(additive());
          left = lte;
          break;
        case MT:
          consume();
          MoreThan mt = new MoreThan();
          mt.setLeft(left);
          mt.setRight(additive());
          left = mt;
          break;
        case MTE:
          consume();
          MoreThanEqual mte = new MoreThanEqual();
          mte.setLeft(left);
          mte.setRight(additive());
          left = mte;
          break;
        case EQUAL:
          consume();
          EqualExpr eq = new EqualExpr();
          eq.setLeft(left);
          eq.setRight(additive());
          left = eq;
          break;
        case NOT_EQUAL:
          consume();
          NotEqualExpr neq = new NotEqualExpr();
          neq.setLeft(left);
          neq.setRight(additive());
          left = neq;
          break;
        default:
          return endRecord(left);
      }
    }
  }

  private Expression additive() throws ParseException {
    startRecord();
    Expression left = multiplicative();
    while (true) {
      next();
      switch (next.type) {
        case PLUS:
          consume();
          PlusExpression pl = new PlusExpression();
          pl.setLeft(left);
          pl.setRight(multiplicative());
          left = pl;
          break;
        case MINUS:
          consume();
          MinusExpression dsh = new MinusExpression();
          dsh.setLeft(left);
          dsh.setRight(multiplicative());
          left = dsh;
          break;
        default:
          return endRecord(left);
      }
    }
  }

  private Expression multiplicative() throws ParseException {
    startRecord();
    Expression left = creation();
    while (true) {
      next();
      switch (next.type) {
        case STAR:
          consume();
          StarExpression srt = new StarExpression();
          srt.setLeft(left);
          srt.setRight(creation());
          left = srt;
          break;
        case SLASH:
          consume();
          SlashExpression slt = new SlashExpression();
          slt.setLeft(left);
          slt.setRight(creation());
          left = slt;
          break;
        default:
          return endRecord(left);
      }
    }
  }

  private Expression creation() throws ParseException {
    startRecord();
    next();
    if (next.type == TokenType.KW_NEW) {
      consume();
      Expression ex = access();

      consume(TokenType.LPAREN);
      List<Expression> expList = new ArrayList<>();
      Map<String, Expression> optsMap = new HashMap<>();

      argsProc(expList, optsMap);

      if (optsMap.size() > 0) {
        NewOptsExpr nex1 = new NewOptsExpr(ex);
        nex1.getArgs().addAll(expList);
        nex1.getOpts().putAll(optsMap);
        return endRecord(nex1);
      } else {
        NewExpr nex2 = new NewExpr(ex);
        nex2.getArgs().addAll(expList);
        return endRecord(nex2);
      }
    } else {
      Expression left = access();
      while (true) {
        next();
        switch (next.type) {
          case LPAREN:
            consume();
            List<Expression> expList = new ArrayList<>();
            Map<String, Expression> optsMap = new HashMap<>();

            argsProc(expList, optsMap);

            if (optsMap.size() > 0) {
              if (left instanceof MemberExpr) {
                MethodOptsExpr mex1 = new MethodOptsExpr((Expression) left);
                mex1.getArgs().addAll(expList);
                mex1.getOpts().putAll(optsMap);
                left = mex1;
              } else {
                FunctionOptsExpr fex1 = new FunctionOptsExpr(left);
                fex1.getArgs().addAll(expList);
                fex1.getOpts().putAll(optsMap);
                left = fex1;
              }
            } else {
              if (left instanceof MemberExpr) {
                MethodExpr mex2 = new MethodExpr((MemberExpr) left);
                mex2.getArgs().addAll(expList);
                left = mex2;
              } else {
                FunctionExpr fex2 = new FunctionExpr(left);
                fex2.getArgs().addAll(expList);
                left = fex2;
              }
            }
            break;
          default:
            return endRecord(left);
        }
      }
    }
  }

  private void argsProc(List<Expression> expList, Map<String, Expression> optsMap) throws ParseException {

    next();
    if (next.type != TokenType.RPAREN && next.type != TokenType.SEMICOLON) {
      while (true) {
        expList.add(expression());
        next();
        if (next.type == TokenType.COMMA) {
          consume(TokenType.COMMA);
        } else {
          break;
        }
      }
    }

    if (next.type == TokenType.SEMICOLON) {
      consume();
      while (true) {
        consume(TokenType.IDENTIFIER);
        String optName = current.text;
        consume(TokenType.COLON);
        optsMap.put(optName, expression());
        next();
        if (next.type == TokenType.COMMA) {
          consume();
        } else {
          break;
        }
      }
    }

    consume(TokenType.RPAREN);
  }

  private Expression access() throws ParseException {
    startRecord();
    Expression left = factor();
    while (true) {
      next();
      switch (next.type) {
        case DOT:
          consume();
          consume(TokenType.IDENTIFIER);
          String name = current.text;
          MemberExpr mex = new MemberExpr(left, name);
          left = mex;
          break;
        case LBRACKET:
          consume();
          Expression indExp = expression();
          consume(TokenType.RBRACKET);
          ListExpr lst = new ListExpr(left);
          lst.setIndex(indExp);
          left = lst;
          break;
        default:
          return endRecord(left);
      }
    }
  }

  private Expression factor() throws ParseException {
    startRecord();
    Expression result = null;
    next();
    switch (next.type) {
      case FLOAT:
        consume();
        result = new FloatLiteral(Double.parseDouble(current.text));
        break;
      case INT:
        consume();
        result = new IntLiteral(Long.parseLong(current.text));
        break;
      case STRING:
        consume();
        String text = current.text.replaceAll("^\"|\"$", "");
        result = new StringLiteral(text);
        break;
      case KW_TRUE:
        consume();
        result = new BooleanLiteral(true);
        break;
      case KW_FALSE:
        consume();
        result = new BooleanLiteral(false);
        break;
      case LPAREN:
        consume();
        Expression exp = expression();
        consume(TokenType.RPAREN);
        result = new Parenthesis(exp);
        break;
      case IDENTIFIER:
        consume();
        result = new VarExpr(current.text);
        break;
      case KW_FUNC:
        consume();
        FunctionDeclExpr fdx = new FunctionDeclExpr();
        consume(TokenType.LPAREN);
        next();
        while (next.type != TokenType.RPAREN) {
          consume(TokenType.IDENTIFIER);
          fdx.getArgNames().add(current.text);
          next();
          if (next.type == TokenType.RPAREN) {
            break;
          } else {
            consume(TokenType.COMMA);
          }
        }
        consume();
        Expression b = block();
        fdx.setBody(b);
        result = fdx;
        break;
      case KW_OBJECT:
        consume();
        ObjectDeclExpr od = new ObjectDeclExpr();
        consume(TokenType.LBRACE);
        next();
        while (next.type != TokenType.RBRACE) {
          od.getExpressions().add(declaration());
          next();
        }
        consume();
        result = od;
        break;
      case LBRACKET:
        consume();
        ListLiteralExpr le = new ListLiteralExpr();
        next();
        //TODO optimize here the while
        while (next.type != TokenType.RBRACKET) {
          le.getElems().add(expression());
          next();
          if (next.type == TokenType.RBRACKET) {
            break;
          } else {
            consume(TokenType.COMMA);
          }
        }
        consume();
        next();
        if (next.type == TokenType.LPAREN) {
          consume();
          le.setInitSize(expression());
          consume(TokenType.RPAREN);
        }
        result = le;
        break;
      case KW_SELECT:
        consume(TokenType.KW_SELECT);
        Expression modelName = expression();
        consume(TokenType.KW_WHERE);
        Expression wb = whereBlock();
        result = new SelectExpression(modelName, wb);
        break;
      default:
        throw new ParseException("Expression expected", tokenizer, next);
    }
    return endRecord(result);
  }

  private Expression whereBlock() throws ParseException {
    startRecord();
    WhereBlock bl = new WhereBlock();
    next();
    if (next.type == TokenType.LBRACE) {
      consume();
      next();
      while (next.type != TokenType.RBRACE) {
        bl.addStatement(statement());
        next();
      }
      consume();
    } else {
      bl.addStatement(statement());
    }
    return endRecord(bl);
  }

  public void cleanup() {
    objectStore.close();
  }

  //debug why so many stack;
  public void startRecord() {
    tokenStack.addFirst(new ArrayList<Token>());
  }

  public Expression endRecord(Expression expression) {
    List<Token> list = tokenStack.removeFirst();

    if (expression != null && !list.isEmpty()) {
      expression.relateTokens(list);
    }

    return expression;
  }

  public static void main(String[] args) {
    MathParser mp = new MathParser();
    try {
      mp.control("src/main/resources/mp/test.mp");
    } catch (EvalException | ParseException ex) {
      log.error("Parse/Eval failed", ex);
    } finally {
      mp.cleanup();
    }

  }

}
