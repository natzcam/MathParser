/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.ArrayList;
import java.util.List;
import nac.mp.Creator;
import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.ast.Expression;
import nac.mp.type.MPFunc;
import nac.mp.type.MPObject;

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
  public MPObject eval(Scope scope) throws EvalException {
    Creator creator = (Creator) expression.eval(scope);

    List<MPObject> argValues = new ArrayList<>();
    for (Expression exp : args) {
      argValues.add(exp.eval(scope));
    }

    MPObject c = creator.create();
    MPFunc ctor = (MPFunc) c.getVar("__init__");

    if (ctor != null) {
      ctor.call(c, argValues);
    }

    return c;
  }
}
