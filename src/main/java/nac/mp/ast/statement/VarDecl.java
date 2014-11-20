/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.instance.MPObject;
import nac.mp.type.instance.MPVoid;

/**
 *
 * @author natz
 */
public class VarDecl implements Expression {

  private final String identifier;
  private Expression defaultValue = null;

  public VarDecl(String identifier) {
    this.identifier = identifier;
  }

  public void setDefaultValue(Expression defaultValue) {
    this.defaultValue = defaultValue;
  }

  public String getIdentifier() {
    return identifier;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {
    if (defaultValue != null) {
      scope.declareLocalVar(identifier, defaultValue.eval(scope, store));
    } else {
      scope.declareLocalVar(identifier, new MPVoid());
    }
    return null;
  }
}
