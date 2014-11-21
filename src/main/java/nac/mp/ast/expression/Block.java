/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.ast.TokenAwareExpression;
import nac.mp.type.instance.MPObject;

/**
 *
 * @author natz
 */
public class Block extends TokenAwareExpression {

  private final List<Expression> statements = new ArrayList<>();

  public void addStatement(Expression statement) {
    statements.add(statement);
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {
    MPObject ret = null;
    for (Expression s : statements) {
      ret = s.eval(scope, store);
      if (ret != null) {
        break;
      }
    }
    return ret;
  }

}
