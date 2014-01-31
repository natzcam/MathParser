/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.Type;

/**
 *
 * @author user
 */
public class MPFloat extends Type {

  private final float value;

  public MPFloat(float value) {
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
  public Type plus(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getFloat() + right.getFloat());
      case INTEGER:
        return new MPFloat(getFloat() + right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public Type dash(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getFloat() - right.getFloat());
      case INTEGER:
        return new MPFloat(getFloat() - right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public Type star(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getFloat() * right.getFloat());
      case INTEGER:
        return new MPFloat(getFloat() * right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public Type slash(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getFloat() / right.getFloat());
      case INTEGER:
        return new MPFloat(getFloat() / right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public Type lt(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getFloat() < right.getFloat());
      case INTEGER:
        return new MPBoolean(getFloat() < right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public Type lte(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getFloat() <= right.getFloat());
      case INTEGER:
        return new MPBoolean(getFloat() <= right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " <= " + right.getHint() + " not supported");
  }

  @Override
  public Type mte(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getFloat() >= right.getFloat());
      case INTEGER:
        return new MPBoolean(getFloat() >= right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " >= " + right.getHint() + " not supported");
  }

  @Override
  public Type mt(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getFloat() > right.getFloat());
      case INTEGER:
        return new MPBoolean(getFloat() > right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " > " + right.getHint() + " not supported");
  }

  @Override
  public Type equal(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(value == right.getFloat());
    }
    return new MPBoolean(false);
  }

  @Override
  public Type notEqual(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(value != right.getFloat());
    }
    return new MPBoolean(true);
  }
}
