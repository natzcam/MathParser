/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.TokenType;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.MPObject;

/**
 *
 * @author user
 */
public class RelDecl implements Expression {

  private final Expression left;
  private final Expression right;
  private final TokenType relType;

  public RelDecl(Expression left, Expression right, TokenType relType) {
    this.left = left;
    this.right = right;
    this.relType = relType;
  }

  public Expression getLeft() {
    return left;
  }

  public Expression getRight() {
    return right;
  }

  public TokenType getRelType() {
    return relType;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {

    return null;
  }
}
