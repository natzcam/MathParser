/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.ast.Scope;
import nac.mp.ast.Expression;
import nac.mp.ast.LValue;
import nac.mp.type.natv.MPInteger;
import nac.mp.type.MPList;
import nac.mp.type.MPModelObj;
import nac.mp.type.MPObject;
import nac.mp.type.MPRefList;

/**
 *
 * @author user
 */
public class ListExpr extends LValue {

  private final Expression expression;
  private Expression index;

  public ListExpr(Expression expression) {
    this.expression = expression;
  }

  public void setIndex(Expression index) {
    this.index = index;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPObject obj = expression.eval(scope);
    if (obj instanceof MPRefList) {
      return ((MPRefList) obj).get((MPInteger) index.eval(scope));
    } else if (obj instanceof MPList) {
      return ((MPList) obj).get((MPInteger) index.eval(scope));
    }
    return null;
  }

  @Override
  public void setValue(Scope scope, MPObject value) throws EvalException {
    MPObject obj = expression.eval(scope);
    if (obj instanceof MPRefList) {
      ((MPRefList) obj).set((MPInteger) index.eval(scope), (MPModelObj) value);
    } else if (obj instanceof MPList) {
      ((MPList) obj).set((MPInteger) index.eval(scope), value);
    }
  }

}
