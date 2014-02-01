/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.store.mysql;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author user
 */
public abstract class SQLTemplate implements Emittable {

  private final JdbcTemplate jdbcTemplate = DBUtil.getJbdcTemplate();
  private final StringBuilder query = new StringBuilder();

  public void execute() {
    emit(query);
    System.out.println("query: " + query.toString());
    jdbcTemplate.execute(query.toString());
  }
}
