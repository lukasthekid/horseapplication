package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.sql.SQLException;
import java.util.List;

public interface HorseDao {

    Horse saveHorse(Horse horse);

    Horse getOneById(Long id);

    List<Horse> findAllHorses();

    Horse updateHorse(Horse horse);

    void deleteHorseById(Long id);

    List<Long> getIdByHorse(Horse horse);
}
