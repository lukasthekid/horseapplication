package at.ac.tuwien.sepm.assignment.individual.unit.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.*;
import at.ac.tuwien.sepm.assignment.individual.service.SportService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public abstract class SportServiceTestBase {

    @Autowired
    SportService sportService;

    @Test
    @DisplayName("Save a sport with digits in its name should throw ServiceException")
    public void savingSportWithDigitsInName_shouldThrowServiceException() {
        //given
        Sport sport = new Sport("P0l0");
        //then
        assertThrows(ServiceException.class,
            () -> sportService.saveSport(sport));
    }

    @Test
    @DisplayName("Save a Sport that already exists should throw DuplicatesFoundException")
    public void SaveSportThatAlreadyExists_shouldThrowDuplicatesFoundException() {

        //given
        Sport sport = TestData.getNewSport("Dressage");

        //when
        sportService.saveSport(sport);

        //then
        assertThrows(DuplicatesFoundException.class,
            () -> sportService.saveSport(sport));
    }

    @Test
    @DisplayName("Delete a Sport should change the list Size")
    public void deleteSportFromList() {

        //given
        Sport sport1 = TestData.getNewSport("Polo");
        sportService.saveSport(sport1);
        Sport sport2 = TestData.getNewSport("Foxhunting");
        sportService.saveSport(sport2);

        Long PoloId = sportService.getIdBySport(sport1).get(0);
        sport1.setId(PoloId);
        sportService.deleteSportById(PoloId);

        //then
        assertThat(sportService.getAllSports().contains(sport1)).isFalse();
    }

    @Test
    @DisplayName("save a valid sport should return the sport")
    public void saveValidSport() {

        //given
        Sport sport1 = TestData.getNewSport("Vaulting");


        //when
        Sport sport2 = sportService.saveSport(sport1);

        //then
        assertThat(sport2).isEqualTo(sport1);
    }



}