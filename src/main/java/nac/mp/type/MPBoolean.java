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
public class MPBoolean extends Type {

  private final boolean value;

  public MPBoolean(boolean value) {
    this.value = value;
  }

  @Override
  public boolean getBoolean() {
    return value;
  }

  @Override
  public Hint getHint() {
    return Hint.BOOLEAN;
  }

  @Override
  public String toString() {
    return Boolean.toString(value);
  }

  @Override
  public Type equal(Type right) {
    switch (right.getHint()) {
      case BOOLEAN:
        return new MPBoolean(value == right.getBoolean());
    }
    return new MPBoolean(false);
  }

  @Override
  public Type notEqual(Type right) {
    switch (right.getHint()) {
      case BOOLEAN:
        return new MPBoolean(value != right.getBoolean());
    }
    return new MPBoolean(true);
  }

}
