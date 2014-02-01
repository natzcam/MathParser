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
import nac.mp.ast.statement.Assignment;
import nac.mp.ast.Factor;
import nac.mp.ast.statement.FunctionDecl;
import nac.mp.ast.expression.FloatLiteral;
import nac.mp.ast.expression.Parenthesis;
import nac.mp.ast.expression.VarExpr;
import nac.mp.ast.Block;
import nac.mp.ast.Declaration;
import nac.mp.ast.Expression;
import nac.mp.ast.Statement;
import nac.mp.ast.expression.BooleanLiteral;
import nac.mp.ast.expression.Equal;
import nac.mp.ast.expression.MinusExpression;
import nac.mp.ast.expression.FunctionDeclExpr;
import nac.mp.ast.statement.Exit;
import nac.mp.ast.expression.FunctionExpr;
import nac.mp.ast.expression.FunctionOptsExpr;
import nac.mp.ast.statement.IfStatement;
import nac.mp.ast.statement.Input;
import nac.mp.ast.expression.IntLiteral;
import nac.mp.ast.expression.LessThan;
import nac.mp.ast.expression.LessThanEqual;
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
import nac.mp.ast.statement.EntityStmt;
import nac.mp.ast.statement.FunctionOptsStatement;
import nac.mp.ast.statement.FunctionStmt;
import nac.mp.ast.statement.ObjectDecl;
import nac.mp.ast.statement.VarDecl;
import nac.mp.ast.statement.WhileStatement;

/**
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

  public void eval(String input) throws ParseException, EvalException {
    tokenizer.process(input);
    next();
    while (next.type != TokenType.EOF) {
      fileBlock.addStatement(statement());
      next();
    }
    fileBlock.eval(global);
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

  private ObjectDecl object() throws ParseException {
    consume(TokenType.IDENTIFIER);
    ObjectDecl od = new ObjectDecl(current.text);
    consume(TokenType.LBRACE);
    next();
    while (next.type != TokenType.RBRACE) {
      od.getDeclarations().add(declaration());
      next();
    }
    consume();
    next();
    if (next.type == TokenType.PROTOTYPE) {
      consume();
      od.setProtoExp(expression());
      consume(TokenType.SEMICOLON);
    }
    return od;
  }

  private Declaration declaration() throws ParseException {
    next();
    switch (next.type) {
      case VAR:
        consume();
        consume(TokenType.IDENTIFIER);
        VarDecl varDecl = new VarDecl(current.text);
        next();
        if (next.type == TokenType.ASSIGN) {
          consume();
          varDecl.setDefaultValue(expression());
        }
        consume(TokenType.SEMICOLON);
        return varDecl;
      case FUNC:
        consume();
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
      case OBJECT:
        consume();
        ObjectDecl od = object();
        return od;
    }
    throw new ParseException("Unexpected token " + next + ". Declaration expected.");
  }

  private Statement statement() throws ParseException {
    next();
    switch (next.type) {
      case ENTITY:
        consume();
        consume(TokenType.IDENTIFIER);
        String en = current.text;
        consume(TokenType.PROTOTYPE);
        Expression p = expression();
        consume(TokenType.SEMICOLON);
        return new EntityStmt(en, p);
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
      case IDENTIFIER:
        consume();
        String[] path = current.text.split("\\.");
        next();
        if (next.type == TokenType.ASSIGN) {
          consume();
          Expression exp = expression();
          consume(TokenType.SEMICOLON);
          return new Assignment(path, exp);
        } else if (next.type == TokenType.LPAREN) {
          consume();
          List<Expression> expList = new ArrayList<>();
          Map<String, Expression> optsMap = new HashMap<>();
          next();
          while (next.type != TokenType.RPAREN) {
            expList.add(expression());
            next();
            if (next.type == TokenType.RPAREN) {
              break;
            } else if (next.type == TokenType.SEMICOLON) {
              consume(TokenType.SEMICOLON);
              break;
            } else {
              consume(TokenType.COMMA);
            }
          }
          while (next.type != TokenType.RPAREN) {
            consume(TokenType.IDENTIFIER);
            String optName = current.text;
            consume(TokenType.COLON);
            optsMap.put(optName, expression());
            next();
            if (next.type == TokenType.RPAREN) {
              break;
            } else {
              consume(TokenType.COMMA);
            }
          }
          consume(TokenType.RPAREN);
          consume(TokenType.SEMICOLON);

          if (optsMap.size() > 0) {
            FunctionOptsStatement fex1 = new FunctionOptsStatement(path);
            fex1.getArgs().addAll(expList);
            fex1.getOpts().putAll(optsMap);
            return fex1;
          } else {
            FunctionStmt fex2 = new FunctionStmt(path);
            fex2.getArgs().addAll(expList);
            return fex2;
          }
        } else {
          throw new ParseException("Unexpected token " + next + ". Assignment expected.");
        }
      default:
        return declaration();
    }
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
    Expression left = factor();
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

  private Factor factor() throws ParseException {
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
        String[] path = current.text.split("\\.");
        next();

        if (next.type == TokenType.LPAREN) {
          consume();
          List<Expression> expList = new ArrayList<>();
          Map<String, Expression> optsMap = new HashMap<>();
          next();
          while (next.type != TokenType.RPAREN) {
            expList.add(expression());
            next();
            if (next.type == TokenType.RPAREN) {
              break;
            } else if (next.type == TokenType.SEMICOLON) {
              consume(TokenType.SEMICOLON);
              break;
            } else {
              consume(TokenType.COMMA);
            }
          }

          while (next.type != TokenType.RPAREN) {
            consume(TokenType.IDENTIFIER);
            String optName = current.text;
            consume(TokenType.COLON);
            optsMap.put(optName, expression());
            next();
            if (next.type == TokenType.RPAREN) {
              break;
            } else {
              consume(TokenType.COMMA);
            }
          }
          consume(TokenType.RPAREN);

          if (optsMap.size() > 0) {
            FunctionOptsExpr fex1 = new FunctionOptsExpr(path);
            fex1.getArgs().addAll(expList);
            fex1.getOpts().putAll(optsMap);
            return fex1;
          } else {
            FunctionExpr fex2 = new FunctionExpr(path);
            fex2.getArgs().addAll(expList);
            return fex2;
          }

        } else {
          return new VarExpr(path);
        }
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
          od.getDeclarations().add(declaration());
          next();
        }
        consume();
        next();
        if (next.type == TokenType.PROTOTYPE) {
          consume();
          od.setProtoExp(expression());
        }
        return od;
      default:
        throw new ParseException("Unexpected token '" + next + "'. Factor expected.");
    }
  }

  public static void main(String[] args) {
    MathParser mp = new MathParser();
    mp.getTokenizer().setDumpTokens(false);
    try {
      mp.eval(Util.readFile("src/main/resources/nac/scripts/test.mp"));
    } catch (IOException | EvalException | ParseException ex) {
      Log.error(ex);
    }
  }

}
