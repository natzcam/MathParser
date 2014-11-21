/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import nac.mp.ast.Scope;
import nac.mp.ast.Expression;

/**
 *
 * @author user
 */
public class EvalException extends Exception {

  public EvalException(String message, Scope scope, Expression expression) {
    super(message + ":" + System.lineSeparator() + scope + System.lineSeparator() + expression);
  }

  public EvalException(String message, Scope scope) {
    super(message + ":" + System.lineSeparator() + scope);
  }

  public EvalException(String message, Throwable cause) {
    super(message, cause);
  }
}
