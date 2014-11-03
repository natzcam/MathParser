/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.mysql;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class MySQLTable extends SQLTemplate {

  private final String engine = "InnoDB";
  private final String name;
  private final List<MySQLColumn> columns = new ArrayList<>();

  public MySQLTable(String name) {
    this.name = name;
  }

  public List<MySQLColumn> getColumns() {
    return columns;
  }

  @Override
  public void emit(StringBuilder query) {
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
