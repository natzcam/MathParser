/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Scope;
import nac.mp.ast.Expression;
import nac.mp.type.MPBaseObj;
import nac.mp.type.MPObject;

/**
 *
 * @author ladilads
 */
public class ObjectDeclExpr implements Expression {

  private final List<Expression> declarations = new ArrayList<>();

  public List<Expression> getExpressions() {
    return declarations;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {

    MPObject obj = new MPBaseObj(scope, null);
    for (Expression d : declarations) {
      d.eval(obj, store);
    }
    return obj;
  }

}
