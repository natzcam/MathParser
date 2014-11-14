/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import nac.mp.EvalException;

/**
 *
 * @author camomon
 */
public class MPList extends MPObject implements Comparable {

  private final List<MPObject> list;

  public MPList(int capacity, List<MPObject> initialValues) {
    super(null, null);
    list = new ArrayList<>(capacity);
    if (initialValues != null) {
      list.addAll(initialValues);
    }
  }

  @Override
  public MPObject getVar(String name) {
    switch (name) {
      case "add":
        return ADD;
    }
    return null;
  }

  @Override
  public MPObject isEqual(MPObject right) {
    switch (right.getHint()) {
      case LIST:
        MPList ml = (MPList) right;
        return new MPBoolean(list.equals(ml.getList()));
    }
    return new MPBoolean(false);
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case LIST:
        MPList ml = (MPList) right;
        return new MPBoolean(!list.equals(ml.getList()));
    }
    return new MPBoolean(false);
  }

  public MPObject get(int index) {
    return list.get(index);
  }

  public MPObject get(MPInteger index) {
    return list.get((int) index.getInt());
  }

  public void add(MPObject obj) {
    list.add(obj);
  }

  public List<MPObject> getList() {
    return list;
  }

  private static final MPFunc ADD = new MPFunc(null, null) {

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues) throws EvalException {
      MPList thisList = (MPList) thisRef;
      thisList.add(argsValues.get(0));
      return null;
    }

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues) throws EvalException {
      return call(thisRef, argsValues);
    }
  };

  @Override
  public Hint getHint() {
    return Hint.LIST;
  }

  @Override
  public String toString() {
    return list.toString();
  }

  @Override
  public int compareTo(Object o) {
    return 0;
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
  public void setVar(String name, MPObject value) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
