/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.io.IOException;
import java.util.Scanner;
import nac.mp.ast.statement.FunctionDecl;
import nac.mp.ast.expression.FloatLiteral;
import nac.mp.ast.expression.Parenthesis;
import nac.mp.ast.expression.IdExpr;
import nac.mp.ast.Block;
import nac.mp.ast.Expression;
import nac.mp.ast.expression.BooleanLiteral;
import nac.mp.ast.expression.DotExpression;
import nac.mp.ast.expression.Equal;
import nac.mp.ast.expression.MinusExpression;
import nac.mp.ast.expression.FunctionDeclExpr;
import nac.mp.ast.statement.Exit;
import nac.mp.ast.statement.IfStatement;
import nac.mp.ast.statement.Input;
import nac.mp.ast.expression.IntLiteral;
import nac.mp.ast.expression.LessThan;
import nac.mp.ast.expression.LessThanEqual;
import nac.mp.ast.expression.ListLiteralExpr;
import nac.mp.ast.expression.MoreThan;
import nac.mp.ast.expression.MoreThanEqual;
import nac.mp.ast.expression.NotEqual;
import nac.mp.ast.expression.ObjectDeclExpr;
import nac.mp.ast.expression.PlusExpression;
import nac.mp.ast.expression.SlashExpression;
import nac.mp.ast.statement.Print;
import nac.mp.ast.statement.Return;
import nac.mp.ast.expression.StarExpression;
import nac.mp.ast.expression.StringLiteral;
import nac.mp.ast.statement.Assert;
import nac.mp.ast.statement.ClassDecl;
import nac.mp.ast.statement.ObjectDecl;
import nac.mp.ast.statement.PersistStmt;
import nac.mp.ast.statement.RestoreStmt;
import nac.mp.ast.statement.VarDecl;
import nac.mp.ast.statement.WhileStatement;
import nac.store.mapdb.ObjectStorage;

/**
 * concat strings
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
        return expression();
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
      cs.getExpressions().add(declaration());
      next();
    }
    consume();
    return cs;
  }

  private Expression expression() throws ParseException {
    Expression left = additive();
    while (true) {
      next();
      switch (next.type) {
        case LT:
          consume();
          LessThan lt = new LessThan();
          lt.setLeft(left);
          lt.setRight(expression());
          left = lt;
          break;
        case LTE:
          consume();
          LessThanEqual lte = new LessThanEqual();
          lte.setLeft(left);
          lte.setRight(expression());
          left = lte;
          break;
        case MT:
          consume();
          MoreThan mt = new MoreThan();
          mt.setLeft(left);
          mt.setRight(expression());
          left = mt;
          break;
        case MTE:
          consume();
          MoreThanEqual mte = new MoreThanEqual();
          mte.setLeft(left);
          mte.setRight(expression());
          left = mte;
          break;
        case EQUAL:
          consume();
          Equal eq = new Equal();
          eq.setLeft(left);
          eq.setRight(expression());
          left = eq;
          break;
        case NOT_EQUAL:
          consume();
          NotEqual neq = new NotEqual();
          neq.setLeft(left);
          neq.setRight(expression());
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
          pl.setRight(additive());
          left = pl;
          break;
        case MINUS:
          consume();
          MinusExpression dsh = new MinusExpression();
          dsh.setLeft(left);
          dsh.setRight(additive());
          left = dsh;
          break;
        default:
          return left;
      }
    }
  }

  private Expression multiplicative() throws ParseException {
    Expression left = access();
    while (true) {
      next();
      switch (next.type) {
        case STAR:
          consume();
          StarExpression srt = new StarExpression();
          srt.setLeft(left);
          srt.setRight(multiplicative());
          left = srt;
          break;
        case SLASH:
          consume();
          SlashExpression slt = new SlashExpression();
          slt.setLeft(left);
          slt.setRight(multiplicative());
          left = slt;
          break;
        default:
          return left;
      }
    }
  }

  private Expression access() throws ParseException {
    Expression left = factor();
    while (true) {
      next();
      switch (next.type) {
        case DOT:
          consume();
          DotExpression dex = new DotExpression();
          dex.setLeft(left);
          consume(TokenType.IDENTIFIER);
          dex.setId(current.text);
          left = dex;
          break;
        case SLASH:
          consume();
          SlashExpression slt = new SlashExpression();
          slt.setLeft(left);
          slt.setRight(multiplicative());
          left = slt;
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
        return new IdExpr(current.text);
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
