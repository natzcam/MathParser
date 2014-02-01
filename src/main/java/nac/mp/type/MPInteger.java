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
public class MPInteger extends Type {

  private final long value;

  public MPInteger(long value) {
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
  public Type plus(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getInt() + right.getFloat());
      case INTEGER:
        return new MPInteger(getInt() + right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public Type dash(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getInt() - right.getFloat());
      case INTEGER:
        return new MPInteger(getInt() - right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public Type star(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getInt() * right.getFloat());
      case INTEGER:
        return new MPInteger(getInt() * right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public Type slash(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPFloat(getInt() / right.getFloat());
      case INTEGER:
        return new MPInteger(getInt() / right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public Type lt(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getInt() < right.getFloat());
      case INTEGER:
        return new MPBoolean(getInt() < right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " * " + right.getHint() + " not supported");
  }

  @Override
  public Type lte(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getInt() <= right.getFloat());
      case INTEGER:
        return new MPBoolean(getInt() <= right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " <= " + right.getHint() + " not supported");
  }

  @Override
  public Type mte(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getInt() >= right.getFloat());
      case INTEGER:
        return new MPBoolean(getInt() >= right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " >= " + right.getHint() + " not supported");
  }

  @Override
  public Type mt(Type right) {
    switch (right.getHint()) {
      case FLOAT:
        return new MPBoolean(getInt() > right.getFloat());
      case INTEGER:
        return new MPBoolean(getInt() > right.getInt());
    }
    throw new UnsupportedOperationException(getHint() + " > " + right.getHint() + " not supported");
  }

  @Override
  public Type equal(Type right) {
    switch (right.getHint()) {
      case INTEGER:
        return new MPBoolean(value == right.getInt());
    }
    return new MPBoolean(false);
  }
  
  @Override
  public Type notEqual(Type right) {
    switch (right.getHint()) {
      case INTEGER:
        return new MPBoolean(value != right.getInt());
    }
    return new MPBoolean(true);
  }
}
