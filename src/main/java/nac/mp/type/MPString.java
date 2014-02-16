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
public class MPString extends MPObject implements Serializable {

  private final String value;

  public MPString(String value) {
    super(null, null);
    this.value = value;
    vars.put("to_int", new MPJavaFunc());
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
  public MPObject equal(MPObject right) {
    switch (right.getHint()) {
      case STRING:
        return new MPBoolean(value.equals(right.getString()));
    }
    return new MPBoolean(false);
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case STRING:
        return new MPBoolean(!value.equals(right.getString()));
    }
    return new MPBoolean(false);
  }
}
