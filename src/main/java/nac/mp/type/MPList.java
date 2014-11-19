/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.type.natv.MPInteger;
import nac.mp.type.natv.MPBoolean;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import nac.mp.EvalException;

/**
 *
 * @author camomon
 */
public class MPList extends MPObject implements Comparable<MPList> {

  private final List<MPObject> list;

  public MPList(int capacity, List<MPObject> initialValues) {
    super(null, null);
    list = new ArrayList<>(capacity);
    if (initialValues != null) {
      list.addAll(initialValues);
    }
  }

  public MPList(List<MPObject> initialValues) {
    super(null, null);
    list = new ArrayList<>();
    if (initialValues != null) {
      list.addAll(initialValues);
    }
  }

  public MPList() {
    super(null, null);
    list = new ArrayList<>();
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
    switch (right.getType()) {
      case LIST:
        return this.listEquals((MPList) right);
    }
    return new MPBoolean(false);
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getType()) {
      case LIST:
        return this.listEquals((MPList) right).inverse();
    }
    return new MPBoolean(false);
  }

  private MPBoolean listEquals(MPList mpList) {
    if (mpList == this) {
      return new MPBoolean(true);
    }

    ListIterator<MPObject> e1 = list.listIterator();
    ListIterator<MPObject> e2 = mpList.list.listIterator();
    while (e1.hasNext() && e2.hasNext()) {
      MPObject o1 = e1.next();
      MPObject o2 = e2.next();

      if (!(o1 == null ? o2 == null : o1.isEqual(o2).getBoolean())) {
        return new MPBoolean(false);
      }
    }
    return new MPBoolean(!(e1.hasNext() || e2.hasNext()));
  }

  public MPObject get(MPInteger index) {
    return list.get((int) index.getInt());
  }

  public MPObject set(MPInteger index, MPObject elem) {
    return list.set((int) index.getInt(), elem);
  }

  public void add(MPObject obj) {
    list.add(obj);
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
  public Type getType() {
    return Type.LIST;
  }

  @Override
  public int compareTo(MPList o) {
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
