package org.igavlik.scoreboard.util;

import java.util.List;
import org.igavlik.scoreboard.data.Repo;
import org.igavlik.scoreboard.domain.match.Match;
import org.igavlik.scoreboard.domain.match.data.MatchRepoInMemory;
import org.junit.jupiter.api.BeforeEach;

public class RepoTest {

  protected final Repo<Match> matchRepo = MatchRepoInMemory.INSTANCE;

  @BeforeEach
  public void init() {
    // hack to delete data between test cases because we have singleton
    getAll()
        .forEach(matchRepo::delete);
  }

  protected List<Match> getAll() {
    return matchRepo.filter(null, null);
  }

}
