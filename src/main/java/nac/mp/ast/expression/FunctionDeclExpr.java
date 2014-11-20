/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.ObjectStore;
import nac.mp.ast.Block;
import nac.mp.ast.Expression;
import nac.mp.ast.Scope;
import nac.mp.type.instance.MPFunc;
import nac.mp.type.instance.MPObject;

/**
 *
 * @author ladilads
 */
public class FunctionDeclExpr implements Expression {

  private final List<String> argNames = new ArrayList<>();
  private Block body;

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
    return func;
  }
}
