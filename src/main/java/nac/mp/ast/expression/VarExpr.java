/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.ObjectStore;
import nac.mp.ast.LValue;
import nac.mp.ast.Scope;
import nac.mp.type.MPObject;

/**
 *
 * @author natz
 */
public class VarExpr extends LValue {

  private final String id;

  public VarExpr(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    return scope.getVar(id, store);
  }

  @Override
  public void setValue(Scope scope, MPObject value, ObjectStore store) {
    scope.setVar(id, value, store);
  }
}
