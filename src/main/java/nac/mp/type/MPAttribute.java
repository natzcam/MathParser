/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.Creator;
import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.store.mysql.MySQLColumn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author camomon
 */
public class MPAttribute extends MPObject implements Creator {

  private static final Logger log = LogManager.getLogger(MPAttribute.class);
  private final String type;
  private final String name;

  public MPAttribute(Scope parent, String type, String name) {
    super(parent, null);
    this.type = type;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public MPObject.Hint getHint() {
    return MPObject.Hint.ATTRIBUTE;
  }

  @Override
  public String toString() {
    return "attr:" + name;
  }

  @Override
  public MPObject notEqual(MPObject right) {
    switch (right.getHint()) {
      case ATTRIBUTE:
        return new MPBoolean(this != right);
    }
    return new MPBoolean(true);
  }

  public MySQLColumn column(){
    MySQLColumn column = new MySQLColumn(name, MySQLColumn.ColumnType.STRING);
    return column;
  }

  @Override
  public MPObject create() throws EvalException {
    switch (type) {
      case "string":
        return new MPVoid();
      case "int":
        return new MPVoid();
      case "bool":
        return new MPVoid();
      case "float":
        return new MPVoid();
      case "list":
        return new MPVoid();
      default:
        MPModel model = (MPModel) parent.getVar(type);
        return model.create();
    }
  }
}
