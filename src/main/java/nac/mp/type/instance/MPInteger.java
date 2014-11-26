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
 * @author user
 */
public class MPInteger extends MPObject implements Comparable<MPInteger> {

  private final long value;

  public MPInteger(long value) {
    super(null, null);
    this.value = value;
  }

  @Override
  public long getInt() {
    return value;
  }

  @Override
  public Type getType() {
    return Type.INT;
  }

  @Override
  public String toString() {
    return Long.toString(value);
  }

  @Override
  public MPObject plus(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPFloat(getInt() + right.getFloat());
      case INT:
        return new MPInteger(getInt() + right.getInt());
    }
    throw new EvalException("Not supported", getType(), " + ", right.getType());
  }

  @Override
  public MPObject dash(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPFloat(getInt() - right.getFloat());
      case INT:
        return new MPInteger(getInt() - right.getInt());
    }
    throw new EvalException("Not supported", getType(), " - ", right.getType());
  }

  @Override
  public MPObject star(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPFloat(getInt() * right.getFloat());
      case INT:
        return new MPInteger(getInt() * right.getInt());
    }
    throw new EvalException("Not supported", getType(), " * ", right.getType());
  }

  @Override
  public MPObject slash(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPFloat(getInt() / right.getFloat());
      case INT:
        return new MPInteger(getInt() / right.getInt());
    }
    throw new EvalException("Not supported", getType(), " / ", right.getType());
  }

  @Override
  public MPObject lt(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return MPBoolean.valueOf(getInt() < right.getFloat());
      case INT:
        return MPBoolean.valueOf(getInt() < right.getInt());
    }
    throw new EvalException("Not supported", getType(), " < ", right.getType());
  }

  @Override
  public MPObject lte(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return MPBoolean.valueOf(getInt() <= right.getFloat());
      case INT:
        return MPBoolean.valueOf(getInt() <= right.getInt());
    }
    throw new EvalException("Not supported", getType(), " <= ", right.getType());
  }

  @Override
  public MPObject mte(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return MPBoolean.valueOf(getInt() >= right.getFloat());
      case INT:
        return MPBoolean.valueOf(getInt() >= right.getInt());
    }
    throw new EvalException("Not supported", getType(), " >= ", right.getType());
  }

  @Override
  public MPObject mt(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return MPBoolean.valueOf(getInt() > right.getFloat());
      case INT:
        return MPBoolean.valueOf(getInt() > right.getInt());
    }
   throw new EvalException("Not supported", getType(), " > ", right.getType());
  }

  @Override
  public MPObject isEqual(MPObject right) {
    switch (right.getType()) {
      case INT:
        return MPBoolean.valueOf(value == right.getInt());
    }
    return MPBoolean.FALSE;
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getType()) {
      case INT:
        return MPBoolean.valueOf(value != right.getInt());
    }
    return MPBoolean.FALSE;
  }

  @Override
  public int compareTo(MPInteger o) {
    return Long.compare(this.value, o.value);
  }

}
