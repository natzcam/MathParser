/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.LValue;
import nac.mp.ast.Scope;
import nac.mp.type.MPObject;
import nac.mp.type.instance.MPInteger;
import nac.mp.type.instance.MPList;
import nac.mp.type.instance.MPModelObj;
import nac.mp.type.instance.MPRefList;

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
  public MPObject eval(Scope scope, ObjectStore store) {
    MPObject obj = expression.eval(scope, store);
    if (obj instanceof MPRefList) {
      return ((MPRefList) obj).get((MPInteger) index.eval(scope, store), store);
    } else if (obj instanceof MPList) {
      return ((MPList) obj).get((MPInteger) index.eval(scope, store));
    }
    return null;
  }

  @Override
  public void setValue(Scope scope, MPObject value, ObjectStore store) {
    MPObject obj = expression.eval(scope, store);
    if (obj instanceof MPRefList) {
      ((MPRefList) obj).set((MPInteger) index.eval(scope, store), (MPModelObj) value);
    } else if (obj instanceof MPList) {
      ((MPList) obj).set((MPInteger) index.eval(scope, store), value);
    }
  }
}
