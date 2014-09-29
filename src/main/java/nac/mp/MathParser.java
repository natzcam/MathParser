/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import nac.mp.ast.statement.FunctionDecl;
import nac.mp.ast.expression.FloatLiteral;
import nac.mp.ast.expression.Parenthesis;
import nac.mp.ast.expression.VarExpr;
import nac.mp.ast.Block;
import nac.mp.ast.Expression;
import nac.mp.ast.expression.BooleanLiteral;
import nac.mp.ast.expression.MemberExpr;
import nac.mp.ast.expression.EqualExpr;
import nac.mp.ast.expression.MinusExpression;
import nac.mp.ast.expression.FunctionDeclExpr;
import nac.mp.ast.expression.FunctionExpr;
import nac.mp.ast.expression.FunctionOptsExpr;
import nac.mp.ast.statement.Exit;
import nac.mp.ast.statement.IfStatement;
import nac.mp.ast.statement.Input;
import nac.mp.ast.expression.IntLiteral;
import nac.mp.ast.expression.LessThan;
import nac.mp.ast.expression.LessThanEqual;
import nac.mp.ast.expression.ListLiteralExpr;
import nac.mp.ast.expression.MoreThan;
import nac.mp.ast.expression.MoreThanEqual;
import nac.mp.ast.expression.NotEqualExpr;
import nac.mp.ast.expression.ObjectDeclExpr;
import nac.mp.ast.expression.PlusExpression;
import nac.mp.ast.expression.SlashExpression;
import nac.mp.ast.statement.Print;
import nac.mp.ast.statement.Return;
import nac.mp.ast.expression.StarExpression;
import nac.mp.ast.expression.StringLiteral;
import nac.mp.ast.statement.Assert;
import nac.mp.ast.expression.Assignment;
import nac.mp.ast.expression.ListExpr;
import nac.mp.ast.expression.MethodExpr;
import nac.mp.ast.expression.MethodOptsExpr;
import nac.mp.ast.expression.NewExpr;
import nac.mp.ast.expression.NewOptsExpr;
import nac.mp.ast.statement.ClassDecl;
import nac.mp.ast.statement.ObjectDecl;
import nac.mp.ast.statement.PersistStmt;
import nac.mp.ast.statement.RestoreStmt;
import nac.mp.ast.statement.VarDecl;
import nac.mp.ast.statement.WhileStatement;
import nac.mp.store.mapdb.ObjectStorage;

/**
 * concat strings TODO: assoc Token to AST nodes to improve debug
 *
 * @author natz TODO: remove while(true) TODO: use switch;
 */
public class MathParser {

  private final Tokenizer tokenizer = new Tokenizer();
  private final Scanner scanner = new Scanner(System.in);
  private final Scope global = new BasicScope(null);
  private final Block fileBlock = new Block();
  private Token current = null;
  private Token next = null;
  private final ObjectStorage objectStore = new ObjectStorage();

  public void eval(String input) throws ParseException, EvalException {
    tokenizer.process(input);
    next();
    while (next.type != TokenType.EOF) {
      fileBlock.addStatement(statement());
      next();
    }
    fileBlock.eval(global);
    objectStore.close();
  }

  public Tokenizer getTokenizer() {
    return tokenizer;
  }

  private void consume() throws ParseException {
    current = tokenizer.consume();
  }

  private void next() throws ParseException {
    next = tokenizer.lookahead(1);
  }

  private void consume(TokenType t) throws ParseException {
    Token e = tokenizer.lookahead(1);
    if (e.type == t) {
      current = tokenizer.consume();
    } else {
      throw new ParseException("Unexpected token " + next + ". " + t + " expected");
    }
  }

  private Block block() throws ParseException {

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
    return bl;
  }

  private Expression statement() throws ParseException {
    next();
    switch (next.type) {
      case PERSIST:
        consume();
        Expression obj = expression();
        consume(TokenType.SEMICOLON);
        return new PersistStmt(objectStore, obj);
      case RESTORE:
        consume();
        consume(TokenType.IDENTIFIER);
        String identi = current.text;
        Expression col2 = expression();
        Expression idexp = expression();
        consume(TokenType.SEMICOLON);
        return new RestoreStmt(objectStore, col2, idexp, identi);
      case PRINT:
        consume();
        Expression ex1 = expression();
        consume(TokenType.SEMICOLON);
        return new Print(ex1);
      case INPUT:
        consume();
        consume(TokenType.IDENTIFIER);
        Input input = new Input(scanner, current.text);
        consume(TokenType.SEMICOLON);
        return input;
      case EXIT:
        consume();
        consume(TokenType.SEMICOLON);
        return new Exit();
      case ASSERT:
        consume();
        Expression ex2 = expression();
        consume(TokenType.SEMICOLON);
        return new Assert(ex2);
      case RETURN:
        consume();
        Expression ex = null;
        next();
        if (next.type != TokenType.SEMICOLON) {
          ex = expression();
        }
        consume(TokenType.SEMICOLON);
        return new Return(ex);
      case IF:
        consume();
        consume(TokenType.LPAREN);
        Expression ifCond = expression();
        consume(TokenType.RPAREN);
        Block ifBody = block();
        IfStatement ifs = new IfStatement(ifCond, ifBody);
        next();
        if (next.type == TokenType.ELSE) {
          consume();
          Block elseBody = block();
          ifs.setElseBody(elseBody);
        }
        return ifs;
      case WHILE:
        consume();
        consume(TokenType.LPAREN);
        Expression cond = expression();
        consume(TokenType.RPAREN);
        Block body = block();
        return new WhileStatement(cond, body);
      case VAR:
        return vardecl();
      case FUNC:
        return funcdecl();
      case OBJECT:
        return objectdecl();
      case CLASS:
        return classdecl();
      default:
        Expression se = expression();
        consume(TokenType.SEMICOLON);
        return se;
    }
  }

  private Expression declaration() throws ParseException {
    next();
    switch (next.type) {
      case VAR:
        return vardecl();
      case FUNC:
        return funcdecl();
      case OBJECT:
        return objectdecl();
      case CLASS:
        return classdecl();
      default:
        throw new ParseException("Unexpected token " + next + ". Declaration expected.");
    }
  }

  private Expression vardecl() throws ParseException {
    consume(TokenType.VAR);
    consume(TokenType.IDENTIFIER);
    VarDecl varDecl = new VarDecl(current.text);
    next();
    if (next.type == TokenType.ASSIGN) {
      consume();
      varDecl.setDefaultValue(expression());
    }
    consume(TokenType.SEMICOLON);
    return varDecl;
  }
  
  private Expression refdecl() throws ParseException {
    consume(TokenType.VAR);
    consume(TokenType.IDENTIFIER);
    VarDecl varDecl = new VarDecl(current.text);
    next();
    if (next.type == TokenType.ASSIGN) {
      consume();
      varDecl.setDefaultValue(expression());
    }
    consume(TokenType.SEMICOLON);
    return varDecl;
  }

  private Expression funcdecl() throws ParseException {
    consume(TokenType.FUNC);
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
    consume(TokenType.RPAREN);
    Block b = block();
    fd.setBody(b);
    return fd;
  }

  private Expression objectdecl() throws ParseException {
    consume(TokenType.OBJECT);
    consume(TokenType.IDENTIFIER);
    ObjectDecl od = new ObjectDecl(current.text);
    consume(TokenType.LBRACE);
    next();
    while (next.type != TokenType.RBRACE) {
      od.getExpressions().add(declaration());
      next();
    }
    consume();
    return od;
  }

  private Expression classdecl() throws ParseException {
    consume(TokenType.CLASS);
    consume(TokenType.IDENTIFIER);
    String cl = current.text;
    next();
    Expression extExp = null;
    if (next.type == TokenType.EXTENDS) {
      consume();
      extExp = expression();
    }
    ClassDecl cs = new ClassDecl(extExp, cl);
    consume(TokenType.LBRACE);
    next();
    while (next.type != TokenType.RBRACE) {
      cs.getDeclarations().add(declaration());
      next();
    }
    consume();
    return cs;
  }

  private Expression expression() throws ParseException {
    Expression left = comparison();
    while (true) {
      next();
      switch (next.type) {
        case ASSIGN:
          consume();
          Assignment as = new Assignment();
          as.setLeftValue(left);
          as.setRightValue(comparison());
          left = as;
          break;
        default:
          return left;
      }
    }
  }

  private Expression comparison() throws ParseException {
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
          return left;
      }
    }
  }

  private Expression additive() throws ParseException {
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
          return left;
      }
    }
  }

  private Expression multiplicative() throws ParseException {
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
          return left;
      }
    }
  }

  private Expression creation() throws ParseException {
    next();
    if (next.type == TokenType.NEW) {
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
        return nex1;
      } else {
        NewExpr nex2 = new NewExpr(ex);
        nex2.getArgs().addAll(expList);
        return nex2;
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
                MethodOptsExpr mex1 = new MethodOptsExpr((MemberExpr) left);
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
            return left;
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
    Expression left = factor();
    while (true) {
      next();
      switch (next.type) {
        case DOT:
          consume();
          consume(TokenType.IDENTIFIER);
          String name = current.text;
          MemberExpr mex = new MemberExpr();
          mex.setLeft(left);
          mex.setId(name);
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
          return left;
      }
    }
  }

  private Expression factor() throws ParseException {
    next();
    switch (next.type) {
      case FLOAT:
        consume();
        return new FloatLiteral(Float.parseFloat(current.text));
      case INT:
        consume();
        return new IntLiteral(Integer.parseInt(current.text));
      case STRING:
        consume();
        String text = current.text.replaceAll("^\"|\"$", "");
        return new StringLiteral(text);
      case TRUE:
        consume();
        return new BooleanLiteral(true);
      case FALSE:
        consume();
        return new BooleanLiteral(false);
      case LPAREN:
        consume();
        Expression exp = expression();
        consume(TokenType.RPAREN);
        return new Parenthesis(exp);
      case IDENTIFIER:
        consume();
        return new VarExpr(current.text);
      case FUNC:
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
        consume(TokenType.RPAREN);
        Block b = block();
        fdx.setBody(b);
        return fdx;
      case OBJECT:
        consume();
        ObjectDeclExpr od = new ObjectDeclExpr();
        consume(TokenType.LBRACE);
        next();
        while (next.type != TokenType.RBRACE) {
          od.getExpressions().add(declaration());
          next();
        }
        consume();
        return od;
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
        consume(TokenType.RBRACKET);
        next();
        if (next.type == TokenType.LPAREN) {
          consume();
          le.setInitSize(expression());
          consume(TokenType.RPAREN);
        }
        return le;
      default:
        throw new ParseException("Unexpected token '" + next + "'. Expression expected.");
    }
  }

  public static void main(String[] args) {
    MathParser mp = new MathParser();
    mp.getTokenizer().setDumpTokens(false);
    try {
      mp.eval(Util.readFile("src/test/mp/test.mp"));
    } catch (IOException | EvalException | ParseException ex) {
      Log.error(ex);
    }
  }

}
