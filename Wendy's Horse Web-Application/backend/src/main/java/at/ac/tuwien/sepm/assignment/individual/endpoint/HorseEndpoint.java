package at.ac.tuwien.sepm.assignment.individual.endpoint;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
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
    public ResponseEntity<List<HorseDto>> findAllHorses(){
        List<Horse> horses = horseService.finAllHorses();
        System.out.println(horses.toString());
        List<HorseDto> horsesDto = horseMapper.entityListToDtoList(horses);
        return new ResponseEntity<>(horsesDto,HttpStatus.OK);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<HorseDto> getOneById(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/{}", id);
        try {
            Horse horse = horseService.getOneById(id);
            return new ResponseEntity<>(horseMapper.entityToDto(horse),HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading sport", e);
        }
    }

    @GetMapping(value = "/find")
    public ResponseEntity<List<Long>> getOneById(@RequestBody HorseDto horseDTO) {
        Horse horse = horseMapper.dtoToEntity(horseDTO);
        LOGGER.info("GET " + BASE_URL + "/find{}", horse);
        try {
            List<Long> ids = horseService.getIdByHorse(horse);
            return new ResponseEntity<>(ids,HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading Horse", e);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<HorseDto> saveHorse(@RequestBody HorseDto horseDTO){
        Horse horse = horseMapper.dtoToEntity(horseDTO);


        return new ResponseEntity<>(horseMapper.entityToDto(horseService.saveHorse(horse)), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<HorseDto> updateHorse(@RequestBody HorseDto horseDTO){
        Horse horse = horseMapper.dtoToEntity(horseDTO);
        Horse newHorse = horseService.updateHorse(horse);
        return new ResponseEntity<>(horseMapper.entityToDto(newHorse),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHorseById(@PathVariable("id") Long id){
        horseService.deleteHorseById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
