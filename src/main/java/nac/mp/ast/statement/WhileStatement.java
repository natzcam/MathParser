/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.ast.Statement;
import nac.mp.EvalException;
import nac.mp.Type;
import nac.mp.Type.Hint;
import nac.mp.BasicScope;
import nac.mp.Scope;
import nac.mp.ast.Block;
import nac.mp.ast.Expression;

/**
 *
 * @author natz
 */
public class WhileStatement implements Statement {

  private final Expression cond;
  private final Block body;

  public WhileStatement(Expression cond, Block body) {
    this.cond = cond;
    this.body = body;
  }

  @Override
  public Type eval(Scope scope) throws EvalException {
    scope = new BasicScope(scope);
    Type condValue = cond.eval(scope);
    if (condValue.getHint() != Hint.BOOLEAN) {
      throw new EvalException("Condition not boolean");
    }
    while (condValue.getBoolean()) {
      Type result = body.eval(scope);
      if (result != null) {
        return result;
      }
      condValue = cond.eval(scope);
    }
    return null;
  }
}
