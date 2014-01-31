/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.Type;
import nac.mp.ast.Factor;
import nac.mp.Scope;
import nac.mp.type.MPString;

/**
 *
 * @author nathaniel
 */
public class StringLiteral  implements Factor {

  private final MPString value;

  public StringLiteral(String value) {
    this.value = new MPString(value);
  }

  @Override
  public Type eval(Scope scope) {
    return value;
  }
}
