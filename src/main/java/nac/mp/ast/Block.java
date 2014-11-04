/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.type.MPObject;

/**
 *
 * @author natz
 */
public class Block implements Expression {

  private final List<Expression> statements = new ArrayList<>();

  public void addStatement(Expression statement) {
    statements.add(statement);
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPObject ret = null;
    for (Expression s : statements) {
      ret = s.eval(scope);
      if (ret != null) {
        break;
      }
    }
    return ret;
  }

}
