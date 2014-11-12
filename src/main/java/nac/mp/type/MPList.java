/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    vars.put("add", ADD);
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
    return MPObject.Hint.LIST;
  }

  @Override
  public boolean equals(Object right) {
    if (right instanceof MPList) {
      return list.equals(((MPList) right).list);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 31 * hash + Objects.hashCode(this.list);
    return hash;
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case LIST:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }

  @Override
  public String toString() {
    return list.toString();
  }

  @Override
  public int compareTo(Object o) {
    return 0;
  }

}
