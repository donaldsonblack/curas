package com.dblck.curas.config;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SchemaLogger implements CommandLineRunner {
  private final JdbcTemplate jdbc;
  private final Logger logger = LoggerFactory.getLogger(SchemaLogger.class);

  public SchemaLogger(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public void run(String... args) {
    // What database URL did Hikari actually use?
    String url = jdbc.queryForObject("SELECT current_database()", String.class);
    // What schema is first on the search_path?
    String schema = jdbc.queryForObject("SHOW search_path", String.class);
    // Does our table actually appear?

    List<Map<String, Object>> tables =
        jdbc.queryForList(
            """
            SELECT table_schema, table_name
              FROM information_schema.tables
             WHERE table_type = 'BASE TABLE'
               AND table_schema NOT IN ('pg_catalog', 'information_schema')
            ORDER BY table_schema, table_name
            """);

    logger.info("→ Connected to database: " + url);
    logger.info("→ search_path: " + schema);
    logger.info("→ Seen tables: " + tables);
  }
}
