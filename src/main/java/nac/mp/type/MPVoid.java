/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.io.Serializable;

/**
 * TODO:use 1 instance of void
 *
 * @author user
 */
public class MPVoid extends MPObject implements Serializable {

  public MPVoid() {
    super(null, null);
  }

  @Override
  public boolean isVoid() {
    return true;
  }

  @Override
  public Hint getHint() {
    return Hint.VOID;
  }

  @Override
  public String toString() {
    return "void";
  }

  @Override
  public MPObject equal(MPObject right) {
    switch (right.getHint()) {
      case VOID:
        return new MPBoolean(true);
    }
    return new MPBoolean(false);
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case VOID:
        return new MPBoolean(false);
    }
    return new MPBoolean(true);
  }
}
