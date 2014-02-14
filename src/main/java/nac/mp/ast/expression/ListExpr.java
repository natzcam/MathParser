/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.ast.Expression;
import nac.mp.type.MPInteger;
import nac.mp.type.MPList;
import nac.mp.type.MPObject;

/**
 *
 * @author user
 */
public class ListExpr implements Expression {

  private final Expression expression;
  private Expression index;

  public ListExpr(Expression expression) {
    this.expression = expression;
  }

  public void setIndex(Expression index) {
    this.index = index;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPList list = (MPList) expression.eval(scope);
    return list.get((MPInteger) index.eval(scope));
  }

}
