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
  public void setLocalVar(String name, MPObject value) {
    vars.put(name, value);
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
  public boolean containsVar(String name, ObjectStore store) {
    if (vars.containsKey(name)) {
      return true;
    } else {
      return parent != null && parent.containsVar(name, store);
    }
  }

  @Override
  public void setVar(String name, MPObject value, ObjectStore store) {
    vars.put(name, value);
  }

  @Override
  public MPObject getVar(String name, ObjectStore store) {
    MPObject result = vars.get(name);
    if (result == null && parent != null) {
      result = parent.getVar(name, store);
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

}
