package org.igavlik.scoreboard.domain.match;

import java.time.LocalDateTime;

public interface Match {

  String getHomeTeam();

  String getAwayTeam();

  int getHomeTeamScore();

  int getAwayTeamScore();

  boolean isInProgress();

  LocalDateTime getStartedAt();

  int getTotalScore();

  void startMatch();

  void updateMatchScore(int homeTeamSore, int awayTeamScore);

  void finishMatch();

  default boolean isStartedOrInProgress() {
    return this.getStartedAt() != null || this.isInProgress();
  }

  default boolean isStartedAndInProgress() {
    return this.getStartedAt() != null && this.isInProgress();
  }
}
