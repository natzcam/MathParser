/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.Scope;

/**
 *
 * @author nathaniel
 */
public class Assert implements Expression {

  private final Expression cond;

  public Assert(Expression cond) {
    this.cond = cond;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPObject result = cond.eval(scope);
    if (result.getHint() == MPObject.Hint.BOOLEAN) {
      if (!result.getBoolean()) {
        throw new EvalException("Assertion failed.");
      }
    }
    return null;
  }
}
