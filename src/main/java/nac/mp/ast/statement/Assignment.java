/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.ast.Statement;
import nac.mp.EvalException;
import nac.mp.Type;
import nac.mp.ast.Expression;
import nac.mp.Scope;
import nac.mp.type.MPObject;

/**
 *
 * @author nathaniel
 */
public class Assignment implements Statement {

  private final String[] path;
  private final Expression rightValue;

  public Assignment(String[] path, Expression rightValue) {
    this.path = path;
    this.rightValue = rightValue;
  }

  public Expression getRightValue() {
    return rightValue;
  }

  public String[] getPath() {
    return path;
  }

  @Override
  public Type eval(Scope scope) throws EvalException {

    if (path.length == 1) {
      scope.setVar(path[0], rightValue.eval(scope));
    } else {
      MPObject c = (MPObject) scope.getVar(path[0]);
      for (int i = 1; i < path.length - 1; i++) {
        c = (MPObject) c.getVar(path[i]);
      }
      c.setVar(path[path.length - 1], rightValue.eval(scope));
    }

    return null;
  }
}
