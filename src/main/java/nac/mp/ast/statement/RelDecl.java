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
import nac.mp.ast.expression.MemberExpr;
import nac.mp.ast.expression.VarExpr;
import nac.mp.type.MPObject;

/**
 *
 * @author user
 */
public class RelDecl implements Expression {

  private final MemberExpr left;
  private final MemberExpr right;
  private final TokenType relType;

  public RelDecl(Expression left, Expression right, TokenType relType) {
    this.left = left instanceof VarExpr ? convertToMemberExpr(left) : (MemberExpr) left;
    this.right = right instanceof VarExpr ? convertToMemberExpr(left) : (MemberExpr) right;
    this.relType = relType;
  }

  private static MemberExpr convertToMemberExpr(Expression ex) {
    VarExpr ve = (VarExpr) ex;
    return new MemberExpr(ve, "id");
  }

  public MemberExpr getLeft() {
    return left;
  }

  public MemberExpr getRight() {
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
