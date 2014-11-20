/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type.instance;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.type.Type;

/**
 * TODO use 1 instance of True and False
 *
 * @author user
 */
public class MPBoolean extends MPObject implements Comparable<MPBoolean> {

  private final boolean value;

  public MPBoolean(boolean value) {
    super(null, null);
    this.value = value;
  }

  @Override
  public boolean getBoolean() {
    return value;
  }

  @Override
  public Type getType() {
    return Type.BOOL;
  }

  @Override
  public String toString() {
    return Boolean.toString(value);
  }

  public MPBoolean inverse() {
    return new MPBoolean(!value);
  }

  @Override
  public MPObject isEqual(MPObject right) {
    switch (right.getType()) {
      case BOOL:
        return new MPBoolean(value == right.getBoolean());
    }
    return new MPBoolean(false);
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getType()) {
      case BOOL:
        return new MPBoolean(value != right.getBoolean());
    }
    return new MPBoolean(false);
  }

  @Override
  public MPObject lo(MPObject right) {
    switch (right.getType()) {
      case BOOL:
        return new MPBoolean(value || right.getBoolean());
    }
    throw new UnsupportedOperationException(getType() + " > " + right.getType() + " not supported");
  }

  @Override
  public MPObject la(MPObject right) {
    switch (right.getType()) {
      case BOOL:
        return new MPBoolean(value && right.getBoolean());
    }
    throw new UnsupportedOperationException(getType() + " > " + right.getType() + " not supported");
  }

  @Override
  public int compareTo(MPBoolean o) {
    return (this.value == o.value) ? 0 : (this.value ? 1 : -1);
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
  public boolean containsVar(String name, ObjectStore store) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public MPObject getVar(String name, ObjectStore store) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void setVar(String name, MPObject value, ObjectStore store) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
