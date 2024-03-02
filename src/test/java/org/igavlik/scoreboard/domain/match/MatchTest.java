package org.igavlik.scoreboard.domain.match;

import org.igavlik.scoreboard.domain.match.impl.FootballMatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MatchTest {

  @Test
  public void createdMatchIsNotInProgress() {
    Match match = new FootballMatch("homeTeamName", "awayTeamName");
    Assertions.assertFalse(match.isInProgress());
  }

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

  @Test
  public void getScoresNotNegative() {
    Match match = new FootballMatch("homeTeamName", "awayTeamName");
    int homeScore = match.getHomeTeamScore();
    int awayScore = match.getAwayTeamScore();

    Assertions.assertTrue(homeScore >= 0);
    Assertions.assertTrue(awayScore >= 0);
    ;
  }

  @Test
  public void startMatchIsInProgress() {
    Match match = new FootballMatch("homeTeamName", "awayTeamName");
    match.startMatch();

    Assertions.assertTrue(match.isInProgress());
  }

  @Test
  public void startMatchScoreIsNll() {
    Match match = new FootballMatch("homeTeamName", "awayTeamName");
    match.startMatch();

    Assertions.assertEquals(0, match.getHomeTeamScore());
    Assertions.assertEquals(0, match.getAwayTeamScore());
  }

  @Test
  public void updateMatchScore() {
    Match match = new FootballMatch("homeTeam", "awayTeam");
    match.startMatch();

    match.updateMatchScore(1, 0);

    Assertions.assertEquals(1, match.getHomeTeamScore());
    Assertions.assertEquals(0, match.getAwayTeamScore());
    Assertions.assertTrue(match.isInProgress());
  }

  @Test
  public void updateMatchScoreToSame() {
    Match match = new FootballMatch("homeTeam", "awayTeam");
    match.startMatch();

    match.updateMatchScore(1, 0);
    match.updateMatchScore(1, 0);

    Assertions.assertEquals(1, match.getHomeTeamScore());
    Assertions.assertEquals(0, match.getAwayTeamScore());
  }

  @Test
  public void updateMatchScoreTwice() {
    Match match = new FootballMatch("homeTeam", "awayTeam");
    match.startMatch();

    match.updateMatchScore(1, 0);
    match.updateMatchScore(2, 0);

    Assertions.assertEquals(2, match.getHomeTeamScore());
    Assertions.assertEquals(0, match.getAwayTeamScore());
  }

  @Test
  public void updateMatchCanNotBeNegative() {
    Match match = new FootballMatch("homeTeam", "awayTeam");
    match.startMatch();

    match.updateMatchScore(-1, 0);

    Assertions.assertEquals(0, match.getHomeTeamScore());
    Assertions.assertEquals(0, match.getAwayTeamScore());
  }

  @Test
  public void updateMatchCanNotBeSmallerThanPrevious() {
    Match match = new FootballMatch("homeTeam", "awayTeam");
    match.startMatch();

    match.updateMatchScore(1, 0);
    match.updateMatchScore(2, 0);
    match.updateMatchScore(1, 0);

    Assertions.assertEquals(2, match.getHomeTeamScore());
    Assertions.assertEquals(0, match.getAwayTeamScore());
  }

  @Test
  public void updateMatchIsNotIncremental() {
    Match match = new FootballMatch("homeTeam", "awayTeam");

    match.startMatch();
    Assertions.assertEquals(0, match.getHomeTeamScore());
    Assertions.assertEquals(0, match.getAwayTeamScore());

    match.updateMatchScore(2, 0);
    Assertions.assertEquals(2, match.getHomeTeamScore());
    Assertions.assertEquals(0, match.getAwayTeamScore());
  }

  @Test
  public void updateMatchCanNotAppliedIfMatchNotStarted() {
    Match match = new FootballMatch("homeTeam", "awayTeam");

    match.updateMatchScore(1, 0);

    Assertions.assertEquals(0, match.getHomeTeamScore());
    Assertions.assertEquals(0, match.getAwayTeamScore());
    Assertions.assertFalse(match.isInProgress());
  }

}
