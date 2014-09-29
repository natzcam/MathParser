/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import nac.mp.EvalException;

/**
 *
 * @author user
 */
public class MPString extends MPObject{

  private final String value;

  private static final MPFunc TO_INT = new MPFunc(null, null) {

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues) throws EvalException {
      return new MPInteger(Long.parseLong(thisRef.getString()));
    }

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues) throws EvalException {
      return call(thisRef, argsValues);
    }
  };

  private static final MPFunc TO_FLOAT = new MPFunc(null, null) {

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues) throws EvalException {
      return new MPFloat(Float.parseFloat(thisRef.getString()));
    }

    @Override
    public MPObject call(MPObject thisRef, List<MPObject> argsValues, Map<String, MPObject> optsValues) throws EvalException {
      return call(thisRef, argsValues);
    }
  };

  public MPString(String value) {
    super(null, null);
    this.value = value;
    vars.put("toInt", TO_INT);
    vars.put("toFloat", TO_FLOAT);
  }

  @Override
  public String getString() {
    return value;
  }

  @Override
  public Hint getHint() {
    return Hint.STRING;
  }

  @Override
  public String toString() {
    return value;
  }

  @Override
  public boolean equals(Object right) {
    if (right instanceof MPString) {
      return value.equals(((MPString) right).getString());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 83 * hash + Objects.hashCode(this.value);
    return hash;
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case STRING:
        return new MPBoolean(!value.equals(right.getString()));
    }
    return new MPBoolean(false);
  }
}
