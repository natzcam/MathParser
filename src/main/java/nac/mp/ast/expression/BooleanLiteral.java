/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.ObjectStore;
import nac.mp.ast.Scope;
import nac.mp.ast.TokenAwareExpression;
import nac.mp.type.MPObject;
import nac.mp.type.instance.MPBoolean;

/**
 *
 * @author nathaniel
 */
public class BooleanLiteral extends TokenAwareExpression {

  private final MPBoolean value;

  public BooleanLiteral(boolean value) {
    this.value = MPBoolean.valueOf(value);
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    return value;
  }
}
