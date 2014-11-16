/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.ast.Scope;

/**
 *
 * @author user
 */
public class MPModelObject extends MPBaseObject {

  private final MPModel model;
  private MPInteger id;

  public MPModelObject(Scope parent, MPModel creator) {
    super(parent, creator);
    this.model = creator;
    this.id = null;
  }

  public MPModel getModel() {
    return model;
  }

  public MPInteger getId() {
    return id;
  }

  public void setId(MPInteger id) {
    this.id = id;
  }

  public MPReference getReference() {
    return new MPReference(parent, creator, this);
  }

  @Override
  public String toString() {
    return "modelobject:" + model.getName() + ":" + id;
  }

  @Override
  public MPObject isEqual(MPObject right) {
    switch (right.getHint()) {
      case MODEL_OBJECT:
        MPModelObject mo = (MPModelObject) right;
        return new MPBoolean(id.getInt() == mo.getId().getInt() && model == mo.getModel());
    }
    return new MPBoolean(false);
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case MODEL_OBJECT:
        MPModelObject mo = (MPModelObject) right;
        return new MPBoolean(id.getInt() != mo.getId().getInt() || model != mo.getModel());
    }
    return new MPBoolean(false);
  }

  @Override
  public Hint getHint() {
    return Hint.MODEL_OBJECT;
  }
}
