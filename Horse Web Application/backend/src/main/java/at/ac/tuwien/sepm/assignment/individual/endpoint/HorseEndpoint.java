package at.ac.tuwien.sepm.assignment.individual.endpoint;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.util.List;

@RestController
@RequestMapping(path = HorseEndpoint.BASE_URL)
public class HorseEndpoint {
    static final String BASE_URL = "/horses";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HorseService horseService;
    private final HorseMapper horseMapper;

    @Autowired
    public HorseEndpoint(HorseService horseService, HorseMapper horseMapper) {
        this.horseService = horseService;
        this.horseMapper = horseMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<HorseDto>> findAllHorses() {
        LOGGER.info("GET " + BASE_URL + "/all");
        try {
            List<Horse> horses = horseService.finAllHorses();

            List<HorseDto> horsesDto = horseMapper.entityListToDtoList(horses);
            return new ResponseEntity<>(horsesDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading horses", e);
        }
    }

    @GetMapping("/children")
    public ResponseEntity<List<HorseDto>> findAllChildren() {
        LOGGER.info("GET " + BASE_URL + "/children");

        try {
            List<Horse> horses = horseService.finAllChildren();

            List<HorseDto> horsesDto = horseMapper.entityListToDtoList(horses);
            return new ResponseEntity<>(horsesDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading horses", e);
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<HorseDto> getOneById(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/{}", id);
        try {
            Horse horse = horseService.getOneById(id);
            return new ResponseEntity<>(horseMapper.entityToDto(horse), HttpStatus.OK);
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading horse", e);
        }
    }

    @PostMapping(value = "/find")
    public ResponseEntity<List<HorseDto>> searchByAttributes(@RequestBody HorseDto horseDTO) {
        LOGGER.info("POST " + BASE_URL + "/find {}", horseDTO);

        Horse horse = horseMapper.dtoToEntity(horseDTO);
        try {
            List<Horse> hits = horseService.searchByAttributes(horse);
            List<HorseDto> hitsDto = horseMapper.entityListToDtoList(hits);
            return new ResponseEntity<>(hitsDto, HttpStatus.OK);
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during searching Horse", e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<HorseDto> saveHorse(@RequestBody HorseDto horseDTO) {
        LOGGER.info("POST " + BASE_URL + "/add {}", horseDTO);
        try {
            Horse horse = horseMapper.dtoToEntity(horseDTO);


            return new ResponseEntity<>(horseMapper.entityToDto(horseService.saveHorse(horse)), HttpStatus.OK);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<HorseDto> updateHorse(@RequestBody HorseDto horseDTO) {
        LOGGER.info("PUT " + BASE_URL + "/update {}", horseDTO);
        try {
            Horse horse = horseMapper.dtoToEntity(horseDTO);
            Horse newHorse = horseService.updateHorse(horse);
            return new ResponseEntity<>(horseMapper.entityToDto(newHorse), HttpStatus.OK);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHorseById(@PathVariable("id") Long id) {
        LOGGER.info("DELETE " + BASE_URL + "/delete/{}", id);

        try {
            horseService.deleteHorseById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error during deleting horse", e);
        }
    }


}
