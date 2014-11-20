/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.instance.MPObject;
import nac.mp.type.instance.MPInteger;

/**
 *
 * @author user
 */
public class IntLiteral implements Expression {

  private final MPInteger value;

  public IntLiteral(long value) {
    this.value = new MPInteger(value);
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    return value;
  }
}
