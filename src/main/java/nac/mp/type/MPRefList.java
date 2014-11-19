/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.ArrayList;
import java.util.List;
import nac.mp.ObjectStore;

/**
 *
 * @author camomon
 */
public class MPRefList extends MPList {

  private final List<MPReference> refList = new ArrayList<MPReference>();
  transient private final ObjectStore objectStore;

  public MPRefList(int capacity, List<MPObject> initialValues, ObjectStore objectStore) {
    super(capacity, initialValues);
    this.objectStore = objectStore;
  }

  public MPRefList(List<MPObject> initialValues, ObjectStore objectStore) {
    super(initialValues);
    this.objectStore = objectStore;
  }

  public MPRefList(ObjectStore objectStore) {
    this.objectStore = objectStore;
  }

  @Override
  public Type getType() {
    return Type.REF_LIST;
  }
}
