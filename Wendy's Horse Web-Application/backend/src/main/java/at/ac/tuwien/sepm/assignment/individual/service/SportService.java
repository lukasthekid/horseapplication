package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
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

    List<Sport> getAllSports();

    List<Long> getIdBySport(Sport sport);

    Sport saveSport(Sport sport);
}
