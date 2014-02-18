/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.io.Serializable;

/**
 * TODO use 1 instance of True and False
 * @author user
 */
public class MPBoolean extends MPObject implements Serializable {

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
  public boolean equals(Object right) {
    if (right instanceof MPBoolean) {
      return value == ((MPBoolean) right).getBoolean();
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 11 * hash + (this.value ? 1 : 0);
    return hash;
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
