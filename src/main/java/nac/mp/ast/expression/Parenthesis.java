/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.Type;
import nac.mp.ast.Expression;
import nac.mp.ast.Factor;
import nac.mp.Scope;

/**
 *
 * @author natz
 */
public class Parenthesis implements Factor {

  private final Expression expression;

  public Parenthesis(Expression expression) {
    this.expression = expression;
  }

  @Override
  public Type eval(Scope scope) throws EvalException {
    return expression.eval(scope);
  }
}
