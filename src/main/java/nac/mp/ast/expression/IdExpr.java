/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.type.MPObject;;
import nac.mp.ast.Factor;
import nac.mp.Scope;
import nac.mp.type.MPObject;

/**
 *
 * @author natz
 */
public class IdExpr implements Factor {

  private final String id;

  public IdExpr(String id) {
    this.id = id;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    return scope.getVar(id);
  }
}
