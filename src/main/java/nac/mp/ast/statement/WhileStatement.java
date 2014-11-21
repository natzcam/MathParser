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
 * @author natz import nac.mp.type.Type;
 */
public class WhileStatement extends TokenAwareExpression {

  private final Expression cond;
  private final Expression body;

  public WhileStatement(Expression cond, Expression body) {
    this.cond = cond;
    this.body = body;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    scope = new BasicScope(scope);
    MPObject condValue = cond.eval(scope, store);
    if (condValue.getType() != Type.BOOL) {
      throw new EvalException("Condition not boolean", scope, this);
    }
    while (condValue.getBoolean()) {
      MPObject result = body.eval(scope, store);
      if (result != null) {
        return result;
      }
      condValue = cond.eval(scope, store);
    }
    return null;
  }
}
