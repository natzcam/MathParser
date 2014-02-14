/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.ast.Expression;
import nac.mp.type.MPInteger;
import nac.mp.type.MPList;
import nac.mp.type.MPObject;

/**
 *
 * @author user
 */
public class ListExpr implements Expression {

  private final String[] path;
  private Expression index;

  public ListExpr(String[] path) {
    this.path = path;
  }

  public void setIndex(Expression index) {
    this.index = index;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPList list;
    MPObject c = null;
    if (path.length == 1) {
      list = (MPList) scope.getVar(path[0]);
    } else {
      c = (MPObject) scope.getVar(path[0]);
      for (int i = 1; i < path.length - 1; i++) {
        c = (MPObject) c.getVar(path[i]);
      }
      list = (MPList) c.getVar(path[path.length - 1]);
    }
    return list.get((MPInteger)index.eval(scope));
  }

}
