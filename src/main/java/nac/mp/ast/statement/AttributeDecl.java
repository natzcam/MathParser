/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.MPAttribute;
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
  private Expression defaultValue = null;

  public static enum Type {

    STRING,
    FLOAT,
    BOOL,
    INT,
    REF,
    LIST,
  }

  public AttributeDecl(String type, String metaType, String identifier) {
    this.type = Type.valueOf(type.toUpperCase());
    this.metaType = metaType;
    this.identifier = identifier;
  }

  public void setDefaultValue(Expression defaultValue) {
    this.defaultValue = defaultValue;
  }

  public String getIdentifier() {
    return identifier;
  }

  public String getMetaType() {
    return metaType;
  }

  public boolean isNative() {
    return type != Type.LIST && type != Type.REF;
  }

  public Type getType() {
    return type;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPAttribute attr = new MPAttribute(scope, this);
    scope.declareLocalVar(identifier, attr);
    return null;
  }
}
