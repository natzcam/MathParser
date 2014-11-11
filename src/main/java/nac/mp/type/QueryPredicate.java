/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import java.util.Map;
import nac.mp.EvalException;
import nac.mp.ast.BasicScope;
import nac.mp.ast.Scope;
import nac.mp.ast.WhereBlock;

/**
 *
 * @author camomon
 */
public class QueryPredicate extends MPObject {

  private final WhereBlock body;

  public QueryPredicate(Scope parent, WhereBlock body) {
    super(parent, null);
    this.body = body;
  }

  public WhereBlock getWhereBlock() {
    return body;
  }

  public boolean call(Map<String, MPObject> vars) throws EvalException {
    Scope newScope = new BasicScope(parent);
    newScope.setLocalVars(vars);
    MPBoolean b = (MPBoolean) body.eval(newScope);
    return b.getBoolean();
  }
}
