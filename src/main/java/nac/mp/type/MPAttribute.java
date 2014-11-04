/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.type;

import nac.mp.EvalException;
import nac.mp.Scope;
import nac.mp.store.mysql.MySQLColumn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author camomon
 */
public class MPAttribute extends MPObject {

  private static final Logger log = LogManager.getLogger(MPAttribute.class);
  private final String type;
  private final String name;

  public MPAttribute(Scope parent, String type, String name) {
    super(parent, null);
    this.type = type;
    this.name = name;
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

  public MySQLColumn column() throws EvalException {
    MySQLColumn column = new MySQLColumn(name, MySQLColumn.ColumnType.STRING);
    return column;
  }
}
