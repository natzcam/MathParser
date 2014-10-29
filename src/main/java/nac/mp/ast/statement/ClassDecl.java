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
import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.store.mapdb.ObjectStorage;
import nac.mp.type.MPClass;

/**
 *
 * @author camomon
 */
public class ClassDecl implements Expression {

  private final Expression extnds;
  private final String name;
  private final List<Expression> declarations = new ArrayList<>();
  private final ObjectStorage objectStorage;

  public ClassDecl(ObjectStorage objectStorage, Expression extnds, String name) {
    this.objectStorage = objectStorage;
    this.extnds = extnds;
    this.name = name;
  }

  public List<Expression> getDeclarations() {
    return declarations;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPClass eclas = null;
    if (extnds != null) {
      eclas = (MPClass) extnds.eval(scope);
    }
    MPClass clazz = new MPClass(scope, name, eclas, declarations);
    scope.declareVarLocal(name, clazz);
    objectStorage.register(clazz);

    return null;
  }
}
