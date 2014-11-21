/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.ast.TokenAwareExpression;
import nac.mp.type.MPObject;
import nac.mp.type.instance.MPModelObj;

/**
 *
 * @author nathaniel
 */
public class Save extends TokenAwareExpression {

  private final Expression expression;

  public Save(Expression expression) {
    this.expression = expression;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    MPModelObj obj = (MPModelObj) expression.eval(scope, store);
    store.save(obj);
    return null;
  }
}
