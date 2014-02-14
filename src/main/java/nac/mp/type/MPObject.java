/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.Scope;

/**
 *
 * @author user
 */
public class MPObject implements Scope, Serializable {

  protected final MPClass clazz;
  protected Scope parent;
  protected final Map<String, MPObject> vars = new HashMap<>();

  public MPObject(Scope parent, MPClass clazz) {
    this.clazz = clazz;
    this.parent = parent;
  }

  public void setParent(Scope parent) {
    this.parent = parent;
  }

  public MPClass getClazz() {
    return clazz;
  }

  public MPObject.Hint getHint() {
    return MPObject.Hint.OBJECT;
  }

  public MPObject methodCall(String name, List<MPObject> argsValues) throws EvalException {
    MPFunc func = (MPFunc) vars.get(name);
    return func.call(this, argsValues);
  }

  public MPObject methodCall(String name, List<MPObject> argsValues, Map<String, MPObject> optsValues) throws EvalException {
    MPFunc func = (MPFunc) vars.get(name);
    return func.call(this, argsValues, optsValues);
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
  public void setVarLocal(String name, MPObject value) {
    vars.put(name, value);
  }

  @Override
  public void declareVarLocal(String name, MPObject defaultValue) throws EvalException {
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
    } else {
      return parent != null && parent.containsVar(name);
    }
  }

  @Override
  public void setVar(String name, MPObject value) {
    setVarLocal(name, value);
  }

  @Override
  public MPObject getVar(String name) {
    // System.out.println(this + ".getVar " + name);
    MPObject result = vars.get(name);
    if (result == null && parent != null) {
      result = parent.getVar(name);
    }
    //System.out.println("result " + result);
    return result;
  }

  public MPObject equal(MPObject right) {
    switch (right.getHint()) {
      case OBJECT:
        return new MPBoolean(this == right);
    }
    return new MPBoolean(false);
  }

  public MPObject notEqual(MPObject right) {
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

  public static enum Hint {

    BOOLEAN,
    INTEGER,
    FLOAT,
    STRING,
    VOID,
    FUNCTION,
    OBJECT,
    CLASS,
    LIST,
  }

  public boolean getBoolean() {
    throw new UnsupportedOperationException("No boolean representation: " + getHint());
  }

  public long getInt() {
    throw new UnsupportedOperationException("No int representation: " + getHint());
  }

  public float getFloat() {
    throw new UnsupportedOperationException("No float representation: " + getHint());
  }

  public String getString() {
    throw new UnsupportedOperationException("No string representation: " + getHint());
  }

  public boolean isVoid() {
    return false;
  }

  public MPObject plus(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " + " + right.getHint() + " not supported");
  }

  public MPObject dash(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " - " + right.getHint() + " not supported");
  }

  public MPObject star(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  public MPObject slash(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " / " + right.getHint() + " not supported");
  }

  public MPObject lte(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " <= " + right.getHint() + " not supported");
  }

  public MPObject lt(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " < " + right.getHint() + " not supported");
  }

  public MPObject mte(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " >= " + right.getHint() + " not supported");
  }

  public MPObject mt(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " > " + right.getHint() + " not supported");
  }
}
