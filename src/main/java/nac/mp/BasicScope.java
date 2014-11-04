/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import nac.mp.type.MPObject;

/**
 *
 * @author user
 */
public class BasicScope implements Scope{

  private final Scope parent;
  private final Map<String, MPObject> vars = new HashMap<>();

  public BasicScope(Scope parent) {
    this.parent = parent;
  }

  @Override
  public Scope getParent() {
    return parent;
  }

  @Override
  public void setVarLocal(String name, MPObject value) {
    vars.put(name, value);
  }

  @Override
  public void declareVarLocal(String name, MPObject defaultValue) throws EvalException {
    if (vars.containsKey(name)) {
      throw new EvalException("Duplicate var: " + name);
    } else {
      vars.put(name, defaultValue);
    }
  }

  @Override
  public boolean containsVar(String name) {
    if (vars.containsKey(name)) {
      return true;
    } else {
      return parent != null && parent.containsVar(name);
    }
  }

  @Override
  public void setVar(String name, MPObject value) throws EvalException {
    if (vars.containsKey(name)) {
      vars.put(name, value);
    } else if (parent != null) {
      parent.setVar(name, value);
    } else {
      throw new EvalException("Var not declared: " + name);
    }
  }

  @Override
  public MPObject getVar(String name) {
    MPObject result = vars.get(name);
    if (result == null && parent != null) {
      result = parent.getVar(name);
    }
    return result;
  }

  @Override
  public Set<String> getVarKeys() {
    return vars.keySet();
  }
  
    @Override
  public Collection<MPObject> getVarValues() {
    return vars.values();
  }

}
