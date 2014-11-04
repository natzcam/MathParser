/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;

/**
 *
 * @author nathaniel
 */
public class Print implements Expression {

  private final Expression expression;

  public Print(Expression expression) {
    this.expression = expression;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    System.out.println(expression.eval(scope));
    return null;
  }
}
