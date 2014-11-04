/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.List;
import java.util.Map;
import nac.mp.EvalException;
import nac.mp.type.MPObject;
import nac.mp.ast.Scope;
import nac.mp.ast.Expression;
import nac.mp.ast.LValue;
import nac.mp.type.MPFunc;

/**
 *
 * @author user
 */
public class MemberExpr extends LValue {

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

  public MPObject methodCall(Scope scope, List<MPObject> argsValues) throws EvalException {
    MPObject lo = (MPObject) left.eval(scope);
    MPFunc func = (MPFunc) lo.getVar(id);
    return func.call(lo, argsValues);
  }

  public MPObject methodCall(Scope scope, List<MPObject> argsValues, Map<String, MPObject> optsValues) throws EvalException {
    MPObject lo = (MPObject) left.eval(scope);
    MPFunc func = (MPFunc) lo.getVar(id);
    return func.call(lo, argsValues);
  }

  @Override
  public void setValue(Scope scope, MPObject value) throws EvalException {
    MPObject lo = (MPObject) left.eval(scope);
    lo.setVar(id, value);
  }
}
