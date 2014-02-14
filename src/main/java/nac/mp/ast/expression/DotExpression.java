/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.type.MPObject;
import nac.mp.Scope;
import nac.mp.ast.Expression;
import nac.mp.ast.LValue;

/**
 *
 * @author user
 */
public class DotExpression extends LValue {
  
  private Expression left;
  private String id;
  
  public void setId(String id) {
    this.id = id;
  }
  
  public void setLeft(Expression left) {
    this.left = left;
  }
  
  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPObject lo = (MPObject) left.eval(scope);
    return lo.getVar(id);
  }
  
  @Override
  public void setValue(Scope scope, MPObject value) throws EvalException {
    MPObject lo = (MPObject) left.eval(scope);
    lo.setVar(id, value);
  }
}
