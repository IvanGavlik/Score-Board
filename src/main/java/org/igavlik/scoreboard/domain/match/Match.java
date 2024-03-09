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

  /**
   * Order of params is important
   *
   * @param match1 match that was created/started before match2
   * @param match2 match that was created/started after match1
   * @return
   */
  static boolean isTeamInTwoMatchesWhereOneIsInProgress(Match match1, Match match2) {
    final String homeTeamM1 = match1.getHomeTeam();
    final String awayTeamM1 = match1.getAwayTeam();
    final String homeTeamM2 = match2.getHomeTeam();
    final String awayTeamM2 = match2.getAwayTeam();
    return isTeamInTwoMatches(homeTeamM1, awayTeamM1, homeTeamM2, awayTeamM2)
        && match1.isInProgress();
  }

  private static boolean isTeamInTwoMatches(String homeTeamM1, String awayTeamM1, String homeTeamM2,
      String awayTeamM2) {
    return (homeTeamM1.equals(homeTeamM2) || homeTeamM1.equals(awayTeamM2)) ||
        (awayTeamM1.equals(homeTeamM2) || awayTeamM1.equals(awayTeamM2));
  }

}
