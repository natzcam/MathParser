/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;

/**
 * TODO:use 1 instance of void
 *
 * @author user
 */
public class MPVoid extends MPObject implements Comparable<MPObject> {

  public MPVoid() {
    super(null, null);
  }

  @Override
  public boolean isVoid() {
    return true;
  }

  @Override
  public Hint getHint() {
    return Hint.VOID;
  }

  @Override
  public String toString() {
    return "<void>";
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case VOID:
        return new MPBoolean(false);
    }
    return new MPBoolean(true);
  }

  @Override
  public MPObject isEqual(MPObject right) {
    switch (right.getHint()) {
      case VOID:
        return new MPBoolean(true);
    }
    return new MPBoolean(false);
  }

  @Override
  public int compareTo(MPObject o) {
    return -1;
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
