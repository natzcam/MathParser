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
import nac.mp.ast.statement.OneToManyDecl;

/**
 *
 * @author user
 */
public class MySQLTable implements Emittable {

  private final ModelDecl model;
  private final String engine = "InnoDB";
  private final List<MySQLColumn> columns = new ArrayList<>();
  private final Map<String, ModelDecl> modelRepo;
  
//   for (ModelDecl modelDecl : modelRepo.values()) {
//        Emittable tb = new MySQLTable(modelRepo, modelDecl);
//        StringBuilder sb = new StringBuilder();
//        tb.emit(sb);
//        log.debug(sb.toString());
//        if (sb.length() != 0) {
//          jdbcTemplate.update(sb.toString());
//        }
//      }
//      for (OneToManyDecl otm : oneToManyRepo) {
//        MySQLOneToMany motm = new MySQLOneToMany(otm);
//        StringBuilder sb = new StringBuilder();
//        motm.emit(sb);
//        log.debug(sb.toString());
//        if (sb.length() != 0) {
//          jdbcTemplate.update(sb.toString());
//        }
//      }
  
  public MySQLTable(Map<String, ModelDecl> modelRepo, ModelDecl model) {
    this.modelRepo = modelRepo;
    this.model = model;
    for (AttributeDecl decl : model.getAttrDecls()) {
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
    query.append("CREATE TABLE IF NOT EXISTS ").append(name).append(" (");
    query.append("\n");
    query.append("id INT NOT NULL AUTO_INCREMENT");
    for (MySQLColumn c : columns) {
      query.append(",");
      query.append("\n");
      c.emit(query);
    }
    query.append(",");
    query.append("\n");
    query.append("PRIMARY KEY (id)");
    query.append(") ENGINE=").append(engine).append(";");
  }
}
