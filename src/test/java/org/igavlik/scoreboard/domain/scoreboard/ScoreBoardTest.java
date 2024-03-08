package org.igavlik.scoreboard.domain.scoreboard;

import java.util.List;
import org.igavlik.scoreboard.domain.match.Match;
import org.igavlik.scoreboard.domain.match.impl.FootballMatch;
import org.igavlik.scoreboard.domain.scoreboard.impl.FootballLiveScoreBoard;
import org.igavlik.scoreboard.util.RepoTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ScoreBoardTest extends RepoTest {

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
    Assertions.assertTrue(
        matchTotalScoreBigger.getTotalScore() > matchTotalScoreSmaller.getTotalScore()
    );
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

  @Test
  public void sortScoresDate() {
    totalScoresDiff();
    startedDiff();

    ScoreBoard scoreBoard = new FootballLiveScoreBoard(matchRepo);
    List<Match> matchList = scoreBoard.getMatchesInProgress();

    Match matchTotalScoreBigger = matchList.get(0);
    Match matchTotalScoreSmaller = matchList.get(1);
    Match sameScoreLatterStarted = matchList.get(2);
    Match sameScoreEarlierStarted = matchList.get(3);

    Assertions.assertTrue(
        matchTotalScoreBigger.getTotalScore() > matchTotalScoreSmaller.getTotalScore()
    );
    Assertions.assertTrue(
        matchTotalScoreSmaller.getTotalScore() > sameScoreLatterStarted.getTotalScore()
    );
    Assertions.assertEquals(sameScoreLatterStarted.getTotalScore(),
        sameScoreEarlierStarted.getTotalScore());

    Assertions.assertTrue(
        sameScoreLatterStarted.getStartedAt().isAfter(sameScoreEarlierStarted.getStartedAt())
    );
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
    Match m1 = new FootballMatch("a", "i");
    m1.startMatch();
    m1.updateMatchScore(2, 1);
    matchRepo.save(m1);

    Match m2 = new FootballMatch("a", "j");
    m2.startMatch();
    m2.updateMatchScore(4, 0);
    matchRepo.save(m2);
  }
}
