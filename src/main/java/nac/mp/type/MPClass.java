/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.List;
import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.Type;
import nac.mp.ast.Declaration;

/**
 *
 * @author camomon
 */
public class MPClass extends Type {

  private final Scope parent;
  private final List<Declaration> declarations;

  public MPClass(Scope parent, List<Declaration> declarations) {
    this.parent = parent;
    this.declarations = declarations;
  }

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

  public MPObject create() throws EvalException {
    MPObject obj = new MPObject(parent);
    for (Declaration d : declarations) {
      d.eval(obj);
    }
    return obj;
  }
}
