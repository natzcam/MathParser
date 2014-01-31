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

/**
 *
 * @author nathaniel
 */
public class Print implements Statement {

  private final Expression expression;

  public Print(Expression expression) {
    this.expression = expression;
  }

  @Override
  public Type eval(Scope scope) throws EvalException {
    System.out.println(expression.eval(scope));
    return null;
  }
}
