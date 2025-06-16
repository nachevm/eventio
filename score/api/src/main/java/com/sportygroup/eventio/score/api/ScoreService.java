package com.sportygroup.eventio.score.api;

/**
 * Service abstraction for score-related operations.
 */
public interface ScoreService {

  /**
   * Retrieves the score data for a given identifier.
   *
   * @param id The identifier of the score.
   * @return The score data associated with the identifier.
   */
  ScoreData findById(String id);
}
