/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type.instance;

import nac.mp.ObjectStore;
import nac.mp.ast.Scope;
import nac.mp.type.MPModel;
import nac.mp.type.Type;

/**
 *
 * @author user
 */
public class MPModelObj extends MPBaseObj {

  private final MPModel model;

  public MPModelObj(Scope parent, MPModel creator) {
    super(parent, creator);
    this.model = creator;
  }

  public MPModel getModel() {
    return model;
  }

  public MPInteger getId() {
    return (MPInteger) vars.get("id");
  }

  public Long getIdLong() {
    return vars.get("id").getInt();
  }

  public void setId(MPInteger id) {
    vars.put("id", id);
  }

  public MPRef getReference() {
    return new MPRef(parent, creator, this);
  }

  @Override
  public void setVar(String name, MPObject value, ObjectStore store) {
    if (value instanceof MPModelObj) {
      vars.put(name, ((MPModelObj) value).getReference());
    } else {
      vars.put(name, value);
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
