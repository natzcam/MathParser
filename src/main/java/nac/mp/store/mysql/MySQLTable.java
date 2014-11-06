/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.mysql;

import nac.mp.store.Emittable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nac.mp.ast.statement.AttributeDecl;
import nac.mp.ast.statement.ModelDecl;

/**
 *
 * @author user
 */
public class MySQLTable implements Emittable {

  private final ModelDecl model;
  private final String engine = "InnoDB";
  private final List<MySQLColumn> columns = new ArrayList<>();
  private final Map<String, ModelDecl> modelRepo;

  public MySQLTable(Map<String, ModelDecl> modelRepo, ModelDecl model) {
    this.modelRepo = modelRepo;
    this.model = model;
    for (AttributeDecl decl : model.getDeclarations()) {
      MySQLColumn column = new MySQLColumn(modelRepo, decl.getType(), decl.getIdentifier());
      columns.add(column);
    }
  }

  public List<MySQLColumn> getColumns() {
    return columns;
  }

  @Override
  public void emit(StringBuilder query) {
    String name = model.getName();
    query.append("CREATE TABLE ").append(name).append(" (");
    query.append("__id__ INT NOT NULL AUTO_INCREMENT");
    for (MySQLColumn c : columns) {
      query.append(",");
      c.emit(query);
    }
    query.append(",PRIMARY KEY (__id__)");
    query.append(") ENGINE=").append(engine).append(";");
  }
}
