/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.List;
import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.ast.Expression;

/**
 *
 * @author camomon
 */
public class MPClass extends MPObject {

  private final MPClass extParent;
  private final String name;
  private final List<Expression> declarations;

    public MPClass(Scope parent, String name, MPClass extParent, List<Expression> declarations) {
    super(parent, null);
    this.name = name;
    this.extParent = extParent;
    this.declarations = declarations;
  }

  public List<Expression> getDeclarations() {
    return declarations;
  }

  @Override
  public MPObject.Hint getHint() {
    return MPObject.Hint.CLASS;
  }

  @Override
  public String toString() {
    return "class:" + name;
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case CLASS:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }

  public MPObject create() throws EvalException {
    MPObject obj = new MPObject(parent, this);
    if (extParent != null) {
      for (Expression d : extParent.declarations) {
        d.eval(obj);
      }
    }
    for (Expression d : declarations) {
      d.eval(obj);
    }
    return obj;
  }
}
