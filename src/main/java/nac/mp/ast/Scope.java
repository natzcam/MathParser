/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.type.MPObject;

/**
 *
 * @author user
 */
public interface Scope extends Serializable {

  public Scope getParent();

  public MPObject getVar(String name, ObjectStore store);

  public boolean containsVar(String name, ObjectStore store);

  public void declareVar(String name, MPObject defaultValue);

  public void setVar(String name, MPObject value, ObjectStore store);

  public Set<String> getLocalVarKeys();

  public Collection<MPObject> getLocalVarValues();

  public void setLocalVar(String name, MPObject value) throws EvalException;

}
