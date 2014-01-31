/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.Type;
import nac.mp.ast.Factor;
import nac.mp.Scope;
import nac.mp.type.MPObject;

/**
 *
 * @author natz
 */
public class VarExpr implements Factor {

  private final String[] path;

  public VarExpr(String[] path) {
    this.path = path;
  }

  @Override
  public Type eval(Scope scope) throws EvalException {
    Type type;

    if (path.length == 1) {
      type = scope.getVar(path[0]);
    } else {
      MPObject c = (MPObject) scope.getVar(path[0]);
      for (int i = 1; i < path.length - 1; i++) {
        c = (MPObject) c.getVar(path[i]);
      }
      type = c.getVar(path[path.length - 1]);
    }
    return type;
  }
}
