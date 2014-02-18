package nac.mp.ast.statement;

import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.type.MPObject;
import nac.mp.ast.Expression;
import nac.mp.type.MPInteger;
import nac.mp.type.MPString;
import nac.mp.store.mapdb.ObjectStorage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class RestoreStmt implements Expression {

  private final ObjectStorage objectStore;
  private final Expression collectionName;
  private final Expression id;
  private final String obj;

  public RestoreStmt(ObjectStorage objectStore, Expression collectionName, Expression id, String obj) {
    this.objectStore = objectStore;
    this.collectionName = collectionName;
    this.id = id;
    this.obj = obj;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MPString col = (MPString) collectionName.eval(scope);
    MPInteger key = (MPInteger) id.eval(scope);
    scope.setVar(obj, objectStore.get(col.getString(), key));
    return null;
  }

}
