/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.type.instance.MPList;
import nac.mp.type.instance.MPObject;
import nac.mp.type.instance.MPModelObj;
import nac.mp.type.instance.MPRefList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Scope;
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

  public Type getValueType() {
    return type;
  }

  public MPObject newInstance(MPModelObj modObj) throws EvalException {
    switch (type) {
      case STRING:
        return new MPVoid();
      case INT:
        return new MPVoid();
      case BOOL:
        return new MPVoid();
      case FLOAT:
        return new MPVoid();
      case LIST:
        return new MPList();
      case REF:
        return new MPVoid();
      case REFLIST:
        return new MPRefList(metaType);
      default:
        return new MPVoid();
    }
  }

  @Override
  public void setLocalVar(String name, MPObject value) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setLocalVars(Map<String, MPObject> vars) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void declareLocalVar(String name, MPObject defaultValue) throws EvalException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Set<String> getLocalVarKeys() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Collection<MPObject> getLocalVarValues() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean containsVar(String name, ObjectStore store) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public MPObject getVar(String name, ObjectStore store) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setVar(String name, MPObject value, ObjectStore store) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
