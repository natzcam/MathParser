/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.type.MPObject;
import nac.mp.ast.BinaryExpression;
import nac.mp.Scope;
import nac.mp.ast.Expression;

/**
 *
 * @author user
 */


public class DotExpression implements Expression {
  
  private Expression left;
  private String id;
  
  public void setId(String id) {
    this.id = id;
  }
  
  public void setLeft(Expression left) {
    this.left = left;
  }
  
  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPObject lo = (MPObject) left.eval(scope);
    return lo.getVar(id);
  }
}
