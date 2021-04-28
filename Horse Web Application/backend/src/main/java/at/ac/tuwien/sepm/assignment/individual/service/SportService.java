package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;

import java.util.List;

public interface SportService {


    /**
     * Gets the sport with a given ID.
     *
     * @param id of the sport to find.
     * @return the sport with the specified id.
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if the sport could not be found in the system.
     */
    Sport getOneById(Long id) throws ServiceException;

    /**
     * Gets all sports in System
     *
     * @return void
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if no Sports could not be found in the system.
     */

    List<Sport> getAllSports() throws ServiceException;

    /**
     * Gets a list of all IDs that matches the sport with the given attributes
     * @param sport Sport
     * @return List of Ids of the sports that matches the attributes given in Object sport
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if the id could not be found in the system.
     */

    List<Long> getIdBySport(Sport sport) throws ServiceException;

    /**
     * Saves a sport in the Database
     * @param sport Sport
     * @return the saved sport
     * @throws RuntimeException  if something goes wrong during data processing.
     */

    Sport saveSport(Sport sport) throws ServiceException;

    /**
     * Delete a sport
     * @param id of the specific sport that should be deleted
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if the specific sport could not be found.
     */

    void deleteSportById(Long id) throws ServiceException;
}
