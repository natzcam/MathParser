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

  public EvalException(String message, Scope scope, Expression expression) {
    super(message + ":" + System.lineSeparator() + ToStringBuilder.reflectionToString(scope) + System.lineSeparator() + ToStringBuilder.reflectionToString(expression));
  }

  public EvalException(String message, Scope scope) {
    super(message + ":" + System.lineSeparator() + ToStringBuilder.reflectionToString(scope));
  }
}
