package org.igavlik.scoreboard.domain.scoreboard.impl;

import java.util.Comparator;
import java.util.List;
import org.igavlik.scoreboard.data.Repo;
import org.igavlik.scoreboard.domain.match.Match;
import org.igavlik.scoreboard.domain.scoreboard.ScoreBoard;

public class FootballLiveScoreBoard implements ScoreBoard {

  private Repo<Match> matchRepo;

  public FootballLiveScoreBoard(Repo<Match> matchRepo) {
    this.matchRepo = matchRepo;
  }


  @Override
  public List<Match> getMatchesInProgress() {
    final int same = 0;
    Comparator<Match> byTotalScoreDescCreatedDesc = (Match m1, Match m2) -> {
      int compare = Integer.compare(totalScore(m1), totalScore(m2));
      if (compare == same) {
        return m2.getStartedAt().compareTo(m1.getStartedAt());
      }
      return compare;
    };
    return this.matchRepo.filter(Match::isInProgress, byTotalScoreDescCreatedDesc);
  }

  private int totalScore(Match match) {
    return match.getHomeTeamScore() + match.getAwayTeamScore();
  }
}
