/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.mysql;

import nac.mp.store.Emittable;
import java.util.Map;
import nac.mp.ast.statement.ModelDecl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author user
 */
public class MySQLColumn implements Emittable {

  private static final Logger log = LogManager.getLogger(MySQLColumn.class);
  private final String identifier;
  private final Map<String, ModelDecl> modelRepo;
  private String columnType = null;

  public MySQLColumn(Map<String, ModelDecl> modelRepo, String type, String identifier) {
    this.modelRepo = modelRepo;
    this.identifier = identifier;

    switch (type) {
      case "string":
        columnType = "VARCHAR(255)";
        break;
      case "int":
        columnType = "INT";
        break;
      case "bool":
        columnType = "BIT";
        break;
      case "float":
        columnType = "REAL";
        break;
      case "ref":
      case "list":
        columnType = "INT";
        break;
    }
  }

  @Override
  public void emit(StringBuilder query) {
    query.append(identifier).append(" ").append(columnType);
  }
}
