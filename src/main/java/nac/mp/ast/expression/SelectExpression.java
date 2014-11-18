/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.MathParser;
import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.ast.WhereBlock;
import nac.mp.type.MPModel;
import nac.mp.type.QueryPredicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author nathaniel
 */
public class SelectExpression implements Expression {

  private static final Logger log = LogManager.getLogger(SelectExpression.class);
  private final Expression modelName;
  private final WhereBlock whereBlock;

  public SelectExpression(Expression modelName, WhereBlock whereBlock) {
    this.modelName = modelName;
    this.whereBlock = whereBlock;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    return MathParser.objectStore.select((MPModel) modelName.eval(scope), new QueryPredicate(scope, whereBlock));
  }
}
