/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.type.MPObject;;
import nac.mp.ast.Expression;
import nac.mp.ast.Factor;
import nac.mp.Scope;
import nac.mp.type.MPFunc;
import nac.mp.type.MPObject;

/**
 *
 * @author ladilads
 */
public class FunctionExpr implements Factor {

  private final String[] path;
  private final List<Expression> args = new ArrayList<>();

  public FunctionExpr(String[] path) {
    this.path = path;
  }

  public List<Expression> getArgs() {
    return args;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPFunc func;
    MPObject c = null;
    if (path.length == 1) {
      func = (MPFunc) scope.getVar(path[0]);
    } else {
      c = (MPObject) scope.getVar(path[0]);
      for (int i = 1; i < path.length - 1; i++) {
        c = (MPObject) c.getVar(path[i]);
      }
      func = (MPFunc) c.getVar(path[path.length - 1]);
    }

    List<MPObject> argValues = new ArrayList<>();
    for (Expression exp : args) {
      argValues.add(exp.eval(scope));
    }
    MPObject result = func.call(c, argValues);
    if (result == null) {
      throw new EvalException("Function does not return: " + path);
    } else {
      return result;
    }
  }
}
