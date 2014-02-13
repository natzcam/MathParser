/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nac.mp.EvalException;
import nac.mp.type.MPObject;;
import nac.mp.ast.Expression;
import nac.mp.Scope;
import nac.mp.ast.Statement;
import nac.mp.type.MPFunc;
import nac.mp.type.MPObject;

/**
 *
 * @author ladilads
 */
public class FunctionOptsStatement implements Statement {

  private final String[] path;
  private final List<Expression> args = new ArrayList<>();
  private final Map<String, Expression> opts = new HashMap<>();

  public FunctionOptsStatement(String[] path) {
    this.path = path;
  }

  public List<Expression> getArgs() {
    return args;
  }

  public Map<String, Expression> getOpts() {
    return opts;
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
    Map<String, MPObject> optsValues = new HashMap<>();
    for (String key : opts.keySet()) {
      optsValues.put(key, opts.get(key).eval(scope));
    }
    func.call(c, argValues, optsValues);
    return null;
  }
}
