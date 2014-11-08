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

  private final Expression left;
  private final String id;

  public MemberExpr(Expression left, String id) {
    this.left = left;
    this.id = id;
  }

  public Expression getLeft() {
    return left;
  }

  public String getId() {
    return id;
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
