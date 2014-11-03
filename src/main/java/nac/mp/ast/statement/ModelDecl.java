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

/**
 *
 * @author camomon
 */
public class ModelDecl implements Expression {

  private final String name;
  private final List<Expression> declarations = new ArrayList<>();

  public ModelDecl(String name) {
    this.name = name;
  }

  public List<Expression> getDeclarations() {
    return declarations;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
//    MPClass eclas = null;
//    MPClass clazz = new MPClass(scope, name, eclas, declarations);
//    scope.declareVarLocal(name, clazz);

    return null;
  }
}
