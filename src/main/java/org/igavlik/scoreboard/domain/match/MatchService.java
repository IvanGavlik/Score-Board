package org.igavlik.scoreboard.domain.match;

public interface MatchService {

  void startMatch(Match match);

  void updateMatchScore(Match match, int homeTeamSore, int awayTeamScore);

  void finishMatch(Match match);

}
