/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.ast.Statement;
import nac.mp.EvalException;
import nac.mp.Type;
import nac.mp.ast.Expression;
import nac.mp.Scope;
import nac.mp.type.MPVoid;

/**
 *
 * @author natz
 */
public class Return implements Statement {

  private final Expression expression;

  public Return(Expression expression) {
    this.expression = expression;
  }

  @Override
  public Type eval(Scope scope) throws EvalException {
    if (expression == null) {
      return new MPVoid();
    } else {
      return expression.eval(scope);
    }
  }
}
