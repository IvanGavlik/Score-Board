package org.igavlik.scoreboard.domain.match.data;


import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import org.igavlik.scoreboard.data.Repo;
import org.igavlik.scoreboard.domain.match.Match;
import org.igavlik.scoreboard.domain.match.impl.FootballMatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MatchRepoInMemoryTest {

  private final Repo<Match> matchRepo = MatchRepoInMemory.INSTANCE;

  @BeforeEach
  public void init() {
    // hack to delete data between test cases because we have singleton
    getAll()
        .forEach(matchRepo::delete);
  }

  @Test
  public void testSaveTwice() {
    var fm1 = new FootballMatch("a", "b");
    matchRepo.save(fm1);
    matchRepo.save(fm1);
    int all = getAll().size();
    Assertions.assertEquals(1, all);
  }

  @Test
  public void testSave() {
    matchRepo.save(new FootballMatch("a", "b"));
    int all = getAll().size();
    Assertions.assertEquals(1, all);
  }


  @Test
  public void testSaveAlreadyExist() {
    matchRepo.save(new FootballMatch("a", "c"));
    matchRepo.save(new FootballMatch("a", "c"));
    int all = getAll().size();
    Assertions.assertEquals(1, all);
  }

  @Test
  public void testSaveNull() {
    matchRepo.save(null);
    int all = getAll().size();
    Assertions.assertEquals(0, all);
  }

  @Test
  public void testSaveTwo() {
    matchRepo.save(new FootballMatch("a", "c"));
    matchRepo.save(new FootballMatch("a", "b"));
    int all = getAll().size();
    Assertions.assertEquals(2, all);
  }

  @Test
  public void update() {
    var match = new FootballMatch("a", "c");
    matchRepo.save(match);
    match.startMatch();
    match.updateMatchScore(1, 0);

    matchRepo.update(match);

    Assertions.assertEquals(1, getAll().size());
    getAll().stream()
        .findAny()
        .ifPresent(el -> {
          Assertions.assertEquals(1, el.getHomeTeamScore());
        });
  }

  @Test
  public void updateTwice() {
    var match = new FootballMatch("a", "c");
    matchRepo.save(match);
    match.startMatch();
    match.updateMatchScore(1, 0);

    matchRepo.update(match);

    match.updateMatchScore(2, 0);
    matchRepo.update(match);

    Assertions.assertEquals(1, getAll().size());
    matchRepo.filter(el -> el.equals(match), null).stream()
        .findAny()
        .ifPresent(el -> {
          Assertions.assertEquals(2, el.getHomeTeamScore());
        });
  }

  @Test
  public void updateAfterSecondSave() {
    var match = new FootballMatch("a", "c");
    matchRepo.save(match);
    match.startMatch();
    match.updateMatchScore(1, 0);

    matchRepo.update(match);

    match.updateMatchScore(2, 0);
    matchRepo.save(match);

    match.updateMatchScore(3, 0);
    matchRepo.update(match);

    Assertions.assertEquals(1, getAll().size());
    matchRepo.filter(el -> el.equals(match), null).stream()
        .findAny()
        .ifPresent(el -> {
          Assertions.assertEquals(3, el.getHomeTeamScore());
        });
  }

  @Test
  public void delete() {
    var match = new FootballMatch("a", "c");
    matchRepo.save(match);

    boolean deleted = matchRepo.delete(match);
    Assertions.assertTrue(deleted);
    Assertions.assertEquals(0, getAll().size());
  }

  @Test
  public void deleteThenUpdate() {
    var match = new FootballMatch("a", "c");
    matchRepo.save(match);

    boolean deleted = matchRepo.delete(match);
    Assertions.assertTrue(deleted);

    matchRepo.update(match);
    Assertions.assertEquals(0, getAll().size());
  }

  @Test
  public void deleteThenSave() {
    var match = new FootballMatch("a", "c");
    matchRepo.save(match);

    boolean deleted = matchRepo.delete(match);
    Assertions.assertTrue(deleted);

    matchRepo.save(match);
    Assertions.assertEquals(1, getAll().size());
  }

  @Test
  public void deleteNull() {
    var match = new FootballMatch("a", "c");
    matchRepo.save(match);

    boolean deleted = matchRepo.delete(null);
    Assertions.assertFalse(deleted);
    Assertions.assertEquals(1, getAll().size());
  }

  @Test
  public void deleteThenSaveThenUpdate() {
    var match = new FootballMatch("a", "c");
    matchRepo.save(match);

    boolean deleted = matchRepo.delete(match);
    Assertions.assertTrue(deleted);

    matchRepo.save(match);
    Assertions.assertEquals(1, getAll().size());
    Assertions.assertFalse(getAll().get(0).isInProgress());

    match.startMatch();
    matchRepo.update(match);
    Assertions.assertEquals(1, getAll().size());
    Assertions.assertTrue(getAll().get(0).isInProgress());
  }

  @Test
  public void saveThenUpdateThenDelete() {
    var match = new FootballMatch("a", "c");
    matchRepo.save(match);

    match.startMatch();
    matchRepo.update(match);

    boolean deleted = matchRepo.delete(match);
    Assertions.assertTrue(deleted);
    Assertions.assertEquals(0, getAll().size());
  }

  @Test
  public void filterDefaultSortDefault() {
    var match = new FootballMatch("a", "c");
    matchRepo.save(match);

    var match2 = new FootballMatch("b", "c");
    matchRepo.save(match2);

    List<Match> matchList = matchRepo.filter(null, null);
    Assertions.assertEquals(2, matchList.size());
    Assertions.assertEquals("a", matchList.get(0).getHomeTeam());
    Assertions.assertEquals("b", matchList.get(1).getHomeTeam());
  }

  @Test
  public void filterNameSortDefault() {
    var match = new FootballMatch("a", "c");
    matchRepo.save(match);

    var match2 = new FootballMatch("b", "c");
    matchRepo.save(match2);

    Predicate<Match> homeTeamA = el -> el.getHomeTeam().equals("a");
    List<Match> matchList = matchRepo.filter(homeTeamA, null);
    Assertions.assertEquals(1, matchList.size());
    Assertions.assertEquals("a", matchList.get(0).getHomeTeam());
  }

  @Test
  public void filterDefaultSortTotalScore() {
    var match = new FootballMatch("a", "c");
    match.startMatch();
    match.updateMatchScore(1, 1);
    matchRepo.save(match);

    var match2 = new FootballMatch("b", "c");
    match2.startMatch();
    match2.updateMatchScore(2, 1);
    matchRepo.save(match2);

    Comparator<Match> byTotalScoreDes = (el1, el2) -> el2.getTotalScore() - el1.getTotalScore();
    List<Match> matchList = matchRepo.filter(null, byTotalScoreDes);
    Assertions.assertEquals(2, matchList.size());
    Assertions.assertEquals("b", matchList.get(0).getHomeTeam());
  }

  @Test
  public void filterNameSortTotalScore() {
    var match = new FootballMatch("a", "c");
    match.startMatch();
    match.updateMatchScore(1, 1);
    match.finishMatch();
    matchRepo.save(match);

    var match2 = new FootballMatch("a", "d");
    match2.startMatch();
    match2.updateMatchScore(2, 1);
    matchRepo.save(match2);

    var match3 = new FootballMatch("b", "c");
    match3.startMatch();
    match3.updateMatchScore(1, 1);
    matchRepo.save(match3);

    Predicate<Match> homeTeamA = el -> el.getHomeTeam().equals("a");
    Comparator<Match> byTotalScoreDes = (el1, el2) ->
        (el2.getHomeTeamScore() + el2.getAwayTeamScore())
            - (el1.getHomeTeamScore() + el1.getAwayTeamScore());
    List<Match> matchList = matchRepo.filter(homeTeamA, byTotalScoreDes);
    Assertions.assertEquals(2, matchList.size());
    Assertions.assertEquals("a", matchList.get(0).getHomeTeam());
    Assertions.assertEquals(2, matchList.get(0).getHomeTeamScore());
  }


  private List<Match> getAll() {
    return matchRepo.filter(null, null);
  }
}
