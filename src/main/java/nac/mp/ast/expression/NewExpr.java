/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.Creator;
import nac.mp.type.instance.MPFunc;
import nac.mp.type.instance.MPObject;

/**
 *
 * @author camomon
 */
public class NewExpr implements Expression {

  private final Expression expression;
  private final List<Expression> args = new ArrayList<>();

  public NewExpr(Expression expression) {
    this.expression = expression;
  }

  public List<Expression> getArgs() {
    return args;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {
    Creator creator = (Creator) expression.eval(scope, store);

    List<MPObject> argValues = new ArrayList<>();
    for (Expression exp : args) {
      argValues.add(exp.eval(scope, store));
    }

    MPObject c = creator.newInstance(store);
    MPFunc ctor = (MPFunc) c.getVar("__init__", store);

    if (ctor != null) {
      ctor.call(c, argValues,store);
    }

    return c;
  }
}
