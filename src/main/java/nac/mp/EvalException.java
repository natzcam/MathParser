/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author user
 */
public class EvalException extends Exception {

  private Scope scope = null;
  private Expression expression = null;

  public EvalException(String message, Scope scope) {
    super(message + ToStringBuilder.reflectionToString(scope));
    this.scope = scope;
  }

  public EvalException(String message, Expression expression) {
    super(message + ToStringBuilder.reflectionToString(expression));
    this.expression = expression;
  }

}
