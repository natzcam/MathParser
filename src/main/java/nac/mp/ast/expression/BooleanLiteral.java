/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.MPObject;
import nac.mp.type.natv.MPBoolean;

/**
 *
 * @author nathaniel
 */
public class BooleanLiteral implements Expression {

  private final MPBoolean value;

  public BooleanLiteral(boolean value) {
    this.value = new MPBoolean(value);
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    return value;
  }
}
