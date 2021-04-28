package at.ac.tuwien.sepm.assignment.individual.unit.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public abstract class HorseServiceTestBase {

    @Autowired
    HorseService horseService;

    @Test
    @DisplayName("Save a horse with digits in its name should throw ServiceException")
    public void savingHorseWithDigitsInName_shouldThrowServiceException() {
        //given
        Horse horse = new Horse();
        horse.setName("P3t3R");horse.setDob(LocalDate.parse("2010-01-01"));horse.setSex("male");
        //then
        assertThrows(ServiceException.class,
            () -> horseService.saveHorse(horse));
    }

    @Test
    @DisplayName("Finding all Horses should return a List of size 2 ")
    public void canFindAllHorses() {

        //given
        LocalDate dob = LocalDate.parse("2000-01-01");
        Horse horse = TestData.getNewHorse("Horst", dob, "male");
        Horse horse2 = TestData.getNewHorse("Peter", dob, "male");
        horseService.saveHorse(horse);
        horseService.saveHorse(horse2);
        int size = horseService.finAllHorses().size();
        //then
        assertThat(size).isEqualTo(2);
    }

    @Test
    @DisplayName("searching for a horse that doesnt exist should throw NotFoundException")
    public void searchForHorseThatDoesntExist_shouldThrowNotFoundException() {

        //given
        Horse horse = new Horse();
        horse.setName("Kevin");horse.setDob(LocalDate.parse("2010-01-01"));horse.setSex("male");
        //then
        assertThrows(NotFoundException.class,
            () -> horseService.searchByAttributes(horse));

    }

    @Test
    @DisplayName("searching for a horse.name should a list of all Horses with this name")
    public void searchForHorseName() {

        //given
        Horse horse = TestData.getNewHorse("Horst", LocalDate.parse("2000-01-01"),"male");
        horse.setId(1L);
        horseService.saveHorse(horse);

        //when
        Horse search = new Horse();
        search.setName("Horst");


        //then
        assertThat(horseService.searchByAttributes(search).size()).isGreaterThan(0);


    }

}