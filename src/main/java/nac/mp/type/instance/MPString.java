/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type.instance;

import java.util.List;
import java.util.Map;
import nac.mp.ObjectStore;
import nac.mp.type.MPObject;
import nac.mp.type.Type;

/**
 *
 * @author user
 */
public class MPString extends MPObject implements Comparable<MPString> {

  private static final MPFunc TO_INT = new MPFunc(null, null) {

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, ObjectStore store) {
      return new MPInteger(Long.parseLong(thisRef.getString()));
    }

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues, ObjectStore store) {
      return call(thisRef, argsValues, store);
    }
  };

  private static final MPFunc TO_FLOAT = new MPFunc(null, null) {

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, ObjectStore store) {
      return new MPFloat(Float.parseFloat(thisRef.getString()));
    }

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues, ObjectStore store) {
      return call(thisRef, argsValues, store);
    }
  };
  private final String value;

  public MPString(String value) {
    super(null, null);
    this.value = value;
  }

  @Override
  public String getString() {
    return value;
  }

  @Override
  public Type getType() {
    return Type.STRING;
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public MPObject isEqual(MPObject right) {
    switch (right.getType()) {
      case STRING:
        return MPBoolean.valueOf(value.equals(right.getString()));
    }
    return MPBoolean.FALSE;
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getType()) {
      case STRING:
        return MPBoolean.valueOf(!value.equals(right.getString()));
    }
    return MPBoolean.FALSE;
  }

  @Override
  public MPObject plus(MPObject right) {
    return new MPString(value + right.toString());
  }

  @Override
  public int compareTo(MPString o) {
    return value.compareTo(o.value);
  }

  @Override
  public MPObject getVar(String name, ObjectStore store) {
    switch (name) {
      case "toInt":
        return TO_INT;
      case "toFloat":
        return TO_FLOAT;
    }
    return super.getVar(name, store);
  }

}
