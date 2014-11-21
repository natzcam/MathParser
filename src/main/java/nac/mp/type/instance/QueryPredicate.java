/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type.instance;

import nac.mp.ObjectStore;
import nac.mp.ast.BasicScope;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.MPObject;
import nac.mp.type.Type;

/**
 *
 * @author camomon
 */
public class QueryPredicate extends MPObject {

  private final Expression body;

  public QueryPredicate(Scope parent, Expression body) {
    super(parent, null);
    this.body = body;
  }

  public Expression getWhereBlock() {
    return body;
  }

  public boolean call(MPObject thisObj, ObjectStore store) {
    Scope newScope = new BasicScope(parent);
    newScope.setLocalVar("this", thisObj);
    MPBoolean b = (MPBoolean) body.eval(newScope, store);
    return b.getBoolean();
  }

  @Override
  public Type getType() {
    return Type.PREDICATE;
  }
}
