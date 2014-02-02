/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp;

import java.util.Set;

/**
 *
 * @author user
 */
public interface Scope {

  public void setVarLocal(String name, Type value);

  public void declareVarLocal(String name, Type defaultValue) throws EvalException;

  public Scope getParent();

  public boolean containsVar(String name);

  public Type getVar(String name);

  public Set<String> getVarKeys();

  public void setVar(String name, Type value) throws EvalException;
}
