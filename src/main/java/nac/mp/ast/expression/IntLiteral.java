/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.Type;
import nac.mp.ast.Factor;
import nac.mp.Scope;
import nac.mp.type.MPInteger;

/**
 *
 * @author user
 */
public class IntLiteral implements Factor {

  private final MPInteger value;

  public IntLiteral(int value) {
    this.value = new MPInteger(value);
  }

  @Override
  public Type eval(Scope scope) {
    return value;
  }
}
