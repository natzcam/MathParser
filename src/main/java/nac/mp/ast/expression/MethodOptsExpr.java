/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.instance.MPObject;

/**
 *
 * @author ladilads
 */
public class MethodOptsExpr implements Expression {

  private final Expression expression;
  private final List<Expression> args = new ArrayList<>();
  private final Map<String, Expression> opts = new HashMap<>();

  public MethodOptsExpr(Expression expression) {
    this.expression = expression;
  }

  public List<Expression> getArgs() {
    return args;
  }

  public Map<String, Expression> getOpts() {
    return opts;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {

    List<MPObject> argValues = new ArrayList<>();
    for (Expression exp : args) {
      argValues.add(exp.eval(scope, store));
    }
    Map<String, MPObject> optsValues = new HashMap<>();
    for (String key : opts.keySet()) {
      optsValues.put(key, opts.get(key).eval(scope, store));
    }
    return ((MemberExpr) expression).methodCall(scope, argValues, optsValues, store);
  }
}
