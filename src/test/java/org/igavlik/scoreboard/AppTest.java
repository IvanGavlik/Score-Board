package org.igavlik.scoreboard;

import java.util.List;
import org.igavlik.scoreboard.domain.match.Match;
import org.igavlik.scoreboard.domain.match.MatchService;
import org.igavlik.scoreboard.domain.match.data.MatchRepoInMemory;
import org.igavlik.scoreboard.domain.match.impl.FootballMatch;
import org.igavlik.scoreboard.domain.match.impl.FootballMatchService;
import org.igavlik.scoreboard.domain.scoreboard.ScoreBoard;
import org.igavlik.scoreboard.domain.scoreboard.impl.FootballLiveScoreBoard;
import org.igavlik.scoreboard.util.RepoTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Integration tests and examples How to use this lib
 */
public class AppTest extends RepoTest {

  @Test
  public void basic() {
    MatchService matchService = new FootballMatchService(MatchRepoInMemory.INSTANCE);

    Match match = new FootballMatch("a", "b");
    matchService.startMatch(match);
    matchService.updateMatchScore(match, 1, 0);

    ScoreBoard matchScoreBoard = new FootballLiveScoreBoard(MatchRepoInMemory.INSTANCE);
    List<Match> matchList = matchScoreBoard.getMatchesInProgress();

    Assertions.assertEquals(1, matchList.size());
  }

  @Test
  public void finishedMatchAreNotShown() {
    MatchService matchService = new FootballMatchService(MatchRepoInMemory.INSTANCE);

    Match match = new FootballMatch("a", "b");
    matchService.startMatch(match);
    matchService.updateMatchScore(match, 1, 0);
    matchService.finishMatch(match);

    ScoreBoard matchScoreBoard = new FootballLiveScoreBoard(MatchRepoInMemory.INSTANCE);
    List<Match> matchList = matchScoreBoard.getMatchesInProgress();

    Assertions.assertEquals(0, matchList.size());
  }

  @Test
  public void moreMatches() {
    Match meca = new FootballMatch("Mexico", "Canada");
    Match spbr = new FootballMatch("Spain", "Brazil");
    Match gefr = new FootballMatch("Germany", "France");
    Match urit = new FootballMatch("Uruguay", "Italy");
    Match arau = new FootballMatch("Argentina", "Australia");

    MatchService matchService = new FootballMatchService(MatchRepoInMemory.INSTANCE);
    matchService.startMatch(meca);
    matchService.updateMatchScore(meca, 0, 5);

    matchService.startMatch(spbr);
    matchService.updateMatchScore(spbr, 10, 2);

    matchService.startMatch(gefr);
    matchService.updateMatchScore(gefr, 2, 2);

    matchService.startMatch(urit);
    matchService.updateMatchScore(urit, 6, 6);

    matchService.startMatch(arau);
    matchService.updateMatchScore(arau, 3, 1);

    ScoreBoard scoreBoard = new FootballLiveScoreBoard(MatchRepoInMemory.INSTANCE);
    List<Match> matchList = scoreBoard.getMatchesInProgress();

    Assertions.assertTrue(urit.equals(matchList.get(0)));
    Assertions.assertTrue(spbr.equals(matchList.get(1)));
    Assertions.assertTrue(meca.equals(matchList.get(2)));
    Assertions.assertTrue(arau.equals(matchList.get(3)));
    Assertions.assertTrue(gefr.equals(matchList.get(4)));
  }

  @Test
  public void canNoStartMatchInProgress() {
    Match meca = new FootballMatch("Mexico", "Canada");
    Match meca1 = new FootballMatch("Mexico", "Canada");

    MatchService matchService = new FootballMatchService(MatchRepoInMemory.INSTANCE);
    matchService.startMatch(meca);
    matchService.startMatch(meca1);

    ScoreBoard scoreBoard = new FootballLiveScoreBoard(MatchRepoInMemory.INSTANCE);
    List<Match> matchList = scoreBoard.getMatchesInProgress();

    Assertions.assertEquals(1, matchList.size());
    Assertions.assertEquals(meca, matchList.get(0));
  }

  @Test
  public void canNoStartMatchInProgressGuessTeamSame() {
    Match meca = new FootballMatch("Mexico", "Canada");
    Match spma = new FootballMatch("Spain", "Mexico");

    MatchService matchService = new FootballMatchService(MatchRepoInMemory.INSTANCE);
    matchService.startMatch(meca);
    matchService.startMatch(spma);

    ScoreBoard scoreBoard = new FootballLiveScoreBoard(MatchRepoInMemory.INSTANCE);
    List<Match> matchList = scoreBoard.getMatchesInProgress();

    Assertions.assertEquals(1, matchList.size());
    Assertions.assertEquals(meca, matchList.get(0));
  }

  @Test
  public void canStarSecondMatchAfterFirstIsDone() {
    Match meca = new FootballMatch("Mexico", "Canada");
    Match meca1 = new FootballMatch("Mexico", "Canada");

    MatchService matchService = new FootballMatchService(MatchRepoInMemory.INSTANCE);
    matchService.startMatch(meca);
    matchService.finishMatch(meca);
    matchService.startMatch(meca1);

    ScoreBoard scoreBoard = new FootballLiveScoreBoard(MatchRepoInMemory.INSTANCE);
    List<Match> matchList = scoreBoard.getMatchesInProgress();

    Assertions.assertEquals(1, matchList.size());
    Assertions.assertEquals(meca1, matchList.get(0));
  }

}
