/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.ast.Expression;
import nac.mp.Scope;
import nac.mp.type.MPFunc;
import nac.mp.type.MPObject;

/**
 *
 * @author ladilads
 */
public class MethodExpr implements Expression {

  private final Expression expression;
  private String name;
  private final List<Expression> args = new ArrayList<>();

  public MethodExpr(Expression expression) {
    this.expression = expression;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Expression> getArgs() {
    return args;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPObject obj = (MPObject) expression.eval(scope);

    List<MPObject> argValues = new ArrayList<>();
    for (Expression exp : args) {
      argValues.add(exp.eval(scope));
    }
    return obj.methodCall(name, argValues);
  }
}
