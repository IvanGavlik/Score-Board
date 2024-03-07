package org.igavlik.scoreboard.domain.scoreboard;

import java.util.List;
import org.igavlik.scoreboard.data.Repo;
import org.igavlik.scoreboard.domain.match.Match;
import org.igavlik.scoreboard.domain.match.data.MatchRepoInMemory;
import org.igavlik.scoreboard.domain.match.impl.FootballMatch;
import org.igavlik.scoreboard.domain.scoreboard.impl.FootballLiveScoreBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScoreBoardTest {

  private final Repo<Match> matchRepo = MatchRepoInMemory.INSTANCE;

  @BeforeEach
  public void init() {
    // hack to delete data between test cases because we have singleton
    getAll()
        .forEach(matchRepo::delete);
  }


  @Test
  public void filterInProgress() {
    oneInProgress();

    ScoreBoard scoreBoard = new FootballLiveScoreBoard(matchRepo);
    List<Match> matchList = scoreBoard.getMatchesInProgress();

    Assertions.assertNotNull(matchList);
    Assertions.assertEquals(1, matchList.size());
  }

  @Test
  public void filterNoInProgress() {
    noInProgress();

    ScoreBoard scoreBoard = new FootballLiveScoreBoard(matchRepo);
    List<Match> matchList = scoreBoard.getMatchesInProgress();

    Assertions.assertNotNull(matchList);
    Assertions.assertEquals(0, matchList.size());
  }

  @Test
  public void sortTotalScore() {
    totalScoresDiff();

    ScoreBoard scoreBoard = new FootballLiveScoreBoard(matchRepo);
    List<Match> matchList = scoreBoard.getMatchesInProgress();

    Match matchTotalScoreBigger = matchList.get(0);
    Match matchTotalScoreSmaller = matchList.get(1);
    Assertions.assertTrue(totalScore(matchTotalScoreBigger) > totalScore(matchTotalScoreSmaller));
  }

  @Test
  public void sortStarted() {
    startedDiff();

    ScoreBoard scoreBoard = new FootballLiveScoreBoard(matchRepo);
    List<Match> matchList = scoreBoard.getMatchesInProgress();

    Match matchTotalScoreBigger = matchList.get(0);
    Match matchTotalScoreSmaller = matchList.get(1);
    Assertions.assertTrue(
        matchTotalScoreBigger.getStartedAt().isAfter(matchTotalScoreSmaller.getStartedAt())
    );
  }

  // TODO refactor - duplicate
  private List<Match> getAll() {
    return matchRepo.filter(null, null);
  }

  private void oneInProgress() {
    Match inProgress = new FootballMatch("a", "b");
    inProgress.startMatch();
    matchRepo.save(inProgress);

    Match notInProgress = new FootballMatch("a", "d");
    matchRepo.save(notInProgress);
  }

  private void noInProgress() {
    Match noInProgress1 = new FootballMatch("a", "b");
    noInProgress1.startMatch();
    noInProgress1.finishMatch();
    matchRepo.save(noInProgress1);

    Match noInProgress2 = new FootballMatch("a", "d");
    matchRepo.save(noInProgress2);
  }

  private void startedDiff() {
    Match m1 = new FootballMatch("a", "b");
    m1.startMatch();
    m1.updateMatchScore(1, 1);
    matchRepo.save(m1);

    Match m2 = new FootballMatch("a", "d");
    m2.startMatch();
    m2.updateMatchScore(1, 1);
    matchRepo.save(m2);
  }

  private void totalScoresDiff() {
    Match m1 = new FootballMatch("a", "b");
    m1.startMatch();
    m1.updateMatchScore(2, 1);
    matchRepo.save(m1);

    Match m2 = new FootballMatch("a", "d");
    m2.startMatch();
    m2.updateMatchScore(2, 1);
    matchRepo.save(m2);
  }


  // TODO refactor - duplicate
  private int totalScore(Match match) {
    return match.getAwayTeamScore() * match.getHomeTeamScore();
  }


}
