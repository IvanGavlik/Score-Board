package org.igavlik.scoreboard;

import org.igavlik.scoreboard.domain.match.Match;
import org.igavlik.scoreboard.domain.match.impl.FootballMatch;
import org.junit.jupiter.api.Test;

/**
 * Examples How to use this lib
 */
public class MainTest {

  // for now ideas
  @Test
  public void basic() {
    /**
     * Option A
     */
    Match match = new FootballMatch("a", "b");
    match.startMatch();
    match.updateMatchScore(1, 0);
    match.finishMatch();

    // TODO impl
    // ScoreBoard scoreBoard = new ScoreBoardImpl()
    // scoreBoard.display();

    // PROS: EASY TO USE 5 (1-5)
    // COINS: HARDER TO IMPLEMENT
    // QUESTION: HOW TO DECOUPLE MATCH ACTON FROM DB (THIS REQ MATCH TO HAVE DEPENDENCY ON OR  EVENT BASED SOLUTION)

    /**
     * Option B
     */
    match = new FootballMatch("a", "b");

    // TODO impl
    // MatchService service = new FootballMatchServiceImpl()
    // service.startMatch(match);
    // service.updateMatchScore(match ,1, 0);
    // service.finishMatch(match);

    // TODO impl
    // ScoreBoard scoreBoard = new ScoreBoardImpl()
    // scoreBoard.display();

    // PROS:  EASY maintenance AND IMPL  DECOUPLE MATCH  FROM DB (NOW WE HAVE SERVICE)
    // COINS: EASY TO USE:  5 (1-5)  NOW CLIENT HAS TO USE ADDITIONAL CLASS SERVICE
    // QUESTION WHERE TO KEEP BUSSINES LOGIC AND VALIDATION (ONLY IN SERVICE, MATCH IS THEN ONLY POJO)

  }

}
