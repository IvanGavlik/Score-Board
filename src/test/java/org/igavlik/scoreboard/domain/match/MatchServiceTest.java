package org.igavlik.scoreboard.domain.match;

import java.util.List;
import org.igavlik.scoreboard.data.Repo;
import org.igavlik.scoreboard.domain.match.data.MatchRepoInMemory;
import org.igavlik.scoreboard.domain.match.impl.FootballMatch;
import org.igavlik.scoreboard.domain.match.impl.FootballMatchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MatchServiceTest {

  private final Repo<Match> matchRepo = MatchRepoInMemory.INSTANCE;

  @BeforeEach
  public void init() {
    // hack to delete data between test cases because we have singleton
    getAll()
        .forEach(matchRepo::delete);
  }

  @Test
  public void startMatch() {
    Match m = new FootballMatch("a", "b");

    MatchService matchService = new FootballMatchService(matchRepo);
    matchService.startMatch(m);

    Assertions.assertEquals(1, getAll().size());
    Assertions.assertTrue(getAll().get(0).isInProgress());
  }

  @Test
  public void startSameMatch() {
    Match m = new FootballMatch("a", "b");
    Match m2 = new FootballMatch("a", "b");

    MatchService matchService = new FootballMatchService(matchRepo);
    matchService.startMatch(m);
    matchService.startMatch(m2);

    Assertions.assertEquals(1, getAll().size());
    Assertions.assertTrue(getAll().get(0).isInProgress());
  }

  @Test
  public void starSecondMatch() {
    Match m = new FootballMatch("a", "b");
    Match m2 = new FootballMatch("a", "c");

    MatchService matchService = new FootballMatchService(matchRepo);
    matchService.startMatch(m);
    matchService.startMatch(m2);

    Assertions.assertEquals(2, getAll().size());
    Assertions.assertTrue(getAll().get(0).isInProgress());
    Assertions.assertTrue(getAll().get(1).isInProgress());
  }

  @Test
  public void startMatchNull() {
    MatchService matchService = new FootballMatchService(matchRepo);
    matchService.startMatch(null);

    Assertions.assertEquals(0, getAll().size());
  }

  @Test
  public void update() {
    Match m = new FootballMatch("a", "b");

    MatchService matchService = new FootballMatchService(matchRepo);
    matchService.startMatch(m);
    matchService.updateMatchScore(m, 1, 0);

    List<Match> matchList = matchRepo.filter(el -> el.equals(m), null);
    Assertions.assertEquals(1, matchList.size());
    Assertions.assertEquals(1, matchList.get(0).getHomeTeamScore());
  }

  @Test
  public void updateTwice() {
    Match m = new FootballMatch("a", "b");

    MatchService matchService = new FootballMatchService(matchRepo);
    matchService.startMatch(m);
    matchService.updateMatchScore(m, 1, 0);
    matchService.updateMatchScore(m, 2, 0);

    List<Match> matchList = matchRepo.filter(el -> el.equals(m), null);
    Assertions.assertEquals(1, matchList.size());
    Assertions.assertEquals(2, matchList.get(0).getHomeTeamScore());
  }

  @Test
  public void toUpdateStartMatch() {
    Match match = new FootballMatch("a", "b");
    MatchService matchService = new FootballMatchService(matchRepo);
    matchService.updateMatchScore(match, 1, 1);

    Assertions.assertEquals(0, getAll().size());

  }

  @Test
  public void updateNull() {
    MatchService matchService = new FootballMatchService(matchRepo);
    matchService.updateMatchScore(null, 1, 0);

    Assertions.assertEquals(0, getAll().size());
  }

  @Test
  public void finish() {
    Match match = new FootballMatch("a", "b");
    MatchService matchService = new FootballMatchService(matchRepo);
    matchService.startMatch(match);
    matchService.finishMatch(match);

    Assertions.assertEquals(1, getAll().size());
    Assertions.assertFalse(getAll().get(0).isInProgress());
  }

  @Test
  public void finishNull() {
    MatchService matchService = new FootballMatchService(matchRepo);
    matchService.finishMatch(null);

    Assertions.assertEquals(0, getAll().size());
  }

  @Test
  public void toFinishStartMatch() {
    Match match = new FootballMatch("a", "b");
    MatchService matchService = new FootballMatchService(matchRepo);
    matchService.finishMatch(match);

    Assertions.assertEquals(0, getAll().size());

  }


  // TODO refactor - duplicate
  private List<Match> getAll() {
    return matchRepo.filter(null, null);
  }

}
