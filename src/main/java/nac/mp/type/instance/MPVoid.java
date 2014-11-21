/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type.instance;

import nac.mp.type.MPObject;
import nac.mp.type.Type;

/**
 * 
 *
 * @author user
 */
public class MPVoid extends MPObject implements Comparable<MPObject> {

  public static final MPVoid VOID = MPVoid.VOID;

  private MPVoid() {
    super(null, null);
  }

  @Override
  public boolean isVoid() {
    return true;
  }

  @Override
  public Type getType() {
    return Type.VOID;
  }

  @Override
  public String toString() {
    return "<void>";
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getType()) {
      case VOID:
        return MPBoolean.FALSE;
    }
    return MPBoolean.valueOf(true);
  }

  @Override
  public MPObject isEqual(MPObject right) {
    switch (right.getType()) {
      case VOID:
        return MPBoolean.valueOf(true);
    }
    return MPBoolean.FALSE;
  }

  @Override
  public int compareTo(MPObject o) {
    return -1;
  }
}
