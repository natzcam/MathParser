/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.io.Serializable;
;

/**
 *
 * @author user
 */
public class MPBoolean extends MPObject implements Serializable{

  private final boolean value;

  public MPBoolean(boolean value) {
    super(null, null);
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
  public MPObject equal(MPObject right) {
    switch (right.getHint()) {
      case BOOLEAN:
        return new MPBoolean(value == right.getBoolean());
    }
    return new MPBoolean(false);
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case BOOLEAN:
        return new MPBoolean(value != right.getBoolean());
    }
    return new MPBoolean(true);
  }

}
