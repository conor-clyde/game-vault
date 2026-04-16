package com.cocoding.playstate.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(0)
public class UserIdAndEmailCleanupRunner implements ApplicationRunner {

  private static final Logger logger = LoggerFactory.getLogger(UserIdAndEmailCleanupRunner.class);

  private final JdbcTemplate jdbcTemplate;

  public UserIdAndEmailCleanupRunner(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  @Transactional
  public void run(ApplicationArguments args) {
    int userGamesUpdated = migrateUserIds("user_games");
    int playLogsUpdated = migrateUserIds("play_logs");
    int playthroughsUpdated = migrateUserIds("user_game_playthroughs");
    int emailsCleared =
        jdbcTemplate.update("update user_accounts set email = null where email is not null");

    if (userGamesUpdated + playLogsUpdated + playthroughsUpdated + emailsCleared > 0) {
      logger.info(
          "User cleanup applied: user_games={}, play_logs={}, user_game_playthroughs={}, emails_cleared={}",
          userGamesUpdated,
          playLogsUpdated,
          playthroughsUpdated,
          emailsCleared);
    }
  }

  private int migrateUserIds(String tableName) {
    String sql =
        "update "
            + tableName
            + " t set user_id = ("
            + "select ua.username from user_accounts ua "
            + "where ua.email is not null and lower(ua.email) = lower(t.user_id)"
            + ") where exists ("
            + "select 1 from user_accounts ua "
            + "where ua.email is not null and lower(ua.email) = lower(t.user_id)"
            + ")";
    return jdbcTemplate.update(sql);
  }
}
