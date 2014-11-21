/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import java.util.ArrayList;
import java.util.List;
import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.ast.TokenAwareExpression;
import nac.mp.type.MPModel;
import nac.mp.type.MPObject;

/**
 *
 * @author camomon
 */
public class ModelDecl extends TokenAwareExpression {

  private final String name;
  private final List<Expression> attributes = new ArrayList<>();

  public ModelDecl(String name) {
    this.name = name;
  }

  public List<Expression> getAttributes() {
    return attributes;
  }

//  public Collection<AttributeDecl> getAttrDecls() {
//    return attrMap.values();
//  }
//
//  public AttributeDecl getAttrDecl(String name) {
//    return attrMap.get(name);
//  }

  public String getName() {
    return name;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    MPModel model = new MPModel(scope, name);
    scope.declareVar(name, model);
    for (Expression attributeDecl : attributes) {
      attributeDecl.eval(model, store);
    }
    store.register(model);
    return null;
  }
}
