/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type.natv;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.type.MPObject;
import nac.mp.type.Type;

/**
 *
 * @author user
 */
public class MPInteger extends MPObject implements Comparable<MPInteger> {

  private final long value;

  public MPInteger(long value) {
    super(null, null);
    this.value = value;
  }

  @Override
  public long getInt() {
    return value;
  }

  @Override
  public Type getType() {
    return Type.INT;
  }

  @Override
  public String toString() {
    return Long.toString(value);
  }

  @Override
  public MPObject plus(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPFloat(getInt() + right.getFloat());
      case INT:
        return new MPInteger(getInt() + right.getInt());
    }
    throw new UnsupportedOperationException(getType() + " * " + right.getType() + " not supported");
  }

  @Override
  public MPObject dash(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPFloat(getInt() - right.getFloat());
      case INT:
        return new MPInteger(getInt() - right.getInt());
    }
    throw new UnsupportedOperationException(getType() + " * " + right.getType() + " not supported");
  }

  @Override
  public MPObject star(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPFloat(getInt() * right.getFloat());
      case INT:
        return new MPInteger(getInt() * right.getInt());
    }
    throw new UnsupportedOperationException(getType() + " * " + right.getType() + " not supported");
  }

  @Override
  public MPObject slash(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPFloat(getInt() / right.getFloat());
      case INT:
        return new MPInteger(getInt() / right.getInt());
    }
    throw new UnsupportedOperationException(getType() + " * " + right.getType() + " not supported");
  }

  @Override
  public MPObject lt(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPBoolean(getInt() < right.getFloat());
      case INT:
        return new MPBoolean(getInt() < right.getInt());
    }
    throw new UnsupportedOperationException(getType() + " * " + right.getType() + " not supported");
  }

  @Override
  public MPObject lte(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPBoolean(getInt() <= right.getFloat());
      case INT:
        return new MPBoolean(getInt() <= right.getInt());
    }
    throw new UnsupportedOperationException(getType() + " <= " + right.getType() + " not supported");
  }

  @Override
  public MPObject mte(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPBoolean(getInt() >= right.getFloat());
      case INT:
        return new MPBoolean(getInt() >= right.getInt());
    }
    throw new UnsupportedOperationException(getType() + " >= " + right.getType() + " not supported");
  }

  @Override
  public MPObject mt(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPBoolean(getInt() > right.getFloat());
      case INT:
        return new MPBoolean(getInt() > right.getInt());
    }
    throw new UnsupportedOperationException(getType() + " > " + right.getType() + " not supported");
  }

  @Override
  public MPObject isEqual(MPObject right) {
    switch (right.getType()) {
      case INT:
        return new MPBoolean(value == right.getInt());
    }
    return new MPBoolean(false);
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getType()) {
      case INT:
        return new MPBoolean(value != right.getInt());
    }
    return new MPBoolean(false);
  }

  @Override
  public int compareTo(MPInteger o) {
    return Long.compare(this.value, o.value);
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
