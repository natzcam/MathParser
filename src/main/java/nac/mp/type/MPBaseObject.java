/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.ast.Scope;

/**
 *
 * @author user
 */
public class MPBaseObject extends MPObject implements Scope {

  private final Map<String, MPObject> vars = new HashMap<>();

  public MPBaseObject(Scope parent, Creator creator) {
    super(parent, creator);
  }

  @Override
  public String toString() {
    return vars.toString();
  }

  @Override
  public void setLocalVar(String name, MPObject value) {
    vars.put(name, value);
  }

  @Override
  public void declareLocalVar(String name, MPObject defaultValue) throws EvalException {
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
  public void setVar(String name, MPObject value) {
    vars.put(name, value);
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
  public Set<String> getLocalVarKeys() {
    return vars.keySet();
  }

  @Override
  public Collection<MPObject> getLocalVarValues() {
    return vars.values();
  }

  @Override
  public void setLocalVars(Map<String, MPObject> vars) {
    this.vars.putAll(vars);
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case OBJECT:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }

  @Override
  public MPObject.Hint getHint() {
    return MPObject.Hint.OBJECT;
  }
}
