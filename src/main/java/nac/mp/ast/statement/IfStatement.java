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
public class IfStatement implements Expression {

  private final Expression cond;
  private final Block ifBody;
  private Block elseBody;

  public IfStatement(Expression cond, Block ifBody) {
    this.cond = cond;
    this.ifBody = ifBody;
  }

  public void setElseBody(Block elseBody) {
    this.elseBody = elseBody;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {
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
      throw new EvalException("Condition not boolean.", scope, this);
    }
    return null;
  }
}
