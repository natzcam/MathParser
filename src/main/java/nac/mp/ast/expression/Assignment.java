/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ParseException;
import nac.mp.ast.Expression;
import nac.mp.ast.LValue;
import nac.mp.ast.Scope;
import nac.mp.type.instance.MPObject;

/**
 *
 * @author nathaniel
 */


public class Assignment implements Expression {

  private LValue leftValue;
  private Expression rightValue;

  public void setLeftValue(Expression leftValue) throws ParseException {
    this.leftValue = (LValue) leftValue;
  }

  public void setRightValue(Expression rightValue) {
    this.rightValue = rightValue;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {
    leftValue.setValue(scope, rightValue.eval(scope, store), store);
    return null;
  }
}
