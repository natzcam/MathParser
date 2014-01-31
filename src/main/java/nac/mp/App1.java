package nac.mp;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Hello world!
 *
 */
public class App1 {

  public static void main(String[] args) {
    System.out.println("Hello World!");
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/mp");
    dataSource.setUsername("root");
    dataSource.setPassword("root");

    JdbcTemplate jt = new JdbcTemplate(dataSource);

  }
}
