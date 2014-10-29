/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.expression;

import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.ast.Expression;
import nac.mp.store.mapdb.ObjectStorage;
import nac.mp.type.MPClass;
import nac.mp.type.MPInteger;
import nac.mp.type.MPObject;

/**
 *
 * @author ladilads
 */
public class GetExpr implements Expression {

  private Expression clazzExp;
  private Expression idExp;
  private final ObjectStorage storage;

  public GetExpr(ObjectStorage storage) {
    this.storage = storage;
  }

  public void setClazzExp(Expression clazzExp) {
    this.clazzExp = clazzExp;
  }

  public void setIdExp(Expression idExp) {
    this.idExp = idExp;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPInteger id = (MPInteger) idExp.eval(scope);
    MPClass claz = (MPClass) clazzExp.eval(scope);
    return storage.getById(claz.toString(), id);
  }

}
