/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.type.MPObject;
import nac.mp.ast.Scope;
import nac.mp.ast.BinaryExpression;

/**
 *
 * @author user
 */
public class MoreThan extends BinaryExpression {

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {
    MPObject leftValue = left.eval(scope, store);
    MPObject rightValue = right.eval(scope, store);
    return leftValue.mt(rightValue);
  }

}
