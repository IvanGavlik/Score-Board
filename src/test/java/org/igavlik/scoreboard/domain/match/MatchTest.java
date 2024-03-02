package org.igavlik.scoreboard.domain.match;

import org.igavlik.scoreboard.domain.match.impl.FootballMatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatchTest {

  @Test
  public void getTeamsHaveValue() {
    Match match = new FootballMatch("homeTeamName", "awayTeamName");
    String home = match.getHomeTeam();
    String away = match.getAwayTeam();

    Assertions.assertNotNull(home);
    Assertions.assertNotNull(away);

    Assertions.assertFalse(home.isEmpty());
    Assertions.assertFalse(away.isEmpty());
  }

}
