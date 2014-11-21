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
import nac.mp.ast.TokenAwareExpression;
import nac.mp.type.instance.MPFunc;
import nac.mp.type.instance.MPObject;

/**
 *
 * @author ladilads
 */
public class FunctionDeclExpr extends TokenAwareExpression {

  private final List<String> argNames = new ArrayList<>();
  private Expression body;

  public void setBody(Expression body) {
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
