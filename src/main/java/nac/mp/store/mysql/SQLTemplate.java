/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nac.mp.store.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author user
 */
public abstract class SQLTemplate implements Emittable {

  private static final Logger log = LogManager.getLogger(SQLTemplate.class);
  private final JdbcTemplate jdbcTemplate = DBUtil.getJbdcTemplate();
  private final StringBuilder query = new StringBuilder();

  public void create() {
    emit(query);
    log.info("query: {} ", query.toString());
    jdbcTemplate.execute(query.toString());
  }
}
