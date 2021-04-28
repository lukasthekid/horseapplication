package at.ac.tuwien.sepm.assignment.individual.base;


import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.SportDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;

import java.time.LocalDate;

public interface TestData {

    /**
     * URI Data
     */
    String BASE_URL = "http://localhost:";
    String HORSE_URL = "/horses";
    String SPORT_URL = "/sports";

    /**
     * Sport Data
     */
    static Sport getNewSport() {
        return new Sport("Sport");
    }

    static Sport getNewSport(String name) {
        return new Sport(name);
    }

    static Sport getNewSportWithId() {
        return new Sport(1L, "Sport");
    }

    static Horse getNewHorse(String name, LocalDate dob, String sex){return new Horse(name,dob,sex);
    }

    static Horse getNewHorseWithId( Long id, String name, LocalDate dob, String sex){
        Horse h = new Horse(name,dob,sex);
        h.setId(id);
        return h;
    }

    static SportDto getNewSportDto(String name){return new SportDto(name);
    }

    static HorseDto getNewHorseDto(String name, LocalDate dob, String sex){return new HorseDto(name,dob,sex);
    }


}
