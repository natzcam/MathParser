/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.ast.WhereBlock;
import nac.mp.type.MPModel;
import nac.mp.type.instance.MPObject;
import nac.mp.type.instance.QueryPredicate;
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
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {
    return store.select((MPModel) modelName.eval(scope, store), new QueryPredicate(scope, whereBlock));
  }
}
