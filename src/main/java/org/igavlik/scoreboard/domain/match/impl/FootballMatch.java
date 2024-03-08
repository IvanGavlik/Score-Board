package org.igavlik.scoreboard.domain.match.impl;

import java.time.LocalDateTime;
import java.util.Objects;
import org.igavlik.scoreboard.domain.match.Match;

public class FootballMatch implements Match {

  private String homeTeam;
  private String awayTeam;

  private int homeTeamScore;

  private int awayTeamScore;

  private boolean inProgress;

  private LocalDateTime startedAt;

  public FootballMatch(String ht, String at) {
    this.homeTeam = ht;
    this.awayTeam = at;
  }

  @Override
  public void startMatch() {
    if (isStartedOrInProgress()) {
      return;
    }
    this.homeTeamScore = 0;
    this.awayTeamScore = 0;
    this.inProgress = true;
    this.startedAt = LocalDateTime.now();
  }

  @Override
  public void updateMatchScore(int homeTeamSore, int awayTeamScore) {
    if (!isStartedAndInProgress()) {
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
  public void finishMatch() {
    if (isStartedOrInProgress()) {
      this.inProgress = false;
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

  @Override
  public LocalDateTime getStartedAt() {
    return this.startedAt;
  }

  @Override
  public int getTotalScore() {
    return this.homeTeamScore + this.awayTeamScore;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FootballMatch that = (FootballMatch) o;
    return Objects.equals(homeTeam, that.homeTeam) && Objects.equals(awayTeam, that.awayTeam);
  }

  @Override
  public int hashCode() {
    return Objects.hash(homeTeam, awayTeam);
  }
}
