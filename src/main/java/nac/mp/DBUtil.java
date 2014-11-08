package nac.mp;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Hello world!
 *
 */
public class DBUtil {

  private static JdbcTemplate jdbcTemplate = null;

  public static JdbcTemplate getDefault() {
    if (jdbcTemplate == null) {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName("com.mysql.jdbc.Driver");
      dataSource.setUrl("jdbc:mysql://localhost:3306/mp");
      dataSource.setUsername("root");
      dataSource.setPassword("root");
      jdbcTemplate = new JdbcTemplate(dataSource);
    }
    return jdbcTemplate;
  }
}
