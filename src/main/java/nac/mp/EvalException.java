/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.Type;

/**
 *
 * @author user
 */
public class EvalException extends RuntimeException {

  private Tokenizer tokenizer;
  private Scope scope;
  private Expression expression;

  public void setTokenizer(Tokenizer tokenizer) {
    this.tokenizer = tokenizer;
  }

  private String getFileName() {
    return tokenizer.getCurrentFile().toString();
  }
  
  @Override
  public String getMessage() {
    StringBuilder sb = new StringBuilder();
    sb.append(super.getMessage()).append(" at ").append(getFileName()).append(System.lineSeparator());
    if (scope != null) {
      sb.append(scope).append(System.lineSeparator());
    }
    if (expression != null) {
      sb.append(expression).append(System.lineSeparator());
    }
    return sb.toString();
  }

  public EvalException(String message, Scope scope, Expression expression) {
    super(message);
    this.scope = scope;
    this.expression = expression;
  }

  public EvalException(String message, Scope scope) {
    super(message);
    this.scope = scope;
  }

  public EvalException(String message, Throwable cause) {
    super(message, cause);
  }

  public EvalException(String message, Type type, String op, Type rtype) {
    super(message + ": " + type + " " + op + " " + rtype);
  }

  public EvalException(String message, Type type) {
    super(message + ": " + type);
  }
}
