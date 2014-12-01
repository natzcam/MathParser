/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type.instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nac.mp.ObjectStore;
import nac.mp.type.ListBase;
import nac.mp.type.MPObject;
import nac.mp.type.Type;

/**
 *
 * @author camomon
 */
public class MPList extends MPObject implements ListBase {

  private static final MPFunc ADD = new MPFunc(null, null) {

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, ObjectStore store) {
      MPList thisList = (MPList) thisRef;
      thisList.list.add(argsValues.get(0));
      return null;
    }

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues, ObjectStore store) {
      return call(thisRef, argsValues, store);
    }
  };

  private static final MPFunc LENGTH = new MPFunc(null, null) {

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, ObjectStore store) {
      MPList thisList = (MPList) thisRef;
      return new MPInteger(thisList.list.size());
    }

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues, ObjectStore store) {
      return call(thisRef, argsValues, store);
    }
  };

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
  public MPObject getVar(String name, ObjectStore store) {
    switch (name) {
      case "add":
        return ADD;
      case "length":
        return LENGTH;
    }
    return super.getVar(name, store);
  }

  @Override
  public MPObject get(MPObject index, ObjectStore store) {
    return list.get((int) index.getInt());
  }

  @Override
  public void set(MPObject index, MPObject elem) {
    list.set((int) index.getInt(), elem);
  }

  @Override
  public void add(MPObject obj) {
    list.add(obj);
  }

  @Override
  public Type getType() {
    return Type.LIST;
  }

  @Override
  public String toString() {
    return getType() + ":" + list;
  }
}
