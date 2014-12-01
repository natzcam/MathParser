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
import nac.mp.type.ListBase;
import nac.mp.type.MPObject;
import nac.mp.type.instance.MPInteger;

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
    ListBase obj = (ListBase) expression.eval(scope, store);
    return obj.get(index.eval(scope, store), store);
  }

  @Override
  public void setValue(Scope scope, MPObject value, ObjectStore store) {
    ListBase obj = (ListBase) expression.eval(scope, store);
    obj.set(index.eval(scope, store), value);
  }
}
