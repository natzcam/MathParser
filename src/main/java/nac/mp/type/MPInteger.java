/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.io.Serializable;


/**
 *
 * @author user
 */
public class MPInteger extends MPObject implements Serializable{

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
  public Hint getHint() {
    return Hint.INTEGER;
  }

  @Override
  public String toString() {
    return Long.toString(value);
  }

  @Override
  public MPObject plus(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getInt() + right.getFloat());
      case INTEGER:
        return new MPInteger(getInt() + right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public MPObject dash(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getInt() - right.getFloat());
      case INTEGER:
        return new MPInteger(getInt() - right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public MPObject star(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getInt() * right.getFloat());
      case INTEGER:
        return new MPInteger(getInt() * right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public MPObject slash(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getInt() / right.getFloat());
      case INTEGER:
        return new MPInteger(getInt() / right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public MPObject lt(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getInt() < right.getFloat());
      case INTEGER:
        return new MPBoolean(getInt() < right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public MPObject lte(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getInt() <= right.getFloat());
      case INTEGER:
        return new MPBoolean(getInt() <= right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " <= " + right.getHint() + " not supported");
  }

  @Override
  public MPObject mte(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getInt() >= right.getFloat());
      case INTEGER:
        return new MPBoolean(getInt() >= right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " >= " + right.getHint() + " not supported");
  }

  @Override
  public MPObject mt(MPObject right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getInt() > right.getFloat());
      case INTEGER:
        return new MPBoolean(getInt() > right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " > " + right.getHint() + " not supported");
  }

  @Override
  public MPObject equal(MPObject right) {
    switch (right.getHint()) {
      case INTEGER:
        return new MPBoolean(value == right.getInt());
    }
    return new MPBoolean(false);
  }
  
  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case INTEGER:
        return new MPBoolean(value != right.getInt());
    }
    return new MPBoolean(true);
  }
}
