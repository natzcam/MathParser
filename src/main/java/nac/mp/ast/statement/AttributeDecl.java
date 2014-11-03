/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.Scope;
import nac.mp.type.MPAttribute;
import nac.mp.type.MPModel;

/**
 *
 * @author natz
 */
public class AttributeDecl implements Expression {

  private final String type;
  private final String identifier;
  private Expression defaultValue = null;

  public AttributeDecl(String type, String identifier) {
    this.type = type;
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
    MPModel model = (MPModel) scope;
    MPAttribute attr = new MPAttribute(scope, identifier);
    scope.declareVarLocal(identifier, attr);
    model.getAttributes().add(attr);
    return null;
  }
}
