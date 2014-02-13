/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.ast.Statement;
import nac.mp.EvalException;
import nac.mp.type.MPObject;;
import nac.mp.type.MPObject.Hint;
import nac.mp.BasicScope;
import nac.mp.Scope;
import nac.mp.ast.Block;
import nac.mp.ast.Expression;

/**
 *
 * @author natz
 */
public class IfStatement implements Statement {

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
  public MPObject eval(Scope scope) throws EvalException {
    MPObject result = cond.eval(scope);
    if (result.getHint() == Hint.BOOLEAN) {
      if (result.getBoolean()) {
        scope = new BasicScope(scope);
        return ifBody.eval(scope);
      } else {
        if (elseBody != null) {
          scope = new BasicScope(scope);
          return elseBody.eval(scope);
        }
      }
    } else {
      throw new EvalException("Condition not boolean.");
    }
    return null;
  }
}
