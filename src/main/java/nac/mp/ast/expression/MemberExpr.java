/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.List;
import java.util.Map;
import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.LValue;
import nac.mp.ast.Scope;
import nac.mp.type.MPObject;
import nac.mp.type.instance.MPFunc;

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
  public MPObject eval(Scope scope, ObjectStore store) {
    MPObject lo = left.eval(scope, store);
    return lo.getVar(id, store);
  }

  public MPObject methodCall(Scope scope, List<MPObject> argsValues, ObjectStore store) {
    MPObject lo = left.eval(scope, store);
    MPFunc func = (MPFunc) lo.getVar(id, store);
    return func.call(lo, argsValues, store);
  }

  public MPObject methodCall(Scope scope, List<MPObject> argsValues, Map<String, MPObject> optsValues, ObjectStore store) {
    MPObject lo = left.eval(scope, store);
    MPFunc func = (MPFunc) lo.getVar(id, store);
    return func.call(lo, argsValues, store);
  }

  @Override
  public void setValue(Scope scope, MPObject value, ObjectStore store) {
    MPObject lo = left.eval(scope, store);
    lo.setVar(id, value, store);
  }
}
