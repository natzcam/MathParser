/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.type.MPObject;
import nac.mp.ast.Scope;
import nac.mp.ast.Block;
import nac.mp.ast.Expression;
import nac.mp.type.MPFunc;

/**
 *
 * @author ladilads
 */
public class FunctionDecl implements Expression {

  private final String name;
  private final List<String> argNames = new ArrayList<>();
  private Block body;

  public FunctionDecl(String name) {
    this.name = name;
  }

  public void setBody(Block body) {
    this.body = body;
  }

  public List<String> getArgNames() {
    return argNames;
  }

  @Override
  public MPObject eval(Scope scope, ObjectStore store) throws EvalException {
    MPFunc func = new MPFunc(scope, body);
    func.getFormalArgs().addAll(argNames);
    scope.declareLocalVar(name, func);
    return null;
  }
}
