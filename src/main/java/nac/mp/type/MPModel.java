/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.LinkedHashMap;
import java.util.Map;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Scope;
import nac.mp.type.instance.MPModelObj;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author camomon
 */
public class MPModel extends MPObject implements Creator {

  private static final Logger log = LogManager.getLogger(MPModel.class);
  private final String name;
  private final Map<String, MPAttribute> attributes = new LinkedHashMap<>();

  public MPModel(Scope parent, String name) {
    super(parent, null);
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public Type getType() {
    return Type.MODEL;
  }

  @Override
  public String toString() {
    return getType() + ":" + name + "," + attributes;
  }

  public Map<String, MPAttribute> getAttributes() {
    return attributes;
  }

  @Override
  public MPObject newInstance(ObjectStore store) {
    MPModelObj obj = new MPModelObj(parent, this);
    for (String attrName : attributes.keySet()) {
      MPAttribute attr = attributes.get(attrName);
      obj.declareVar(attrName, attr.newInstance(obj));
    }
    return obj;
  }

  @Override
  public MPObject getVar(String name, ObjectStore store) {
    if (attributes.containsKey(name)) {
      return attributes.get(name);
    } else {
      throw new EvalException("Member not declared: " + name, this);
    }
  }

  @Override
  public boolean containsVar(String name, ObjectStore store) {
    return attributes.containsKey(name);
  }

  @Override
  public void declareVar(String name, MPObject defaultValue) {
    if (attributes.containsKey(name)) {
      throw new EvalException("Duplicate member: " + name, this);
    } else {
      attributes.put(name, (MPAttribute) defaultValue);
    }
  }
}
