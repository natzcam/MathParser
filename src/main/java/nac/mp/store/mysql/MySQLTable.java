/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.mysql;

import java.util.ArrayList;
import java.util.List;
import nac.mp.type.MPAttribute;
import nac.mp.type.MPModel;
import nac.mp.type.MPObject;

/**
 *
 * @author user
 */
public class MySQLTable extends SQLTemplate {

  private final MPModel model;
  private final String engine = "InnoDB";
  private final List<MySQLColumn> columns = new ArrayList<>();

  public MySQLTable(MPModel model) {
    this.model = model;

    for (MPObject obj : model.getVarValues()) {
      if (obj instanceof MPAttribute) {
        MPAttribute attr = (MPAttribute) obj;
        columns.add(attr.column());
      }
    }
  }

  @Override
  public void emit(StringBuilder query) {
    String name = model.getName();
    query.append("CREATE TABLE IF NOT EXISTS ").append(name).append(" (");
    query.append(name).append("_id INT NOT NULL AUTO_INCREMENT");
    for (MySQLColumn c : columns) {
      query.append(",");
      c.emit(query);
    }
    query.append(",PRIMARY KEY (").append(name).append("_id)");
    query.append(") ENGINE=").append(engine).append(";");
  }
}
