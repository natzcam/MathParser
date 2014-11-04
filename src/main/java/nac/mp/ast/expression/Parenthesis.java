/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.MPObject;

/**
 *
 * @author natz
 */
public class Parenthesis implements Expression {

  private final Expression expression;

  public Parenthesis(Expression expression) {
    this.expression = expression;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    return expression.eval(scope);
  }
}
