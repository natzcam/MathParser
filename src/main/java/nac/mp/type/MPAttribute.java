/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.ast.Scope;
import nac.mp.type.instance.MPList;
import nac.mp.type.instance.MPModelObj;
import nac.mp.type.instance.MPRefList;
import nac.mp.type.instance.MPVoid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author camomon
 */
public class MPAttribute extends MPObject {

  private static final Logger log = LogManager.getLogger(MPAttribute.class);
  private final Type type;
  private final String metaType;
  private final String name;

  public MPAttribute(Scope parent, Type type, String metaType, String name) {
    super(parent, null);
    this.type = type;
    this.metaType = metaType;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getMetaType() {
    return metaType;
  }

  @Override
  public Type getType() {
    return Type.ATTRIBUTE;
  }

  @Override
  public String toString() {
    return getType() + ":" + type + "," + metaType + "," + name;
  }

  public Type getValueType() {
    return type;
  }

  public MPObject newInstance(MPModelObj modObj) {
    switch (type) {
      case STRING:
        return MPVoid.VOID;
      case INT:
        return MPVoid.VOID;
      case BOOL:
        return MPVoid.VOID;
      case FLOAT:
        return MPVoid.VOID;
      case LIST:
        return new MPList();
      case REF:
        return MPVoid.VOID;
      case REFLIST:
        return new MPRefList(metaType);
      default:
        return MPVoid.VOID;
    }
  }
}
