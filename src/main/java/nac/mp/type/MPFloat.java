/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.io.Serializable;
import nac.mp.type.MPObject;

;

/**
 *
 * @author user
 */
public class MPFloat extends MPObject implements Serializable {

  private final float value;

  public MPFloat(float value) {
    super(null, null);
    this.value = value;
  }

  @Override
  public float getFloat() {
    return value;
  }

  @Override
  public Hint getHint() {
    return Hint.FLOAT;
  }

  @Override
  public String toString() {
    return Float.toString(value);
  }

  @Override
  public MPObject plus(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getFloat() + right.getFloat());
      case INTEGER:
        return new MPFloat(getFloat() + right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public MPObject dash(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getFloat() - right.getFloat());
      case INTEGER:
        return new MPFloat(getFloat() - right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public MPObject star(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getFloat() * right.getFloat());
      case INTEGER:
        return new MPFloat(getFloat() * right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public MPObject slash(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getFloat() / right.getFloat());
      case INTEGER:
        return new MPFloat(getFloat() / right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public MPObject lt(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getFloat() < right.getFloat());
      case INTEGER:
        return new MPBoolean(getFloat() < right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public MPObject lte(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getFloat() <= right.getFloat());
      case INTEGER:
        return new MPBoolean(getFloat() <= right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " <= " + right.getHint() + " not supported");
  }

  @Override
  public MPObject mte(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getFloat() >= right.getFloat());
      case INTEGER:
        return new MPBoolean(getFloat() >= right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " >= " + right.getHint() + " not supported");
  }

  @Override
  public MPObject mt(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getFloat() > right.getFloat());
      case INTEGER:
        return new MPBoolean(getFloat() > right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " > " + right.getHint() + " not supported");
  }

  @Override
  public MPObject equal(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(value == right.getFloat());
    }
    return new MPBoolean(false);
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(value != right.getFloat());
    }
    return new MPBoolean(true);
  }
}
