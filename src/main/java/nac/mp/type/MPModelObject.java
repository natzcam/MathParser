/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.ast.Scope;
import nac.mp.store.frostbyte.FrostByte;

/**
 *
 * @author user
 */
public class MPModelObject extends MPObject {

  private final MPModel model;
  private FrostByte fb = null;

  public MPModelObject(Scope parent, MPModel creator) {
    super(parent, creator);
    this.model = creator;
  }

  public MPModel getModel() {
    return model;
  }

  public MPInteger getId() {
    return (MPInteger) vars.get("id");
  }

  public FrostByte getFb() {
    return fb;
  }

  public void setFb(FrostByte fb) {
    this.fb = fb;
  }

  public MPReference getReference() {
    return new MPReference(parent, creator, this);
  }
  
   @Override
  public String toString() {
    return hashCode() + ":" + vars.toString();
  }
}
