package org.igavlik.scoreboard.domain.match.impl;

import java.util.Optional;
import java.util.function.Predicate;
import org.igavlik.scoreboard.data.Repo;
import org.igavlik.scoreboard.domain.match.Match;
import org.igavlik.scoreboard.domain.match.MatchService;

public class FootballMatchService implements MatchService {

  private Repo<Match> matchRepo;

  public FootballMatchService(Repo<Match> matchRepo) {
    this.matchRepo = matchRepo;
  }

  @Override
  public void startMatch(Match match) {
    if (match == null) {
      return;
    }
    Predicate<Match> findSameInProgress = el -> el.equals(match) && el.isInProgress();
    Optional<Match> existAndInProgress = matchRepo
        .filter(findSameInProgress, null)
        .stream()
        .findAny();

    if (existAndInProgress.isPresent()) {
      return;
    }

    match.startMatch();
    matchRepo.save(match);
  }

  @Override
  public void updateMatchScore(Match match, int homeTeamSore, int awayTeamScore) {
    if (match == null) {
      return;
    }
    match.updateMatchScore(homeTeamSore, awayTeamScore);
    matchRepo.update(match);
  }

  @Override
  public void finishMatch(Match match) {
    if (match == null) {
      return;
    }
    match.finishMatch();
    matchRepo.update(match);
  }
}
