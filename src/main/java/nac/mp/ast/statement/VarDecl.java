/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.ast.TokenAwareExpression;
import nac.mp.type.MPObject;
import nac.mp.type.instance.MPVoid;

/**
 *
 * @author natz
 */
public class VarDecl extends TokenAwareExpression {

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
  public MPObject eval(Scope scope, ObjectStore store) {
    if (defaultValue != null) {
      scope.declareVar(identifier, defaultValue.eval(scope, store));
    } else {
      scope.declareVar(identifier, MPVoid.VOID);
    }
    return null;
  }
}
