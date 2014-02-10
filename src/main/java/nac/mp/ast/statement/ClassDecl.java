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
import nac.mp.ast.Block;
import nac.mp.ast.Declaration;
import nac.mp.ast.Expression;
import nac.mp.ast.Statement;
import nac.mp.type.MPClass;
import nac.mp.type.MPObject;

/**
 *
 * @author camomon
 */
public class ClassDecl implements Statement {

  private final String name;
  private final List<Declaration> declarations = new ArrayList<>();

  public ClassDecl(String name) {
    this.name = name;
  }

  public List<Declaration> getDeclarations() {
    return declarations;
  }

  @Override
  public Type eval(Scope scope) throws EvalException {

    MPObject proto = null;
    if (prototype != null) {
      proto = (MPObject) prototype.eval(scope);
    }
    MPClass func = new MPClass(scope, body);
    func.getFormalArgs().addAll(argNames);
    scope.declareVarLocal(name, func);
    return null;
  }
}
