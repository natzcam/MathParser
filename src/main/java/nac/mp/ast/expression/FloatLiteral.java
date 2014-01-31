/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.Type;
import nac.mp.ast.Factor;
import nac.mp.Scope;
import nac.mp.type.MPFloat;

/**
 *
 * @author natz
 */
public class FloatLiteral implements Factor {

  private final MPFloat value;

  public FloatLiteral(float value) {
    this.value = new MPFloat(value);
  }

  @Override
  public Type eval(Scope scope) {
    return value;
  }
}
