/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.Type;

/**
 *
 * @author camomon
 */
public class MPClass extends Type {

  @Override
  public Type.Hint getHint() {
    return Type.Hint.CLASS;
  }

  @Override
  public String toString() {
    return "class:" + this.hashCode();
  }

  @Override
  public Type equal(Type right) {
    switch (right.getHint()) {
      case CLASS:
        return new MPBoolean(this == right);
    }
    return new MPBoolean(false);
  }

  @Override
  public Type notEqual(Type right) {
    switch (right.getHint()) {
      case CLASS:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }

}
