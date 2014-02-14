/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author camomon
 */
public class MPList extends MPObject {

  private final List<MPObject> list;

  public MPList(int capacity, List<MPObject> initialValues) {
    super(null, null);
    list = new ArrayList<>(capacity);
    list.addAll(initialValues);
  }

  public int getLength() {
    return list.size();
  }

  public MPObject get(int index) {
    return list.get(index);
  }

  public MPObject get(MPInteger index) {
    return list.get((int) index.getInt());
  }

  @Override
  public Hint getHint() {
    return MPObject.Hint.LIST;
  }

  @Override
  public MPObject equal(MPObject right) {
    switch (right.getHint()) {
      case LIST:
        return new MPBoolean(this == right);
    }
    return new MPBoolean(false);
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case LIST:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }

}
