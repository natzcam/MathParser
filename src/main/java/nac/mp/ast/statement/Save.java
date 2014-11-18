/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.MathParser;
import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.MPModelObj;

/**
 *
 * @author nathaniel
 */
public class Save implements Expression {

  private final Expression expression;

  public Save(Expression expression) {
    this.expression = expression;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPModelObj obj = (MPModelObj) expression.eval(scope);
    MathParser.objectStore.save(obj);
    return null;
  }
}
