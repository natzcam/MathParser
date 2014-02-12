/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.ArrayList;
import java.util.List;
import nac.mp.Type;

/**
 *
 * @author camomon
 */
public class MPList extends Type {

  private final List<Type> list;

  public MPList(int capacity, List<Type> initialValues) {
    list = new ArrayList<>(capacity);
    list.addAll(initialValues);
  }

  public int getLength() {
    return list.size();
  }

  @Override
  public Hint getHint() {
    return Type.Hint.LIST;
  }

  @Override
  public Type equal(Type right) {
    switch (right.getHint()) {
      case LIST:
        return new MPBoolean(this == right);
    }
    return new MPBoolean(false);
  }

  @Override
  public Type notEqual(Type right) {
    switch (right.getHint()) {
      case LIST:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }

}
