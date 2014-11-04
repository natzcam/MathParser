/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.type.MPObject;
import nac.mp.ast.Scope;
import nac.mp.ast.BinaryExpression;

/**
 *
 * @author user
 */
public class MoreThanEqual extends BinaryExpression {

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPObject leftValue = left.eval(scope);
    MPObject rightValue = right.eval(scope);
    return leftValue.mte(rightValue);
  }

}
