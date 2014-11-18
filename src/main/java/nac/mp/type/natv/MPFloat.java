/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type.natv;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;
import nac.mp.type.MPObject;

/**
 *
 * @author user
 */
public class MPFloat extends MPObject implements Comparable<MPFloat> {

  private final double value;

  public MPFloat(double value) {
    super(null, null);
    this.value = value;
  }

  @Override
  public double getFloat() {
    return value;
  }

  @Override
  public Hint getHint() {
    return Hint.FLOAT;
  }

  @Override
  public String toString() {
    return Double.toString(value);
  }

  @Override
  public MPObject plus(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getFloat() + right.getFloat());
      case INTEGER:
        return new MPFloat(getFloat() + right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public MPObject dash(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getFloat() - right.getFloat());
      case INTEGER:
        return new MPFloat(getFloat() - right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public MPObject star(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getFloat() * right.getFloat());
      case INTEGER:
        return new MPFloat(getFloat() * right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public MPObject slash(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getFloat() / right.getFloat());
      case INTEGER:
        return new MPFloat(getFloat() / right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public MPObject lt(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getFloat() < right.getFloat());
      case INTEGER:
        return new MPBoolean(getFloat() < right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public MPObject lte(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getFloat() <= right.getFloat());
      case INTEGER:
        return new MPBoolean(getFloat() <= right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " <= " + right.getHint() + " not supported");
  }

  @Override
  public MPObject mte(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getFloat() >= right.getFloat());
      case INTEGER:
        return new MPBoolean(getFloat() >= right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " >= " + right.getHint() + " not supported");
  }

  @Override
  public MPObject mt(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getFloat() > right.getFloat());
      case INTEGER:
        return new MPBoolean(getFloat() > right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " > " + right.getHint() + " not supported");
  }

  @Override
  public MPObject isEqual(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(value == right.getFloat());
    }
    return new MPBoolean(false);
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(value != right.getFloat());
    }
    return new MPBoolean(false);
  }

  @Override
  public int compareTo(MPFloat o) {
    return Double.compare(this.value, o.value);
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
  public void setVar(String name, MPObject value) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
