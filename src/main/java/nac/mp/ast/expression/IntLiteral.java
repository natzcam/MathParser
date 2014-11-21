/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.ObjectStore;
import nac.mp.ast.Scope;
import nac.mp.ast.TokenAwareExpression;
import nac.mp.type.instance.MPInteger;
import nac.mp.type.instance.MPObject;

/**
 *
 * @author user
 */
public class IntLiteral extends TokenAwareExpression {

  private final MPInteger value;

  public IntLiteral(long value) {
    this.value = new MPInteger(value);
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    return value;
  }
}
