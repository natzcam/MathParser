/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.ast.TokenAwareExpression;
import nac.mp.type.MPObject;
import nac.mp.type.instance.MPVoid;

/**
 *
 * @author natz
 */
public class Return extends TokenAwareExpression {

  private final Expression expression;

  public Return(Expression expression) {
    this.expression = expression;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    if (expression == null) {
      return MPVoid.VOID;
    } else {
      return expression.eval(scope, store);
    }
  }
}
