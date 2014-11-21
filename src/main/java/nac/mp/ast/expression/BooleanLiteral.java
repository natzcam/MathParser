/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.ObjectStore;
import nac.mp.ast.Scope;
import nac.mp.ast.TokenAwareExpression;
import nac.mp.type.instance.MPBoolean;
import nac.mp.type.instance.MPObject;

/**
 *
 * @author nathaniel
 */
public class BooleanLiteral extends TokenAwareExpression {

  private final MPBoolean value;

  public BooleanLiteral(boolean value) {
    this.value = new MPBoolean(value);
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    return value;
  }
}
