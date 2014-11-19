/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.MPModel;
import nac.mp.type.MPObject;

/**
 *
 * @author camomon
 */
public class ModelDecl implements Expression {

  private final String name;
  private final Map<String, AttributeDecl> attrMap = new HashMap<>();

  public ModelDecl(String name) {
    this.name = name;
  }

  public void addAttrDecl(AttributeDecl attr) {
    attrMap.put(attr.getIdentifier(), attr);
  }

  public Collection<AttributeDecl> getAttrDecls() {
    return attrMap.values();
  }

  public AttributeDecl getAttrDecl(String name) {
    return attrMap.get(name);
  }

  public String getName() {
    return name;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {
    MPModel model = new MPModel(scope, name);
    scope.declareLocalVar(name, model);
    for (AttributeDecl attributeDecl : attrMap.values()) {
      attributeDecl.eval(model, store);
    }
    store.register(model);
    return null;
  }
}
