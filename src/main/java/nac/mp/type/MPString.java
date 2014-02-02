/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.io.Serializable;
import nac.mp.Type;

/**
 *
 * @author user
 */
public class MPString extends Type implements Serializable{

  private final String value;

  public MPString(String value) {
    this.value = value;
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
  public Type equal(Type right) {
    switch (right.getHint()) {
      case STRING:
        return new MPBoolean(value.equals(right.getString()));
    }
    return new MPBoolean(false);
  }

  @Override
  public Type notEqual(Type right) {
    switch (right.getHint()) {
      case STRING:
        return new MPBoolean(!value.equals(right.getString()));
    }
    return new MPBoolean(false);
  }
}
