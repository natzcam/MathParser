/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.ArrayList;
import java.util.List;
import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.ast.TokenAwareExpression;
import nac.mp.type.MPObject;
import nac.mp.type.instance.MPList;

/**
 *
 * @author ladilads
 */
public class ListLiteralExpr extends TokenAwareExpression {

  private Expression initSize;
  private final List<Expression> elems = new ArrayList<>();

  public void setInitSize(Expression initSize) {
    this.initSize = initSize;
  }

  public List<Expression> getElems() {
    return elems;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    List<MPObject> initValues = new ArrayList<>();

    for (Expression elemExpr : elems) {
      initValues.add(elemExpr.eval(scope, store));
    }
    if (initSize != null) {
      MPObject initS = initSize.eval(scope, store);
      return new MPList((int) initS.getInt(), initValues);
    } else {
      return new MPList(initValues.size(), initValues);
    }

  }

}
