package org.igavlik.scoreboard.domain.match;

public interface Match {

  String getHomeTeam();

  String getAwayTeam();

  int getHomeTeamScore();

  int getAwayTeamScore();

  void startMatch();
}
