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
public class Assert implements Statement {

  private final Expression cond;

  public Assert(Expression cond) {
    this.cond = cond;
  }

  @Override
  public Type eval(Scope scope) throws EvalException {
    Type result = cond.eval(scope);
    if (result.getHint() == Type.Hint.BOOLEAN) {
      if (!result.getBoolean()) {
        throw new EvalException("Assertion failed.");
      }
    }
    return null;
  }
}
