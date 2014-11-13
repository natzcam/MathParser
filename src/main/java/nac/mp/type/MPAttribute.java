/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.EvalException;
import nac.mp.ast.Scope;
import nac.mp.ast.statement.AttributeDecl;
import nac.mp.ast.statement.AttributeDecl.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author camomon
 */
public class MPAttribute extends MPObject implements Creator {

  private static final Logger log = LogManager.getLogger(MPAttribute.class);
  private final AttributeDecl attrDecl;

  public MPAttribute(Scope parent, AttributeDecl attrDecl) {
    super(parent, null);
    this.attrDecl = attrDecl;
  }

  public String getName() {
    return attrDecl.getIdentifier();
  }

  @Override
  public MPObject.Hint getHint() {
    return MPObject.Hint.ATTRIBUTE;
  }

  @Override
  public String toString() {
    return "attr:" + attrDecl.getIdentifier();
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case ATTRIBUTE:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }
  
  public Type getType(){
    return attrDecl.getType();
  }

  @Override
  public MPObject newInstance() throws EvalException {
    switch (attrDecl.getType()) {
      case STRING:
        return new MPVoid();
      case INT:
        return new MPVoid();
      case BOOL:
        return new MPVoid();
      case FLOAT:
        return new MPVoid();
      case LIST:
        return new MPList(10, null);
      case REF:
        return new MPVoid();
      default:
        return new MPVoid();
    }
  }
}
