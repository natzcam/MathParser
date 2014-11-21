/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.ArrayList;
import java.util.List;
import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.ast.TokenAwareExpression;
import nac.mp.type.MPObject;
import nac.mp.type.instance.MPBaseObj;

/**
 *
 * @author ladilads
 */
public class ObjectDeclExpr extends TokenAwareExpression {

  private final List<Expression> declarations = new ArrayList<>();

  public List<Expression> getExpressions() {
    return declarations;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {

    MPObject obj = new MPBaseObj(scope, null);
    for (Expression d : declarations) {
      d.eval(obj, store);
    }
    return obj;
  }

}
