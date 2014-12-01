/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type.instance;

import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Scope;
import nac.mp.type.MPModel;
import nac.mp.type.MPObject;
import nac.mp.type.Type;

/**
 *
 * @author user
 */
public class MPModelObj extends MPBaseObj {

  private final MPModel model;
  transient private ObjectStore objectStore = null;

  public MPModelObj(Scope parent, MPModel creator) {
    super(parent, creator);
    this.model = creator;
  }

  public MPModel getModel() {
    return model;
  }

  public MPObject getId() {
    return vars.get("id");
  }

  @Override
  public MPRef getReference() {
    return new MPRef(parent, creator, this);
  }

  @Override
  public boolean canBeRef() {
    return true;
  }

  public ObjectStore getObjectStore() {
    return objectStore;
  }

  public void setObjectStore(ObjectStore objectStore) {
    this.objectStore = objectStore;
  }

  @Override
  public void setVar(String name, MPObject value, ObjectStore store) {
    if (vars.containsKey(name)) {
      if (value.canBeRef()) {
        vars.put(name, value.getReference());
      } else {
        vars.put(name, value);
      }
    } else {
      throw new EvalException("Member not declared for ModeledObj: " + name, this);
    }

  }

  @Override
  public Type getType() {
    return Type.MODEL_OBJECT;
  }

  @Override
  public String toString() {
    return getType() + ":" + model + "," + vars;
  }

}
