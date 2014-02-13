/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast;

import nac.mp.Scope;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nac.mp.EvalException;
import nac.mp.type.MPObject;;

/**
 *
 * @author natz
 */
public class Block implements Expression {

  private final List<Statement> statements = new ArrayList<>();

  public void addStatement(Statement statement) {
    statements.add(statement);
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPObject ret = null;
    for (Statement s : statements) {
      ret = s.eval(scope);
      if (ret != null) {
        break;
      }
    }
    return ret;
  }

}
