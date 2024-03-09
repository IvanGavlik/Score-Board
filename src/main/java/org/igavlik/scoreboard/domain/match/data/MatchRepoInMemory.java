package org.igavlik.scoreboard.domain.match.data;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.igavlik.scoreboard.data.Repo;
import org.igavlik.scoreboard.domain.match.Match;

public final class MatchRepoInMemory implements Repo<Match> {

  public final static MatchRepoInMemory INSTANCE = new MatchRepoInMemory();

  public final static Comparator<Match> SORT_HOME_TEAM = Comparator.comparing(Match::getHomeTeam);


  // fast retrieval and update
  // no duplicates
  // does not accept null
  private final Set<Match> data = new HashSet<>();


  private MatchRepoInMemory() {
  }

  @Override
  public void save(Match match) {
    if (match == null) {
      return;
    }
    BiPredicate<Match, Match> findSameInProgress = Match::isTeamInTwoMatchesWhereFirstIsInProgress;
    Optional<Match> existAndInProgress = this.data.stream()
        .filter(el -> findSameInProgress.test(el, match))
        .findAny();

    if (existAndInProgress.isPresent()) {
      return;
    }
    this.data.add(match);
  }

  @Override
  public void update(Match match) {
    if (match == null) {
      return;
    }
    if (this.delete(match)) {
      this.save(match);
    }
  }

  @Override
  public boolean delete(Match match) {
    return this.data.remove(match);
  }

  /**
   * @param filter if null return all
   * @param sort   if null sort by default sorter MatchRepoInMemory#SORT_HOME_TEAM
   * @return
   */
  @Override
  public List<Match> filter(Predicate<Match> filter, Comparator<Match> sort) {
    return this.data.stream()
        .filter(el -> {
              if (filter == null) {
                return true;
              }
              return filter.test(el);
            }
        )
        .sorted((el1, el2) -> {
          if (sort == null) {
            return SORT_HOME_TEAM.compare(el1, el2);
          }
          return sort.compare(el1, el2);
        })
        .collect(Collectors.toList());
  }
}
