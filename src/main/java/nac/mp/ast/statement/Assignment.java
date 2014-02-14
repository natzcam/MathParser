/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
;
import nac.mp.ast.Expression;
import nac.mp.Scope;
import nac.mp.type.MPObject;

/**
 *
 * @author nathaniel
 */
public class Assignment implements Expression {

  private final Expression leftValue;
  private final Expression rightValue;

  public Assignment(Expression leftValue, Expression rightValue) {
    this.leftValue = leftValue;
    this.rightValue = rightValue;
  }

  public Expression getRightValue() {
    return rightValue;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {

//    if (path.length == 1) {
//      scope.setVar(path[0], rightValue.eval(scope));
//    } else {
//      MPObject c = (MPObject) scope.getVar(path[0]);
//      for (int i = 1; i < path.length - 1; i++) {
//        c = (MPObject) c.getVar(path[i]);
//      }
//      c.setVar(path[path.length - 1], rightValue.eval(scope));
//    }

    return null;
  }
}
