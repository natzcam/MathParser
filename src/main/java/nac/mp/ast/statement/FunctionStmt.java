/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.ast.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.Type;
import nac.mp.ast.Expression;
import nac.mp.Scope;
import nac.mp.type.MPFunc;
import nac.mp.type.MPObject;

/**
 *
 * @author user
 */
public class FunctionStmt implements Statement {

  private final String[] path;
  private final List<Expression> args = new ArrayList<>();

  public FunctionStmt(String[] path) {
    this.path = path;
  }

  public List<Expression> getArgs() {
    return args;
  }

  @Override
  public Type eval(Scope scope) throws EvalException {
    MPFunc func;
    MPObject c = null;
    //System.out.println("path: " + Arrays.toString(path));
    if (path.length == 1) {
      func = (MPFunc) scope.getVar(path[0]);
    } else {
      c = (MPObject) scope.getVar(path[0]);
      for (int i = 1; i < path.length - 1; i++) {
        c = (MPObject) c.getVar(path[i]);
      }
      func = (MPFunc) c.getVar(path[path.length - 1]);
    }

    List<Type> argValues = new ArrayList<>();
    for (Expression exp : args) {
      argValues.add(exp.eval(scope));
    }
    func.call(c, argValues);
    return null;
  }
}
