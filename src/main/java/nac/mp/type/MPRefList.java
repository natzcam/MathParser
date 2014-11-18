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
import nac.mp.ObjectStore;

/**
 *
 * @author camomon
 */
public class MPRefList extends MPObject implements Comparable<MPRefList> {

  private final List<MPReference> refList = new ArrayList<MPReference>();
  private transient final List<MPModelObj> list;
  private final ObjectStore objectStore;

  public MPRefList(int capacity, List<MPModelObj> initialValues, ObjectStore objectStore) {
    super(null, null);
    list = new ArrayList<>(capacity);
    if (initialValues != null) {
      list.addAll(initialValues);
    }
    this.objectStore = objectStore;
  }

  public MPRefList(List<MPModelObj> initialValues, ObjectStore objectStore) {
    super(null, null);
    list = new ArrayList<>();
    if (initialValues != null) {
      list.addAll(initialValues);
    }
    this.objectStore = objectStore;
  }

  public MPRefList(ObjectStore objectStore) {
    super(null, null);
    list = new ArrayList<>();
    this.objectStore = objectStore;
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
        return this.listEquals((MPRefList) right);
    }
    return new MPBoolean(false);
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getType()) {
      case LIST:
        return this.listEquals((MPRefList) right).inverse();
    }
    return new MPBoolean(false);
  }

  private MPBoolean listEquals(MPRefList mpList) {
    if (mpList == this) {
      return new MPBoolean(true);
    }

    ListIterator<MPModelObj> e1 = list.listIterator();
    ListIterator<MPModelObj> e2 = mpList.list.listIterator();
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

  public void add(MPModelObj obj) {
    list.add(obj);
  }

  private static final MPFunc ADD = new MPFunc(null, null) {

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues) throws EvalException {
      MPRefList thisList = (MPRefList) thisRef;
      thisList.list.add((MPModelObj) argsValues.get(0));
      return null;
    }

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues) throws EvalException {
      return call(thisRef, argsValues);
    }
  };

  @Override
  public Type getType() {
    return Type.REF_LIST;
  }

  @Override
  public int compareTo(MPRefList o) {
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
