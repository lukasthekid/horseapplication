package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;

import java.util.List;

public interface HorseDao {


    /**
     * Saves a horse to database
     * @param horse to save in Database
     * @return the saved horse
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws UpdateException if an error occurs while saving the horse to database.
     */

    Horse saveHorse(Horse horse);

    /**
     * Get the horse with a given ID
     * @param id of the horse to find
     * @return the found horse.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException if no horse could be read out from database.
     */

    Horse getOneById(Long id);

    /**
     * Gets all horses
     * @return a list of all horses in the database.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException if no horses could be read out from database.
     */

    List<Horse> findAllHorses();

    /**
     *
     * Update a horse with a given horse to overwrite
     * @param horse the one to overwrite the ID stays the same to find the peupdated horse.
     * @return the new overwrited horse.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws UpdateException if the horse could not be updated in database.
     */

    Horse updateHorse(Horse horse);

    /**
     * Delete a Horse by given ID
     *
     * @param id of the horse to delete
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException if the deleted horse could not be found.
     */

    void deleteHorseById(Long id);

    /**
     * Gets a list of IDs that matches the attributes of the given horse
     * @param horse to search related ones
     * @return a list of IDs
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException if no IDs could be found.
     */

    List<Long> getIdByHorse(Horse horse, boolean search);

    /**
     * Gets all child horses
     * @return a list of all horses from the database.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException if no child horses could be read out from database.
     */

    List<Horse> findAllChildren();
}
