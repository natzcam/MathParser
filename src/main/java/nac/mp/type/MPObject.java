/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.Type;
import nac.mp.Scope;

/**
 *
 * @author user
 */
public class MPObject extends Type implements Scope, Serializable{
  
  private final Scope parent;
  private final Map<String, Type> vars = new HashMap<>();
  private final MPObject prototype;

  public MPObject(Scope parent, MPObject prototype) {
    this.parent = parent;
    this.prototype = prototype;
  }

  @Override
  public Type.Hint getHint() {
    return Type.Hint.OBJECT;
  }

  @Override
  public String toString() {
    return vars.toString();
  }

  @Override
  public Scope getParent() {
    return parent;
  }

  @Override
  public void setVarLocal(String name, Type value) {
    vars.put(name, value);
  }

  @Override
  public void declareVarLocal(String name, Type defaultValue) throws EvalException {
    if (vars.containsKey(name)) {
      throw new EvalException("Duplicate var: " + name);
    } else {
      vars.put(name, defaultValue);
    }
  }

  @Override
  public boolean containsVar(String name) {
    if (vars.containsKey(name)) {
      return true;
    } else if (prototype != null && prototype.containsVar(name)) {
      return true;
    } else {
      return parent != null && parent.containsVar(name);
    }
  }

  @Override
  public void setVar(String name, Type value) {
    setVarLocal(name, value);
  }

  @Override
  public Type getVar(String name) {
    // System.out.println(this + ".getVar " + name);
    Type result = vars.get(name);
    if (result == null && prototype != null) {
      result = prototype.getVar(name);
    }
    if (result == null && parent != null) {
      result = parent.getVar(name);
    }
    //System.out.println("result " + result);
    return result;
  }

  @Override
  public Type equal(Type right) {
    switch (right.getHint()) {
      case OBJECT:
        return new MPBoolean(this == right);
    }
    return new MPBoolean(false);
  }

  @Override
  public Type notEqual(Type right) {
    switch (right.getHint()) {
      case OBJECT:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }

  @Override
  public Set<String> getVarKeys() {
    return vars.keySet();
  }

}
