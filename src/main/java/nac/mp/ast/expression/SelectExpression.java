/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.ast.WhereBlock;
import nac.mp.store.frostbyte.FrostByte;
import nac.mp.type.MPModelObject;
import nac.mp.type.QueryPredicate;

/**
 *
 * @author nathaniel
 */
public class SelectExpression implements Expression {

  private final String modelName;
  private final WhereBlock whereBlock;
  private final FrostByte fb;

  public SelectExpression(FrostByte fb, String modelName, WhereBlock whereBlock) {
    this.modelName = modelName;
    this.fb = fb;
    this.whereBlock = whereBlock;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    return new QueryPredicate(scope, whereBlock);
  }
}
