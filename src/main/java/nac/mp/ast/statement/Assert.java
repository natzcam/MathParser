/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.ast.TokenAwareExpression;
import nac.mp.type.MPObject;
import nac.mp.type.Type;

/**
 *
 *
 */
public class Assert extends TokenAwareExpression {

  private final Expression cond;

  public Assert(Expression cond) {
    this.cond = cond;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    MPObject result = cond.eval(scope, store);
    if (result.getType() == Type.BOOL) {
      if (!result.getBoolean()) {
        throw new EvalException("Assertion failed", scope, cond);
      }
    }
    return null;
  }
}
