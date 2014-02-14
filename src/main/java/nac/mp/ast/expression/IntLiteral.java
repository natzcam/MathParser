/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.Scope;
import nac.mp.type.MPInteger;

/**
 *
 * @author user
 */
public class IntLiteral implements Expression {

  private final MPInteger value;

  public IntLiteral(int value) {
    this.value = new MPInteger(value);
  }

  @Override
  public MPObject eval(Scope scope) {
    return value;
  }
}
