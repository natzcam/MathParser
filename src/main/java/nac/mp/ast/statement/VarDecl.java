/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.type.MPObject;;
import nac.mp.ast.Declaration;
import nac.mp.ast.Expression;
import nac.mp.Scope;
import nac.mp.type.MPVoid;

/**
 *
 * @author natz
 */
public class VarDecl implements Declaration {

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
  public MPObject eval(Scope scope) throws EvalException {
    if (defaultValue != null) {
      scope.declareVarLocal(identifier, defaultValue.eval(scope));
    } else {
      scope.declareVarLocal(identifier, new MPVoid());
    }
    return null;
  }
}
