/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import nac.mp.ast.Scope;

/**
 *
 * @author user
 */
public class EvalException extends Exception {

  private final Scope scope;

  public EvalException(String message, Scope scope) {
    super(message + scope);
    this.scope = scope;
  }

}
