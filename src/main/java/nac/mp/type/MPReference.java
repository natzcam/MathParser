/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.ast.Scope;

/**
 *
 * @author camomon
 */
public class MPReference extends MPObject {

  private Long id;
  private String modelName;

  public MPReference(Scope parent, Creator creator, MPModelObject obj) {
    super(parent, creator);
    this.id = obj.getId().getInt();
    this.modelName = obj.getModel().getName();
  }

  public Long getId() {
    return id;
  }

  public String getModelName() {
    return modelName;
  }

}
