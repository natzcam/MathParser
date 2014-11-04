/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.mysql;

import nac.mp.type.MPModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author user
 */
public class MySQLColumn implements Emittable {
  private static final Logger log = LogManager.getLogger(MySQLColumn.class);
  private final String name;
  private final ColumnType type;
  private final MPModel parent;

  public MySQLColumn(MPModel parent, String name, ColumnType type) {
    this.parent = parent;
    this.name = name;
    this.type = type;
  }

  @Override
  public void emit(StringBuilder query) {
    log.debug(parent);
    query.append(name).append(" ").append(type.toString());
    if (type == ColumnType.REFERENCE) {
      query.append(", FOREIGN KEY (").append(name).append(")");
      query.append(" REFERENCES ").append(parent.getName()).append("__id__");
    }
  }

  public static enum ColumnType {

    INTEGER("INT"), BOOLEAN("BIT"), FLOAT("REAL"), STRING("VARCHAR(255)"), REFERENCE("INT");
    private final String template;

    private ColumnType(String template) {
      this.template = template;
    }

    @Override
    public String toString() {
      return template;
    }

  }

}
