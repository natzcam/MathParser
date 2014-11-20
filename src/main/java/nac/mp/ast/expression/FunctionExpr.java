/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.instance.MPFunc;
import nac.mp.type.instance.MPObject;

/**
 *
 * @author ladilads
 */
public class FunctionExpr implements Expression {

  private final Expression expression;
  private final List<Expression> args = new ArrayList<>();

  public FunctionExpr(Expression expression) {
    this.expression = expression;
  }

  public List<Expression> getArgs() {
    return args;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {
    MPFunc func = (MPFunc) expression.eval(scope, store);

    List<MPObject> argValues = new ArrayList<>();
    for (Expression exp : args) {
      argValues.add(exp.eval(scope, store));
    }
    return func.call(null, argValues, store);
  }
}
