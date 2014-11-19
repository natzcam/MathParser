/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.MPAttribute;
import nac.mp.type.MPModel;
import nac.mp.type.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author natz
 */
public class AttributeDecl implements Expression {

  private static final Logger log = LogManager.getLogger(AttributeDecl.class);
  private final Type type;
  private final String metaType;
  private final String identifier;

  public AttributeDecl(String type, String metaType, String identifier) {
    this.type = metaType == null ? Type.LIST : Type.REF_LIST;
    this.metaType = metaType;
    this.identifier = identifier;
  }

  public String getIdentifier() {
    return identifier;
  }

  public String getMetaType() {
    return metaType;
  }

  public Type getType() {
    return type;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {
   
    MPAttribute attr = new MPAttribute(scope, type, metaType, identifier);
    scope.declareLocalVar(identifier, attr);
    return null;
  }
}
