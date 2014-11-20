/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.instance.MPObject;
import nac.mp.type.instance.MPString;

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
  public MPObject eval(Scope scope, ObjectStore store) {
    return value;
  }
}
