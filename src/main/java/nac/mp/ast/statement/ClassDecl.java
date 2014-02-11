/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.Type;
import nac.mp.ast.Declaration;
import nac.mp.ast.Expression;
import nac.mp.type.MPClass;

/**
 *
 * @author camomon
 */
public class ClassDecl implements Declaration {

  private final Expression extnds;
  private final String name;
  private final List<Declaration> declarations = new ArrayList<>();

  public ClassDecl(Expression extnds, String name) {
    this.extnds = extnds;
    this.name = name;
  }

  public List<Declaration> getDeclarations() {
    return declarations;
  }

  @Override
  public Type eval(Scope scope) throws EvalException {
    MPClass eclas = null;
    if (extnds != null) {
      eclas = (MPClass) extnds.eval(scope);
    }
    MPClass clazz = new MPClass(scope, eclas, declarations);
    scope.declareVarLocal(name, clazz);

    return null;
  }
}
