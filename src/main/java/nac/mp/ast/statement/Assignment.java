/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.ParseException;
;
import nac.mp.ast.Expression;
import nac.mp.Scope;
import nac.mp.ast.LValue;
import nac.mp.type.MPObject;

/**
 *
 * @author nathaniel
 */


public class Assignment implements Expression {

  private LValue leftValue;
  private Expression rightValue;

  public void setLeftValue(Expression leftValue) throws ParseException {
    if (!(leftValue instanceof LValue)) {
      throw new ParseException("Not an Lvalue");
    }
    this.leftValue = (LValue) leftValue;
  }

  public void setRightValue(Expression rightValue) {
    this.rightValue = rightValue;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    leftValue.setValue(scope, rightValue.eval(scope));
    return null;
  }
}
