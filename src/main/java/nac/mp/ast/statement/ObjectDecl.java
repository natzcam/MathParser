/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

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
public class ObjectDecl implements Expression {

  private final String name;
  private final List<Expression> declarations = new ArrayList<>();

  public ObjectDecl(String name) {
    this.name = name;
  }

  public List<Expression> getExpressions() {
    return declarations;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {

    MPObject obj = new MPBaseObj(scope, null);
    scope.declareLocalVar(name, obj);
    for (Expression d : declarations) {
      d.eval(obj, store);
    }
    return null;
  }

}
