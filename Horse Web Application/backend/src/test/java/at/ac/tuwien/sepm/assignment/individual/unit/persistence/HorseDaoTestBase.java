package at.ac.tuwien.sepm.assignment.individual.unit.persistence;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public abstract class HorseDaoTestBase {

    @Autowired
    HorseDao horseDao;

    @Test
    @DisplayName("Finding horse by non-existing ID should throw NotFoundException")
    public void findingHorseById_nonExisting_shouldThrowNotFoundException() {
        assertThrows(NotFoundException.class,
            () -> horseDao.getOneById(100L));
    }

    @Test
    @DisplayName("Finding horseId by a given horse should return 3")
    public void findingHorseIdByPassingHorse() {


        //given
        LocalDate dob = LocalDate.parse("2000-01-01");
        Horse horse = TestData.getNewHorse("Mark", dob, "male");
        Horse horse2 = TestData.getNewHorse("Gustav", dob, "male");
        horseDao.saveHorse(horse);
        horseDao.saveHorse(horse2);

        //when
        Long horseId = horseDao.getIdByHorse(horse, false).get(0);
        //then
        assertThat(horseId).isEqualTo(4L);


    }

    @Test
    @DisplayName("saving the child as a parent of its parent should throw PersistenceException")
    public void savingChildAsParentOfItsParent_shouldThrowPersistenceException() {

        //given
        LocalDate dob = LocalDate.parse("2000-01-01");
        Horse amadee = TestData.getNewHorse( "Amadee", dob, "male");
        Horse peter = TestData.getNewHorse( "Peter", dob, "male");
        horseDao.saveHorse(peter);
        horseDao.saveHorse(amadee);
        Long peterId = horseDao.getIdByHorse(peter, false).get(0);
        peter.setId(peterId);
        Long amadeeId = horseDao.getIdByHorse(amadee, false).get(0);
        amadee.setId(amadeeId);
        amadee.setDad(peter);
        horseDao.updateHorse(amadee);

        //when
        peter.setDad(amadee);

        //then
        assertThrows(PersistenceException.class,
            () -> horseDao.updateHorse(peter));

    }

    @Test
    @DisplayName("updating a horse with a non existing favorite Sport should throw NotFoundException ")
    public void updatingHorseWithNonExistingSport_shouldThrowNotFoundException() {

        //given
        LocalDate dob = LocalDate.parse("2000-01-01");
        Horse h = TestData.getNewHorseWithId(1L,"Horst", dob, "male");
        ;
        Sport s = new Sport("nonexisting");
        h.setFavoriteSport(s);

        //then
        assertThrows(NotFoundException.class,
            () -> horseDao.updateHorse(h));

    }

}
