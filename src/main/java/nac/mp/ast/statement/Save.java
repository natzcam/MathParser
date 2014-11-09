/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.store.frostbyte.FrostByte;
import nac.mp.type.MPModelObject;

/**
 *
 * @author nathaniel
 */
public class Save implements Expression {

  private final Expression expression;
  private final FrostByte fb;

  public Save(FrostByte fb, Expression expression) {
    this.expression = expression;
    this.fb = fb;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPModelObject obj = (MPModelObject) expression.eval(scope);
    fb.save(obj);
    return null;
  }
}
