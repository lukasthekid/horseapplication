package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface HorseService {


    /**
     * Gets the sport with a given ID.
     *
     * @param id of the sport to find.
     * @return the sport with the specified id.
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if the sport could not be found in the system.
     */


    Horse getOneById(Long id);

    Horse saveHorse(Horse horse);

    List<Horse> finAllHorses();

    Horse updateHorse(Horse horse);

    void deleteHorseById(Long id);

    List<Long> getIdByHorse(Horse horse);
}
