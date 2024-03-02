package org.igavlik.scoreboard.domain.match.impl;

import org.igavlik.scoreboard.domain.match.Match;

public class FootballMatch implements Match {

  private String homeTeam;
  private String awayTeam;

  public FootballMatch(String ht, String at) {
    this.homeTeam = ht;
    this.awayTeam = at;
  }

  @Override
  public String getHomeTeam() {
    return homeTeam;
  }

  @Override
  public String getAwayTeam() {
    return awayTeam;
  }
}
