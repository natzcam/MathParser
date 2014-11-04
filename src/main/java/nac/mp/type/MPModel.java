/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.Creator;
import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.store.mysql.MySQLTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author camomon
 */
public class MPModel extends MPObject implements Creator {

  private static final Logger log = LogManager.getLogger(MPModel.class);
  private final String name;

  public MPModel(Scope parent, String name) {
    super(parent, null);
    this.name = name;
  }

  @Override
  public MPObject.Hint getHint() {
    return MPObject.Hint.MODEL;
  }

  @Override
  public String toString() {
    return "model:" + name;
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case MODEL:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }

  public void register() throws EvalException {
    MySQLTable table = new MySQLTable(name);

    for (MPObject obj : vars.values()) {
      if (obj instanceof MPAttribute) {
        MPAttribute attr = (MPAttribute) obj;
        table.getColumns().add(attr.column());
      }
    }
    StringBuilder sb = new StringBuilder();
    table.emit(sb);
    log.trace(sb.toString());
  }

  @Override
  public MPObject create() throws EvalException {
    MPObject obj = new MPModeledObject(parent, this);

    for (MPObject v : vars.values()) {
      if (v instanceof MPAttribute) {
        MPAttribute attr = (MPAttribute) v;
        obj.declareVarLocal(attr.getName(), attr.create());
      }
    }
    return obj;
  }
}
