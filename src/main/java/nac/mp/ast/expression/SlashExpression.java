/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.Type;
import nac.mp.ast.BinaryExpression;
import nac.mp.Scope;

/**
 *
 * @author user
 */
public class SlashExpression extends BinaryExpression {

  @Override
  public Type eval(Scope scope) throws EvalException {
    Type leftValue = left.eval(scope);
    Type rightValue = right.eval(scope);
    return leftValue.slash(rightValue);
  }
}
