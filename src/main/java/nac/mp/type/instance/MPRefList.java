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
public class MPRefList extends MPObject implements ListBase {

  private static final MPFunc ADD = new MPFunc(null, null) {

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, ObjectStore store) {
      MPRefList thisList = (MPRefList) thisRef;
      thisList.add(argsValues.get(0));
      return null;
    }

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues, ObjectStore store) {
      return call(thisRef, argsValues, store);
    }
  };

  private final String model;
  transient private List<MPModelObj> list = new ArrayList<>();
  private final List<MPObject> refList = new ArrayList<>();

  public MPRefList(String model) {
    super(null, null);
    this.model = model;
  }

  @Override
  public MPObject getVar(String name, ObjectStore store) {
    switch (name) {
      case "add":
        return ADD;
    }
    return super.getVar(name, store);
  }

  public String getModel() {
    return model;
  }

  @Override
  public String toString() {
    return getType() + ":" + model + "," + refList;
  }

  private void fetch(ObjectStore store) {
    list = store.select(this);
  }

  @Override
  public MPModelObj get(MPObject index, ObjectStore store) {
    if (list == null || list.size() < refList.size()) {
      fetch(store);
    }
    return list.get((int) index.getInt());
  }

  @Override
  public void set(MPObject index, MPObject elem) {
    MPModelObj moElem = (MPModelObj) elem;
    list.set((int) index.getInt(), moElem);
    refList.set((int) index.getInt(), moElem.getId());
  }

  @Override
  public void add(MPObject elem) {
    MPModelObj moElem = (MPModelObj) elem;
    list.add(moElem);
    refList.add(moElem.getId());
  }

  public List<MPObject> getRefList() {
    return refList;
  }

  @Override
  public Type getType() {
    return Type.LIST;
  }

}
