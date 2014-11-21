/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import nac.mp.ast.Expression;
import nac.mp.ast.Scope;

/**
 *
 * @author user
 */
public class EvalException extends RuntimeException {

  public EvalException(String message, Scope scope, Expression expression) {
    super(message + ":" + System.lineSeparator() + scope + System.lineSeparator() + expression);
  }

  public EvalException(String message, Scope scope) {
    super(message + ":" + System.lineSeparator() + scope);
  }

  public EvalException(String message, Throwable cause) {
    super(message, cause);
  }

  public EvalException(String message) {
    super(message);
  }
}
