/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.Type;

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
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {
    MPObject result = cond.eval(scope, store);
    if (result.getType() == Type.BOOL) {
      if (!result.getBoolean()) {
        throw new EvalException("Assertion failed.", scope, cond);
      }
    }
    return null;
  }
}
