/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.ast.TokenAwareExpression;
import nac.mp.type.MPObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author nathaniel
 */
public class Print extends TokenAwareExpression {

  private static final Logger log = LogManager.getLogger(Print.class);
  private final Expression expression;

  public Print(Expression expression) {
    this.expression = expression;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    log.info(expression.eval(scope, store));
    return null;
  }
}
