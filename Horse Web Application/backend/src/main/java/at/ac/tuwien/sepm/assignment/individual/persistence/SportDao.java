package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;

import java.util.List;

public interface SportDao {

    /**
     * Get the sport with given ID.
     *
     * @param id of the sport to find.
     * @return the sport with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException   will be thrown if the sport could not be found in the database.
     */
    Sport getOneById(Long id);

    /**
     * Get all sports of database
     * @return a list of sports
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException   will be thrown if no sports could not be found in the database.
     */

    List<Sport> getAllSports();

    /**
     * Gets a list of Ids that matches the attributes of the given sport
     * @param sport of the sport to search for
     * @return a List of Long IDs
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException   will be thrown if the id could not be found in the database.
     */

    List<Long> getIdBySport(Sport sport);

    /**
     * Saves a sport in Databsae
     * @param sport of the Object to save
     * @return the saved sport
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws UpdateException if there was an error while saving to database.
     */

    Sport saveSport(Sport sport);

    /**
     * Delete a Sport with given ID
     * @param id of the sport to delete
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException if the sport could not be found in database.
     */

    void deleteSportById(Long id);
}
