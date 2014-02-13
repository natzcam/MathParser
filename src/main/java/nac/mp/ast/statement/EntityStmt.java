/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.ast.statement;

import java.util.ArrayList;
import java.util.List;
import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.type.MPObject;;
import nac.mp.ast.Declaration;
import nac.mp.ast.Expression;
import nac.mp.ast.Statement;
import nac.mp.type.MPObject;
import nac.store.mapdb.ObjectStorage;
import nac.store.mysql.MySQLColumn;
import nac.store.mysql.MySQLColumn.ColumnType;
import nac.store.mysql.MySQLTable;

/**
 *
 * @author user
 */
public class EntityStmt implements Statement {

  private final String name;
  private final Expression prototype;
  private final List<Declaration> declarations = new ArrayList<>();
  private final ObjectStorage objectStore;

  public EntityStmt(ObjectStorage objectStore, String name, Expression prototype) {
    this.objectStore = objectStore;
    this.name = name;
    this.prototype = prototype;
  }

  public List<Declaration> getDeclarations() {
    return declarations;
  }

  @Override
  public MPObject eval(Scope scope) throws EvalException {
    MySQLTable table = new MySQLTable(name);
    MPObject object = (MPObject) prototype.eval(scope);
    for (String key : object.getVarKeys()) {
      MySQLColumn col = null;
      MPObject type = object.getVar(key);
      switch (type.getHint()) {
        case BOOLEAN:
          col = new MySQLColumn(key, ColumnType.BOOLEAN);
          break;
        case FLOAT:
          col = new MySQLColumn(key, ColumnType.FLOAT);
          break;
        case INTEGER:
          col = new MySQLColumn(key, ColumnType.INTEGER);
          break;
        case STRING:
          col = new MySQLColumn(key, ColumnType.STRING);
          break;
      }
      if (col != null) {
        table.getColumns().add(col);
      }
    }
    table.execute();
    return null;
  }
}
