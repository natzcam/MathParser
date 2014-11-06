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
  private final String type;
  private final String identifier;
  private final Map<String, ModelDecl> modelRepo;
  private final MySQLColumn.ColumnType columnType;

  public MySQLColumn(Map<String, ModelDecl> modelRepo, String type, String identifier) {
    this.modelRepo = modelRepo;
    this.type = type;
    this.identifier = identifier;

    switch (type) {
      case "string":
        columnType = MySQLColumn.ColumnType.STRING;
        break;
      case "int":
        columnType = MySQLColumn.ColumnType.INTEGER;
        break;
      case "bool":
        columnType = MySQLColumn.ColumnType.BOOLEAN;
        break;
      case "float":
        columnType = MySQLColumn.ColumnType.FLOAT;
        break;
      default:
        columnType = MySQLColumn.ColumnType.REFERENCE;
        log.debug("type {}", type);
        break;
    }
  }

  @Override
  public void emit(StringBuilder query) {
    query.append(identifier).append(" ").append(columnType.toString());
    if (columnType == ColumnType.REFERENCE) {
      query.append(", FOREIGN KEY (").append(identifier).append(")");
      query.append(" REFERENCES ").append(modelRepo.get(type).getName()).append("(__id__)");
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
