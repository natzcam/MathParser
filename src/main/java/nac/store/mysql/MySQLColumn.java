/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.store.mysql;

/**
 *
 * @author user
 */
public class MySQLColumn implements Emittable {

  private final String name;
  private final ColumnType type;

  public MySQLColumn(String name, ColumnType type) {
    this.name = name;
    this.type = type;
  }

  @Override
  public void emit(StringBuilder query) {
    query.append(name).append(" ").append(type.toString());
  }

  public static enum ColumnType {

    INTEGER("INT"), BOOLEAN("BIT"), FLOAT("REAL"), STRING("VARCHAR(255)");
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
