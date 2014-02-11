/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.Type;
import nac.mp.Type.Hint;
import nac.mp.ast.Expression;
import nac.mp.ast.Statement;
import nac.mp.type.MPObject;
import nac.mp.type.MPString;
import nac.mp.type.MPVoid;
import nac.store.mapdb.ObjectStorage;

/**
 *
 * @author user
 */
public class PersistStmt implements Statement {

  private final Expression object;
  private final ObjectStorage storage;

  public PersistStmt(ObjectStorage storage, Expression object) {
    this.storage = storage;
    this.object = object;
  }

  @Override
  public Type eval(Scope scope) throws EvalException {
    
    MPObject obj = (MPObject) object.eval(scope);
    for (String key : obj.getVarKeys()) {
      Type t = obj.getVar(key);
      if (t.getHint() == Hint.FUNCTION) {
        obj.setVar(key, new MPVoid());
      }
    }
    storage.put(obj.getClazz().toString(), obj);
    return null;
  }
}
