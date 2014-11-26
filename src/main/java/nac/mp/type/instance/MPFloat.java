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
public class MPFloat extends MPObject implements Comparable<MPFloat> {

  private final double value;

  public MPFloat(double value) {
    super(null, null);
    this.value = value;
  }

  @Override
  public double getFloat() {
    return value;
  }

  @Override
  public Type getType() {
    return Type.FLOAT;
  }

  @Override
  public String toString() {
    return Double.toString(value);
  }

  @Override
  public MPObject plus(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPFloat(getFloat() + right.getFloat());
      case INT:
        return new MPFloat(getFloat() + right.getInt());
    }
    throw new EvalException("Not supported", getType(), " + ", right.getType());
  }

  @Override
  public MPObject dash(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPFloat(getFloat() - right.getFloat());
      case INT:
        return new MPFloat(getFloat() - right.getInt());
    }
    throw new EvalException("Not supported", getType(), " - ", right.getType());
  }

  @Override
  public MPObject star(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPFloat(getFloat() * right.getFloat());
      case INT:
        return new MPFloat(getFloat() * right.getInt());
    }
    throw new EvalException("Not supported", getType(), " * ", right.getType());
  }

  @Override
  public MPObject slash(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return new MPFloat(getFloat() / right.getFloat());
      case INT:
        return new MPFloat(getFloat() / right.getInt());
    }
    throw new EvalException("Not supported", getType(), " / ", right.getType());
  }

  @Override
  public MPObject lt(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return MPBoolean.valueOf(getFloat() < right.getFloat());
      case INT:
        return MPBoolean.valueOf(getFloat() < right.getInt());
    }
    throw new EvalException("Not supported", getType(), " < ", right.getType());
  }

  @Override
  public MPObject lte(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return  MPBoolean.valueOf(getFloat() <= right.getFloat());
      case INT:
        return MPBoolean.valueOf(getFloat() <= right.getInt());
    }
    throw new EvalException("Not supported", getType(), " <= ", right.getType());
  }

  @Override
  public MPObject mte(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return MPBoolean.valueOf(getFloat() >= right.getFloat());
      case INT:
        return MPBoolean.valueOf(getFloat() >= right.getInt());
    }
   throw new EvalException("Not supported", getType(), " >= ", right.getType());
  }

  @Override
  public MPObject mt(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return MPBoolean.valueOf(getFloat() > right.getFloat());
      case INT:
        return MPBoolean.valueOf(getFloat() > right.getInt());
    }
    throw new EvalException("Not supported", getType(), " > ", right.getType());
  }

  @Override
  public MPObject isEqual(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return MPBoolean.valueOf(value == right.getFloat());
    }
    return MPBoolean.FALSE;
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getType()) {
      case FLOAT:
        return MPBoolean.valueOf(value != right.getFloat());
    }
    return MPBoolean.FALSE;
  }

  @Override
  public int compareTo(MPFloat o) {
    return Double.compare(this.value, o.value);
  }
}
