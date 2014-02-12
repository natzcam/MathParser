/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.Type;
import nac.mp.Scope;
import nac.mp.ast.Expression;
import nac.mp.ast.Factor;
import nac.mp.type.MPInteger;
import nac.mp.type.MPList;

/**
 *
 * @author ladilads
 */
public class ListExpr implements Factor {

  private Expression initSize;
  private final List<Expression> elems = new ArrayList<>();

  public void setInitSize(Expression initSize) {
    this.initSize = initSize;
  }

  public List<Expression> getElems() {
    return elems;
  }

  @Override
  public Type eval(Scope scope) throws EvalException {

    List<Type> initValues = new ArrayList<>();

    for (Expression elemExpr : elems) {
      initValues.add(elemExpr.eval(scope));
    }
    MPInteger initS = (MPInteger) initSize.eval(scope);
    return new MPList((int) initS.getInt(), initValues);
  }

}
