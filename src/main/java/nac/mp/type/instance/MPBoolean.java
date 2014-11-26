/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type.instance;

import nac.mp.EvalException;
import nac.mp.type.MPObject;
import nac.mp.type.Type;

/**
 *
 *
 * @author user
 */
public class MPBoolean extends MPObject implements Comparable<MPBoolean> {

  public static final MPBoolean TRUE = new MPBoolean(true);
  public static final MPBoolean FALSE = new MPBoolean(false);
  private final boolean value;

  private MPBoolean(boolean value) {
    super(null, null);
    this.value = value;
  }

  public static MPBoolean valueOf(boolean value) {
    return value ? MPBoolean.TRUE : MPBoolean.FALSE;
  }

  @Override
  public boolean getBoolean() {
    return value;
  }

  @Override
  public Type getType() {
    return Type.BOOL;
  }

  @Override
  public String toString() {
    return Boolean.toString(value);
  }

  public MPBoolean inverse() {
    return MPBoolean.valueOf(!value);
  }

  @Override
  public MPObject isEqual(MPObject right) {
    switch (right.getType()) {
      case BOOL:
        return MPBoolean.valueOf(value == right.getBoolean());
    }
    return MPBoolean.FALSE;
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getType()) {
      case BOOL:
        return MPBoolean.valueOf(value != right.getBoolean());
    }
    return MPBoolean.FALSE;
  }

  @Override
  public MPObject lo(MPObject right) {
    switch (right.getType()) {
      case BOOL:
        return MPBoolean.valueOf(value || right.getBoolean());
    }
    throw new EvalException("Not supported", getType(), " || ", right.getType());
  }

  @Override
  public MPObject la(MPObject right) {
    switch (right.getType()) {
      case BOOL:
        return MPBoolean.valueOf(value && right.getBoolean());
    }
    throw new EvalException("Not supported", getType(), " && ", right.getType());
  }

  @Override
  public int compareTo(MPBoolean o) {
    return (this.value == o.value) ? 0 : (this.value ? 1 : -1);
  }
}
