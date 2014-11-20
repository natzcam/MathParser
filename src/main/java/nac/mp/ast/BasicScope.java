/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.type.MPObject;

/**
 *
 * @author user
 */
public class BasicScope implements Scope {

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
  public void setLocalVar(String name, MPObject value) {
    vars.put(name, value);
  }

  @Override
  public void setLocalVars(Map<String, MPObject> vars) {
    vars.putAll(vars);
  }

  @Override
  public void declareLocalVar(String name, MPObject defaultValue) throws EvalException {
    if (vars.containsKey(name)) {
      throw new EvalException("Duplicate var: " + name, this);
    } else {
      vars.put(name, defaultValue);
    }
  }

  @Override
  public Set<String> getLocalVarKeys() {
    return vars.keySet();
  }

  @Override
  public Collection<MPObject> getLocalVarValues() {
    return vars.values();
  }

  @Override
  public boolean containsVar(String name, ObjectStore store) {
    if (vars.containsKey(name)) {
      return true;
    } else {
      return parent != null && parent.containsVar(name, store);
    }
  }

  @Override
  public void setVar(String name, MPObject value, ObjectStore store) throws EvalException {
    if (vars.containsKey(name)) {
      vars.put(name, value);
    } else if (parent != null) {
      parent.setVar(name, value, store);
    } else {
      throw new EvalException("Var not declared: " + name, value);
    }
  }

  @Override
  public String toString() {
    return vars.toString();
  }

  @Override
  public MPObject getVar(String name, ObjectStore store) {
    MPObject result = vars.get(name);
    if (result == null && parent != null) {
      result = parent.getVar(name, store);
    }
    return result;
  }
}
