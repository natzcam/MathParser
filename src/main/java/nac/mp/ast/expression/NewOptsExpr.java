/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.type.MPObject;;
import nac.mp.ast.Expression;
import nac.mp.ast.Expression;
import nac.mp.type.MPClass;
import nac.mp.type.MPFunc;
import nac.mp.type.MPObject;

/**
 *
 * @author camomon
 */
public class NewOptsExpr implements Expression {

  private final String[] path;
  private final List<Expression> args = new ArrayList<>();
  private final Map<String, Expression> opts = new HashMap<>();

  public NewOptsExpr(String[] path) {
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
    MPClass clazz;
    MPObject c = null;
    if (path.length == 1) {
      clazz = (MPClass) scope.getVar(path[0]);
    } else {
      c = (MPObject) scope.getVar(path[0]);
      for (int i = 1; i < path.length - 1; i++) {
        c = (MPObject) c.getVar(path[i]);
      }
      clazz = (MPClass) c.getVar(path[path.length - 1]);
    }

    List<MPObject> argValues = new ArrayList<>();
    for (Expression exp : args) {
      argValues.add(exp.eval(scope));
    }
    Map<String, MPObject> optsValues = new HashMap<>();
    for (String key : opts.keySet()) {
      optsValues.put(key, opts.get(key).eval(scope));
    }
    c = clazz.create();
    MPFunc ctor = (MPFunc)c.getVar("__init__");
    if(ctor != null){
      ctor.call(c, argValues, optsValues);
    }
    return c;
  }
}
