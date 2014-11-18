/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.ast.Scope;

/**
 *
 * @author user
 */
public class MPModelObject extends MPBaseObject {

  private final MPModel model;

  public MPModelObject(Scope parent, MPModel creator) {
    super(parent, creator);
    this.model = creator;
  }

  public MPModel getModel() {
    return model;
  }

  public MPInteger getId() {
    return (MPInteger) getVar("id");
  }

  public void setId(MPInteger id) {
    setLocalVar("id", id);
  }

  public MPReference getReference() {
    return new MPReference(parent, creator, this);
  }

  @Override
  public String toString() {
    return "modelobject:" + model.getName() + ":" + getId() + " : " + super.toString();
  }

//  @Override
//  public MPObject isEqual(MPObject right) {
//    switch (right.getHint()) {
//      case MODEL_OBJECT:
//        MPModelObject mo = (MPModelObject) right;
//        return new MPBoolean(getId().isEqual(mo.getId()).getBoolean() && model.isEqual(mo.getModel()).getBoolean());
//    }
//    return new MPBoolean(false);
//  }
//
//  @Override
//  public MPObject notEqual(MPObject right) {
//    switch (right.getHint()) {
//      case MODEL_OBJECT:
//        MPModelObject mo = (MPModelObject) right;
//        return new MPBoolean(getId().notEqual(mo.getId()).getBoolean() || model.notEqual(mo.getModel()).getBoolean());
//    }
//    return new MPBoolean(false);
//  }
  @Override
  public Hint getHint() {
    return Hint.MODEL_OBJECT;
  }
}
