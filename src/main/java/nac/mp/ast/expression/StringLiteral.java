/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.Scope;
import nac.mp.type.MPString;

/**
 *
 * @author nathaniel
 */
public class StringLiteral  implements Expression {

  private final MPString value;

  public StringLiteral(String value) {
    this.value = new MPString(value);
  }

  @Override
  public MPObject eval(Scope scope) {
    return value;
  }
}
