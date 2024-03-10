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
  public void createdMatchIsNotStarted() {
    Match match = new FootballMatch("homeTeamName", "awayTeamName");
    Assertions.assertNull(match.getStartedAt());
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
  public void startMatchHasStartedAT() {
    Match match = new FootballMatch("homeTeamName", "awayTeamName");
    match.startMatch();

    Assertions.assertNotNull(match.getStartedAt());
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
    Assertions.assertNotNull(match.getStartedAt());
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
    Assertions.assertNull(match.getStartedAt());
  }

  @Test
  public void afterUpdateCanNotRestartMatch() {
    Match match = new FootballMatch("homeTeam", "awayTeam");
    match.startMatch();
    match.updateMatchScore(1, 0);
    match.startMatch();

    Assertions.assertEquals(1, match.getHomeTeamScore());
    Assertions.assertEquals(0, match.getAwayTeamScore());
    Assertions.assertTrue(match.isInProgress());
    Assertions.assertNotNull(match.getStartedAt());
  }

  @Test
  public void finishMatch() {
    Match match = new FootballMatch("homeTeam", "awayTeam");
    match.startMatch();
    match.updateMatchScore(1, 0);
    match.finishMatch();

    Assertions.assertEquals(1, match.getHomeTeamScore());
    Assertions.assertEquals(0, match.getAwayTeamScore());
    Assertions.assertFalse(match.isInProgress());
    Assertions.assertNotNull(match.getStartedAt());
  }

  @Test
  public void afterMatchIsFinishedUpdateIsNotApplied() {
    Match match = new FootballMatch("homeTeam", "awayTeam");
    match.startMatch();
    match.updateMatchScore(1, 0);
    match.finishMatch();
    match.updateMatchScore(2, 0);

    Assertions.assertEquals(1, match.getHomeTeamScore());
    Assertions.assertEquals(0, match.getAwayTeamScore());
    Assertions.assertFalse(match.isInProgress());
    Assertions.assertNotNull(match.getStartedAt());
  }

  @Test
  public void afterMatchIsFinishedCanNotBeStarted() {
    Match match = new FootballMatch("homeTeam", "awayTeam");
    match.startMatch();
    match.updateMatchScore(1, 0);
    match.finishMatch();
    match.startMatch();

    Assertions.assertEquals(1, match.getHomeTeamScore());
    Assertions.assertEquals(0, match.getAwayTeamScore());
    Assertions.assertFalse(match.isInProgress());
    Assertions.assertNotNull(match.getStartedAt());
  }

  @Test
  public void afterMatchIsFinishedCanNotBeStartedAndUpdate() {
    Match match = new FootballMatch("homeTeam", "awayTeam");
    match.startMatch();
    match.updateMatchScore(1, 0);
    match.finishMatch();
    match.startMatch();
    match.updateMatchScore(2, 0);

    Assertions.assertEquals(1, match.getHomeTeamScore());
    Assertions.assertEquals(0, match.getAwayTeamScore());
    Assertions.assertFalse(match.isInProgress());
    Assertions.assertNotNull(match.getStartedAt());
  }

  @Test
  public void teamInTwoMatchesOneFirstInProgress() {
    Match m1 = new FootballMatch("a", "b");
    m1.startMatch();
    Match m2 = new FootballMatch("f", "a");

    boolean haveTeam = Match.isTeamInTwoMatchesWhereFirstIsInProgress(m1, m2);
    Assertions.assertTrue(haveTeam);

    m1 = new FootballMatch("a", "b");
    m1.startMatch();
    m2 = new FootballMatch("a", "f");

    haveTeam = Match.isTeamInTwoMatchesWhereFirstIsInProgress(m1, m2);
    Assertions.assertTrue(haveTeam);

    m1 = new FootballMatch("a", "b");
    m1.startMatch();
    m2 = new FootballMatch("b", "f");

    haveTeam = Match.isTeamInTwoMatchesWhereFirstIsInProgress(m1, m2);
    Assertions.assertTrue(haveTeam);

    m1 = new FootballMatch("a", "b");
    m1.startMatch();
    m2 = new FootballMatch("f", "b");

    haveTeam = Match.isTeamInTwoMatchesWhereFirstIsInProgress(m1, m2);
    Assertions.assertTrue(haveTeam);
  }

  @Test
  public void teamInTwoMatchesOneSecondInProgress() {
    Match m1 = new FootballMatch("a", "b");
    m1.startMatch();
    m1.finishMatch();
    Match m2 = new FootballMatch("f", "a");
    m2.startMatch();

    boolean haveTeam = Match.isTeamInTwoMatchesWhereFirstIsInProgress(m1, m2);
    Assertions.assertFalse(haveTeam);

    m1 = new FootballMatch("a", "b");
    m1.startMatch();
    m1.finishMatch();
    m2 = new FootballMatch("a", "f");
    m2.startMatch();

    haveTeam = Match.isTeamInTwoMatchesWhereFirstIsInProgress(m1, m2);
    Assertions.assertFalse(haveTeam);

    m1 = new FootballMatch("a", "b");
    m1.startMatch();
    m1.finishMatch();
    m2 = new FootballMatch("b", "f");
    m2.startMatch();

    haveTeam = Match.isTeamInTwoMatchesWhereFirstIsInProgress(m1, m2);
    Assertions.assertFalse(haveTeam);

    m1 = new FootballMatch("a", "b");
    m1.startMatch();
    m1.finishMatch();
    m2 = new FootballMatch("f", "b");
    m2.startMatch();

    haveTeam = Match.isTeamInTwoMatchesWhereFirstIsInProgress(m1, m2);
    Assertions.assertFalse(haveTeam);
  }

  @Test
  public void teamInTwoMatchesNullInput() {
    Assertions.assertFalse(Match.isTeamInTwoMatchesWhereFirstIsInProgress(null, null));

    Match m1 = new FootballMatch("a", "b");
    m1.startMatch();
    ;
    Assertions.assertFalse(Match.isTeamInTwoMatchesWhereFirstIsInProgress(m1, null));
    Assertions.assertFalse(Match.isTeamInTwoMatchesWhereFirstIsInProgress(null, m1));
  }

}
