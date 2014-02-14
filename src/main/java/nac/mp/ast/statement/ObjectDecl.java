/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.ast.Expression;
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
  public MPObject eval(Scope scope) throws EvalException {

    MPObject obj = new MPObject(scope, null);
    scope.declareVarLocal(name, obj);
    for (Expression d : declarations) {
      d.eval(obj);
    }
    return null;
  }

}
