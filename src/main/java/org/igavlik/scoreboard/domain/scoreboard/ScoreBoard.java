package org.igavlik.scoreboard.domain.scoreboard;

import java.util.List;
import org.igavlik.scoreboard.domain.match.Match;

public interface ScoreBoard {
  
  List<Match> getMatchesInProgress();
}
