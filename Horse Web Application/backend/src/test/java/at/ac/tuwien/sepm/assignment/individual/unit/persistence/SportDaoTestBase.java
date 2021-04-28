package at.ac.tuwien.sepm.assignment.individual.unit.persistence;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.SportDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SportDaoTestBase {

    @Autowired
    SportDao sportDao;

    @Test
    @DisplayName("Finding sport by non-existing ID should throw NotFoundException")
    public void findingSportById_nonExisting_shouldThrowNotFoundException() {
        assertThrows(NotFoundException.class,
            () -> sportDao.getOneById(100L));
    }

    @Test
    @DisplayName("Finding sportId by a given sport name should return 6")
    public void findingSportIdByPassingSportsName() {

        //given
        Sport sport = TestData.getNewSport("Polo");
        sportDao.saveSport(sport);

        //when
        Long sportId = sportDao.getIdBySport(sport).get(0);
        //then
        assertThat(sportId).isEqualTo(5L);


    }
    @Test
    @DisplayName("Saving a valid Sport should return the valid sport")
    public void savingValidSport() {

        //given
        Sport sport = TestData.getNewSport("Foxhunting");


        //when
        Sport sportSaved = sportDao.saveSport(sport);
        //then
        assertThat(sport).isEqualTo(sportSaved);


    }

    @Test
    @DisplayName("deleting sport by non existing ID should not change the sportsList")
    public void deletingSportWithNonExistingID_shouldThrowNotFoundException() {

        //given
        Long deleteId = 10L;
        Sport sport = TestData.getNewSport("Vaulting");
        sportDao.saveSport(sport);
        int number = sportDao.getAllSports().size();
        //when
        sportDao.deleteSportById(deleteId);
        int newNumber = sportDao.getAllSports().size();
        //then
        assertThat(newNumber).isEqualTo(number);
    }

}
