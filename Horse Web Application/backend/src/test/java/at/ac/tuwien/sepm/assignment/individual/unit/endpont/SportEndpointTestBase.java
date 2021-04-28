package at.ac.tuwien.sepm.assignment.individual.unit.endpont;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.endpoint.SportEndpoint;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.SportDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

public abstract class SportEndpointTestBase {

    @Autowired
    SportEndpoint sportEndpoint;


    @Test
    @DisplayName("Read all sports from an empty List should throw ResponseStatusException")
    public void readAllSportsFromEmptySystem_shouldThrowResponseStatusException() {

        //then
        assertThrows(ResponseStatusException.class,
            () -> sportEndpoint.findAllSports());
    }

    @Test
    @DisplayName("saving a sport should return Responseentity with Status 200")
    public void savingValidSport() {

        //given
        SportDto sport = TestData.getNewSportDto("Polo");

        //when
        ResponseEntity re = sportEndpoint.saveSport(sport);
        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("deleting a non existing sport should let the system untouched")
    public void deletingSportThatDoesntExist() {

        //given
        SportDto sport = TestData.getNewSportDto("Foxhunting");
        sportEndpoint.saveSport(sport);
        int size = sportEndpoint.findAllSports().getBody().size();

        //when
        sportEndpoint.deleteSportById(10L);
        int sizeNew = sportEndpoint.findAllSports().getBody().size();

        //then
        assertThat(sizeNew).isEqualTo(size);


    }

    @Test
    @DisplayName("finding a sport by Id that doesnt exist should throe ResponseStatusException")
    public void findSportByNonExistingId_shouldThrowResponseStatusException() {


        //then
        assertThrows(ResponseStatusException.class,
            () -> sportEndpoint.getOneById(10L));


    }

}