/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.ast.Scope;

/**
 *
 * @author user
 */
public class MPObject implements Scope {

  protected final Creator creator;
  protected Scope parent;
  protected final Map<String, MPObject> vars = new HashMap<>();

  public MPObject(Scope parent, Creator creator) {
    this.creator = creator;
    this.parent = parent;
  }

  public void setParent(Scope parent) {
    this.parent = parent;
  }

  public Creator getCreator() {
    return creator;
  }

  public MPObject.Hint getHint() {
    return MPObject.Hint.OBJECT;
  }

  @Override
  public String toString() {
    return vars.toString();
  }

  @Override
  public Scope getParent() {
    return parent;
  }

  public void detachParent() {
    parent = null;
  }

  @Override
  public void setLocalVar(String name, MPObject value) {
    vars.put(name, value);
  }

  @Override
  public void declareLocalVar(String name, MPObject defaultValue) throws EvalException {
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
    vars.put(name, value);
  }

  @Override
  public MPObject getVar(String name) {
    MPObject result = vars.get(name);
    if (result == null && parent != null) {
      result = parent.getVar(name);
    }
    return result;
  }

  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case OBJECT:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }

  @Override
  public Set<String> getLocalVarKeys() {
    return vars.keySet();
  }

  @Override
  public Collection<MPObject> getLocalVarValues() {
    return vars.values();
  }

  @Override
  public void setLocalVars(Map<String, MPObject> vars) {
    this.vars.putAll(vars);
  }

  public static enum Hint {

    BOOLEAN,
    INTEGER,
    FLOAT,
    STRING,
    VOID,
    FUNCTION,
    OBJECT,
    TEMPLATE,
    LIST,
    MODEL,
    ATTRIBUTE;
  }

  public boolean getBoolean() {
    throw new UnsupportedOperationException("No boolean representation: " + getHint());
  }

  public long getInt() {
    throw new UnsupportedOperationException("No int representation: " + getHint());
  }

  public double getFloat() {
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

  public MPObject la(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " && " + right.getHint() + " not supported");
  }

  public MPObject lo(MPObject right) {
    throw new UnsupportedOperationException(getHint() + " || " + right.getHint() + " not supported");
  }
}
