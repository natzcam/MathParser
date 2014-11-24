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
  public MPObject getVar(String name, ObjectStore store) {
    if (vars.containsKey(name)) {
      return vars.get(name);
    } else if (parent != null) {
      return parent.getVar(name, store);
    } else {
      throw new EvalException("Var not declared: " + name, this);
    }
  }

  @Override
  public boolean containsVar(String name, ObjectStore store) {
    return vars.containsKey(name) ? true : parent != null && parent.containsVar(name, store);
  }

  @Override
  public void declareVar(String name, MPObject defaultValue) {
    if (vars.containsKey(name)) {
      throw new EvalException("Duplicate var: " + name, this);
    } else {
      vars.put(name, defaultValue);
    }
  }

  @Override
  public void setVar(String name, MPObject value, ObjectStore store) {
    if (vars.containsKey(name)) {
      vars.put(name, value);
    } else if (parent != null) {
      parent.setVar(name, value, store);
    } else {
      throw new EvalException("Var not declared: " + name, this);
    }
  }

  @Override
  public void setLocalVar(String name, MPObject value) {
    vars.put(name, value);
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
  public String toString() {
    return "SCOPE: " + vars.toString();
  }

}
