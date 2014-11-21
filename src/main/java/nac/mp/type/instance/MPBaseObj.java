/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type.instance;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Scope;
import nac.mp.type.Creator;
import nac.mp.type.MPObject;
import nac.mp.type.Type;

/**
 *
 * @author user
 */
public class MPBaseObj extends MPObject implements Scope {

  protected final Map<String, MPObject> vars = new HashMap<>();

  public MPBaseObj(Scope parent, Creator creator) {
    super(parent, creator);
  }

  @Override
  public Type getType() {
    return Type.BASE_OBJECT;
  }

  @Override
  public MPObject getVar(String name, ObjectStore store) {
    if (vars.containsKey(name)) {
      return vars.get(name);
    } else if (parent != null) {
      return parent.getVar(name, store);
    } else {
      throw new EvalException("Member not declared: " + name, this);
    }
  }

  @Override
  public boolean containsVar(String name, ObjectStore store) {
    return vars.containsKey(name) ? true : parent != null && parent.containsVar(name, store);
  }

  @Override
  public void declareVar(String name, MPObject defaultValue) {
    if (vars.containsKey(name)) {
      throw new EvalException("Duplicate member: " + name, defaultValue);
    } else {
      vars.put(name, defaultValue);
    }
  }

  @Override
  public void setVar(String name, MPObject value, ObjectStore store) {
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
  public void setLocalVar(String name, MPObject value) {
    vars.put(name, value);
  }

  @Override
  public String toString() {
    return getType() + ":" + vars;
  }

}
