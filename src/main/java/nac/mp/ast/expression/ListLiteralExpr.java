/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.instance.MPList;
import nac.mp.type.instance.MPObject;
import nac.mp.type.instance.MPInteger;

/**
 *
 * @author ladilads
 */
public class ListLiteralExpr implements Expression {

  private Expression initSize;
  private final List<Expression> elems = new ArrayList<>();

  public void setInitSize(Expression initSize) {
    this.initSize = initSize;
  }

  public List<Expression> getElems() {
    return elems;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {
    List<MPObject> initValues = new ArrayList<>();

    for (Expression elemExpr : elems) {
      initValues.add(elemExpr.eval(scope, store));
    }
    if (initSize != null) {
      MPInteger initS = (MPInteger) initSize.eval(scope, store);
      return new MPList((int) initS.getInt(), initValues);
    } else {
      return new MPList(initValues.size(), initValues);
    }

  }

}
