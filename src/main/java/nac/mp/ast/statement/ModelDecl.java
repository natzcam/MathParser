/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.ast.Scope;
import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.type.MPModel;

/**
 *
 * @author camomon
 */
public class ModelDecl implements Expression {

  private final String name;
  private final List<AttributeDecl> declarations = new ArrayList<>();

  public ModelDecl(String name) {
    this.name = name;
  }

  public List<AttributeDecl> getDeclarations() {
    return declarations;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPModel model = new MPModel(scope, name);
    scope.declareVarLocal(name, model);
    for (AttributeDecl attributeDecl : declarations) {
      attributeDecl.eval(model);
    }
    return null;
  }
}
