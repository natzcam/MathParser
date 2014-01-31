/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.Type;
import nac.mp.ast.Factor;
import nac.mp.Scope;
import nac.mp.type.MPBoolean;

/**
 *
 * @author nathaniel
 */
public class BooleanLiteral implements Factor {

  private final MPBoolean value;

  public BooleanLiteral(boolean value) {
    this.value = new MPBoolean(value);
  }

  @Override
  public Type eval(Scope scope) {
    return value;
  }
}
