/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast;

import java.util.ArrayList;
import java.util.List;
import nac.mp.ObjectStore;
import nac.mp.type.MPObject;

/**
 *
 * @author natz
 */
public class WhereBlock extends TokenAwareExpression {

  private final List<Expression> statements = new ArrayList<>();

  public void addStatement(Expression statement) {
    statements.add(statement);
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) {
    MPObject ret = null;
    
    if (statements.size() == 1) {
      return statements.get(0).eval(scope, store);
    }

    for (Expression s : statements) {
      ret = s.eval(scope, store);
      if (ret != null) {
        break;
      }
    }
    return ret;
  }

}
