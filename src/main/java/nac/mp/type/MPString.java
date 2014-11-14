/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import nac.mp.EvalException;

/**
 *
 * @author user
 */
public class MPString extends MPObject implements Comparable<MPString> {

  private final String value;

  private static final MPFunc TO_INT = new MPFunc(null, null) {

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues) throws EvalException {
      return new MPInteger(Long.parseLong(thisRef.getString()));
    }

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues) throws EvalException {
      return call(thisRef, argsValues);
    }
  };

  private static final MPFunc TO_FLOAT = new MPFunc(null, null) {

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues) throws EvalException {
      return new MPFloat(Float.parseFloat(thisRef.getString()));
    }

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues) throws EvalException {
      return call(thisRef, argsValues);
    }
  };

  public MPString(String value) {
    super(null, null);
    this.value = value;
  }

  @Override
  public String getString() {
    return value;
  }

  @Override
  public Hint getHint() {
    return Hint.STRING;
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public boolean equals(Object right) {
    if (right instanceof MPString) {
      return value.equals(((MPString) right).getString());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + Objects.hashCode(this.value);
    return hash;
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case STRING:
        return new MPBoolean(!value.equals(right.getString()));
    }
    return new MPBoolean(false);
  }

  @Override
  public MPObject plus(MPObject right) {
    return new MPString(value + right.toString());
  }

  @Override
  public int compareTo(MPString o) {
    return value.compareTo(o.value);
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
    switch (name) {
      case "toInt":
        return TO_INT;
      case "toFloat":
        return TO_FLOAT;
    }
    return null;
  }

  @Override
  public void setVar(String name, MPObject value) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
