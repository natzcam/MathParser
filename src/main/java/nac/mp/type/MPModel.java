/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Scope;
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
  
  public Map<String, MPAttribute> getAttributes() {
    return attributes;
  }
  
  @Override
  public MPObject newInstance(ObjectStore store) throws EvalException {
    MPModelObj obj = new MPModelObj(parent, this);
    for (String attrName : attributes.keySet()) {
      obj.declareLocalVar(attrName, attributes.get(attrName).newInstance(obj));
    }
    return obj;
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
    attributes.put(name, (MPAttribute) defaultValue);
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
  public boolean containsVar(String name) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
  @Override
  public MPObject getVar(String name) {
    return attributes.get(name);
  }
  
  @Override
  public void setVar(String name, MPObject value) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
