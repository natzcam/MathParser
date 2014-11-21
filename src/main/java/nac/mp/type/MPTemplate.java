/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.List;
import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.instance.MPBaseObj;

/**
 *
 * @author camomon
 */
public class MPTemplate extends MPObject implements Creator {

  private final MPTemplate extParent;
  private final String name;
  private final List<Expression> declarations;

  public MPTemplate(Scope parent, String name, MPTemplate extParent, List<Expression> declarations) {
    super(parent, null);
    this.name = name;
    this.extParent = extParent;
    this.declarations = declarations;
  }

  public String getName() {
    return name;
  }

  public List<Expression> getDeclarations() {
    return declarations;
  }

  @Override
  public Type getType() {
    return Type.TEMPLATE;
  }

  @Override
  public MPObject newInstance(ObjectStore store) {
    MPObject obj = new MPBaseObj(parent, this);
    if (extParent != null) {
      for (Expression d : extParent.declarations) {
        d.eval(obj, store);
      }
    }
    for (Expression d : declarations) {
      d.eval(obj, store);
    }
    return obj;
  }

  @Override
  public String toString() {
    return getType() + ":" + name;
  }
}
