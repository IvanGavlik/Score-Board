package org.igavlik.scoreboard.data;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface Repo<T> {

  void save(T match);

  void update(T match);

  boolean delete(T match);

  /**
   * @param filter if null return all
   * @param sort   if null sort by default sorter
   * @return
   */
  List<T> filter(Predicate<T> filter, Comparator<T> sort);

}
