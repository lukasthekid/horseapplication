package at.ac.tuwien.sepm.assignment.individual.unit.endpont;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.endpoint.HorseEndpoint;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

public abstract class HorseEndpointTestBase {

    @Autowired
    HorseEndpoint horseEndpoint;


    @Test
    @DisplayName("Read all horses from an empty List should throw ResponseStatusException")
    public void readAllHorsesFromEmptySystem_shouldThrowResponseStatusException() {

        //then
        assertThrows(ResponseStatusException.class,
            () -> horseEndpoint.findAllHorses());
    }

    @Test
    @DisplayName("saving a Horse should return Responseentity with Status 200")
    public void savingValidHorse() {

        //given
        HorseDto horse = TestData.getNewHorseDto("Hengst", LocalDate.parse("2000-01-02"), "male");

        //when
        ResponseEntity re = horseEndpoint.saveHorse(horse);
        //then
        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @DisplayName("deleting a non existing Horse should let the system untouched")
    public void deletingHorseThatDoesntExist() {

        //given
        HorseDto horse = TestData.getNewHorseDto("Gustav", LocalDate.parse("2000-01-02"), "male");
        horseEndpoint.saveHorse(horse);
        int size = horseEndpoint.findAllHorses().getBody().size();

        //when
        horseEndpoint.deleteHorseById(10L);
        int sizeNew = horseEndpoint.findAllHorses().getBody().size();

        //then
        assertThat(sizeNew).isEqualTo(size);


    }

    @Test
    @DisplayName("deleting an existing Horse should return Responseentity with Status 200 ")
    public void deletingValidHorse() {

        //given
        HorseDto horse = TestData.getNewHorseDto("Augustin", LocalDate.parse("2000-01-02"), "male");
        horseEndpoint.saveHorse(horse);

        //when
        ResponseEntity re = horseEndpoint.deleteHorseById(1L);

        //then
        assertThat(re.getStatusCode()).isEqualTo(HttpStatus.OK);


    }



}