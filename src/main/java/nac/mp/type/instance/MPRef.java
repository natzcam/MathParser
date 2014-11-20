/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type.instance;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Scope;
import nac.mp.type.Creator;
import nac.mp.type.Type;

/**
 *
 * @author camomon
 */
public class MPRef extends MPObject {

  transient private MPModelObj mpModelObj;
  private final Long id;
  private final String modelName;

  public MPRef(Scope parent, Creator creator, MPModelObj obj) {
    super(parent, creator);
    this.mpModelObj = obj;
    this.id = obj.getIdLong();
    this.modelName = obj.getModel().getName();
  }

  public Long getId() {
    return id;
  }

  public String getModelName() {
    return modelName;
  }

  @Override
  public Type getType() {
    return Type.REF;
  }

  @Override
  public void setLocalVar(String name, MPObject value) {
    mpModelObj.setLocalVar(name, value);
  }

  @Override
  public void declareLocalVar(String name, MPObject defaultValue) throws EvalException {
    mpModelObj.declareLocalVar(name, defaultValue);
  }

  @Override
  public boolean containsVar(String name, ObjectStore store) {
    if (mpModelObj == null) {
      mpModelObj = store.dereference(this);
    }
    return mpModelObj.containsVar(name, store);
  }

  @Override
  public MPObject getVar(String name, ObjectStore store) {
    if (mpModelObj == null) {
      mpModelObj = store.dereference(this);
    }
    return mpModelObj.getVar(name, store);
  }

  @Override
  public Set<String> getLocalVarKeys() {
    return mpModelObj.getLocalVarKeys();
  }

  @Override
  public Collection<MPObject> getLocalVarValues() {
    return mpModelObj.getLocalVarValues();
  }

  @Override
  public void setLocalVars(Map<String, MPObject> vars) {
    mpModelObj.setLocalVars(vars);
  }

  @Override
  public void setVar(String name, MPObject value, ObjectStore store) throws EvalException {
    if (mpModelObj == null) {
      mpModelObj = store.dereference(this);
    }
    mpModelObj.setVar(name, value, store);
  }

  @Override
  public String toString() {
    return getType() + ":" + modelName + "," + id;
  }

}
