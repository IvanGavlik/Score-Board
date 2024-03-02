package org.igavlik.scoreboard.domain.match.impl;

import org.igavlik.scoreboard.domain.match.Match;

public class FootballMatch implements Match {

  private String homeTeam;
  private String awayTeam;

  private int homeTeamScore;

  private int awayTeamScore;

  private boolean inProgress;

  public FootballMatch(String ht, String at) {
    this.homeTeam = ht;
    this.awayTeam = at;
  }

  @Override
  public void startMatch() {
    this.homeTeamScore = 0;
    this.awayTeamScore = 0;
    this.inProgress = true;
  }

  @Override
  public void updateMatchScore(int homeTeamSore, int awayTeamScore) {
    if (!this.isInProgress()) {
      return;
    }

    if (homeTeamSore > this.homeTeamScore) {
      this.homeTeamScore = homeTeamSore;
    }

    if (awayTeamScore > this.awayTeamScore) {
      this.awayTeamScore = awayTeamScore;
    }
  }

  @Override
  public String getHomeTeam() {
    return homeTeam;
  }

  @Override
  public String getAwayTeam() {
    return awayTeam;
  }

  @Override
  public int getHomeTeamScore() {
    return homeTeamScore;
  }

  @Override
  public int getAwayTeamScore() {
    return awayTeamScore;
  }

  @Override
  public boolean isInProgress() {
    return this.inProgress;
  }
}
