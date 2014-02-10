/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.Type;
import nac.mp.ast.Declaration;
import nac.mp.Scope;
import nac.mp.ast.Expression;
import nac.mp.type.MPObject;

/**
 *
 * @author ladilads
 */
public class ObjectDecl implements Declaration {

  private final String name;
  private final List<Declaration> declarations = new ArrayList<>();

  public ObjectDecl(String name) {
    this.name = name;
  }

  public List<Declaration> getDeclarations() {
    return declarations;
  }

  @Override
  public Type eval(Scope scope) throws EvalException {

    MPObject obj = new MPObject(scope);
    scope.declareVarLocal(name, obj);
    for (Declaration d : declarations) {
      d.eval(obj);
    }
    return null;
  }

}
