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
public class MPModelObject extends MPObject {

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
  public Hint getHint() {
    return Hint.MODEL_OBJECT;
  }

  @Override
  public void setLocalVar(String name, MPObject value) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setLocalVars(Map<String, MPObject> vars) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void declareLocalVar(String name, MPObject defaultValue) throws EvalException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Set<String> getLocalVarKeys() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Collection<MPObject> getLocalVarValues() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean containsVar(String name) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public MPObject getVar(String name) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setVar(String name, MPObject value){
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
