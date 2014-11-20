/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.BasicScope;
import nac.mp.ast.Block;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.instance.MPObject;
import nac.mp.type.Type;

/**
 *
 * @author natz
 */
public class WhileStatement implements Expression {

  private final Expression cond;
  private final Block body;

  public WhileStatement(Expression cond, Block body) {
    this.cond = cond;
    this.body = body;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {
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
