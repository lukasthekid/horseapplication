package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.DuplicatesFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;

import java.util.List;

public interface HorseService {


    /**
     * Gets the sport with a given ID.
     *
     * @param id of the horse to find.
     * @return the horse with the specified id.
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if the horse could not be found in the system.
     */


    Horse getOneById(Long id) throws ServiceException;

    /**
     * Saves a horse inside the database.
     * @param horse of the horse to save.
     * @return the horse that is saved.
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws DuplicatesFoundException if the horse is already stored in database.
     */

    Horse saveHorse(Horse horse) throws ServiceException;

    /**
     * Gets all the horses in the system
     * @return a list of horses
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if no horses could be found.
     */

    List<Horse> finAllHorses() throws ServiceException;

    /**
     * Updates the attributes of a given horse
     * @param horse the new horse with the same id of the old one (overwrites the old one)
     * @return the new updated horse
     * @throws RuntimeException  if something goes wrong during data processing.
     */

    Horse updateHorse(Horse horse) throws ServiceException;

    /**
     * Deletes the specific horse from system
     * @param id of the horse that should be deleted
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if the horse could not be found.
     */

    void deleteHorseById(Long id) throws ServiceException;

    /**
     * Gets a List of all horses in the system that matches the attributes of the given horse.
     * @param horse attributes on which related horses should be found.
     * @return A list of horses.
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if no related horses could be found.
     */

    List<Horse> searchByAttributes(Horse horse) throws ServiceException;

    /**
     * Gets all horses that arent parents of other horses
     * @return A list of child horses
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if no children could be found.
     */

    List<Horse> finAllChildren() throws ServiceException;
}
