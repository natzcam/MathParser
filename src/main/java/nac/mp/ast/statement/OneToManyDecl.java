/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ParseException;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.MPObject;

/**
 *
 * @author user
 */
public class OneToManyDecl implements Expression {

  private final Expression left;
  private final Expression right;
  private final boolean bidi = false;
  private final boolean forward = true;

  public OneToManyDecl(Expression left, Expression right) throws ParseException {
    this.left = left;
    this.right = right;

//    if (this.left instanceof MemberExpr) {
//      MemberExpr l = (MemberExpr) this.left;
//      String l1 = ((VarExpr) l.getLeft()).getId();
//      String l2 = l.getId();
//      leftModel = modelRepo.get(l1);
//      leftAttr = leftModel.getAttrDecl(l2);
//    } else {
//      VarExpr r = (VarExpr) this.left;
//      leftModel = modelRepo.get(r.getId());
//    }
//
//    if (this.right instanceof MemberExpr) {
//      MemberExpr r = (MemberExpr) this.right;
//      String r1 = ((VarExpr) r.getLeft()).getId();
//      String r2 = r.getId();
//      rightModel = modelRepo.get(r1);
//      rightAttr = rightModel.getAttrDecl(r2);
//    } else {
//      VarExpr r = (VarExpr) this.right;
//      rightModel = modelRepo.get(r.getId());
//    }
//
//    if (leftAttr == null && rightAttr == null) {
//      throw new ParseException("Invalid relationship: No attributes on both sides");
//    } else if (rightAttr == null) {
//      bidi = false;
//      forward = true;
//    } else if (leftAttr == null) {
//      bidi = false;
//      forward = false;
//    } else {
//      bidi = true;
//      forward = true;
//    }
  }

  public Expression getLeft() {
    return left;
  }

  public Expression getRight() {
    return right;
  }

  public boolean isBidi() {
    return bidi;
  }

  public boolean isForward() {
    return forward;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {
    return null;
  }
}
