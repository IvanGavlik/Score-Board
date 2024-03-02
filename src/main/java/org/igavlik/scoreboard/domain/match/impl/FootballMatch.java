package org.igavlik.scoreboard.domain.match.impl;

import org.igavlik.scoreboard.domain.match.Match;

public class FootballMatch implements Match {

  private String homeTeam;
  private String awayTeam;

  private int homeTeamScore;

  private int awayTeamScore;

  public FootballMatch(String ht, String at) {
    this.homeTeam = ht;
    this.awayTeam = at;
  }

  @Override
  public void startMatch() {
    this.homeTeamScore = 0;
    this.awayTeamScore = 0;
  }

  @Override
  public void updateScore(int ht, int at) {
    this.homeTeamScore = ht;
    this.awayTeamScore = at;
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
}
