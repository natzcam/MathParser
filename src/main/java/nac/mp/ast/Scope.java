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
import nac.mp.type.MPObject;

/**
 *
 * @author user
 */
public interface Scope extends Serializable {

  public void setVarLocal(String name, MPObject value);

  public void declareVarLocal(String name, MPObject defaultValue) throws EvalException;

  public Scope getParent();

  public boolean containsVar(String name);

  public MPObject getVar(String name);

  public Set<String> getVarKeys();

  public Collection<MPObject> getVarValues();

  public void setVar(String name, MPObject value) throws EvalException;

}
