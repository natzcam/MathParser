/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.EvalException;
import nac.mp.ast.Scope;
import nac.mp.ast.statement.AttributeDecl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author camomon
 */
public class MPAttribute extends MPObject implements Creator {

  private static final Logger log = LogManager.getLogger(MPAttribute.class);
  private final AttributeDecl attrDecl;
  private final String type;
  private final String name;

  public MPAttribute(Scope parent, AttributeDecl attrDecl) {
    super(parent, null);
    this.attrDecl = attrDecl;
    this.type = attrDecl.getType();
    this.name = attrDecl.getType();
  }

  public String getName() {
    return name;
  }

  @Override
  public MPObject.Hint getHint() {
    return MPObject.Hint.ATTRIBUTE;
  }

  @Override
  public String toString() {
    return "attr:" + name;
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case ATTRIBUTE:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }

  @Override
  public MPObject newInstance() throws EvalException {
    switch (type) {
      case "string":
        return new MPVoid();
      case "int":
        return new MPVoid();
      case "bool":
        return new MPVoid();
      case "float":
        return new MPVoid();
      case "list":
        return new MPVoid();
      default:
        return new MPVoid();
    }
  }
}
