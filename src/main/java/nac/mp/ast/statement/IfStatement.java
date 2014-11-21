/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.BasicScope;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.ast.TokenAwareExpression;
import nac.mp.type.MPObject;
import nac.mp.type.Type;

/**
 *
 * @author natz
 import nac.mp.type.Type;
*/
public class IfStatement extends TokenAwareExpression {

  private final Expression cond;
  private final Expression ifBody;
  private Expression elseBody;

  public IfStatement(Expression cond, Expression ifBody) {
    this.cond = cond;
    this.ifBody = ifBody;
  }

  public void setElseBody(Expression elseBody) {
    this.elseBody = elseBody;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    MPObject result = cond.eval(scope, store);
    if (result.getType() == Type.BOOL) {
      if (result.getBoolean()) {
        scope = new BasicScope(scope);
        return ifBody.eval(scope, store);
      } else {
        if (elseBody != null) {
          scope = new BasicScope(scope);
          return elseBody.eval(scope, store);
        }
      }
    } else {
      throw new EvalException("Condition not boolean", scope, cond);
    }
    return null;
  }
}
