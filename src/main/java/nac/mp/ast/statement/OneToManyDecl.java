/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.ast.expression.MemberExpr;
import nac.mp.type.MPObject;

/**
 *
 * @author user
 */
public class OneToManyDecl implements Expression {

  private final MemberExpr left;
  private final Expression right;

  public OneToManyDecl(Expression left, Expression right) {
    this.left = (MemberExpr) left;
    this.right = right;
  }

//  private static MemberExpr defaultToMember(Expression ex, String member) {
//    if (ex instanceof MemberExpr) {
//      return (MemberExpr) ex;
//    }
//    return new MemberExpr((VarExpr) ex, member);
//  }

  public MemberExpr getLeft() {
    return left;
  }

  public Expression getRight() {
    return right;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    return null;
  }
}
