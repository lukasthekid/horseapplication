package at.ac.tuwien.sepm.assignment.individual.endpoint;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.SportDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.SportMapper;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.service.SportService;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(SportEndpoint.BASE_URL)
public class SportEndpoint {

    static final String BASE_URL = "/sports";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final SportService sportService;
    private final SportMapper sportMapper;

    @Autowired
    public SportEndpoint(SportService sportService, SportMapper sportMapper) {
        this.sportService = sportService;
        this.sportMapper = sportMapper;
    }

    @GetMapping(value = "/{id}")
    public SportDto getOneById(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/{}", id);
        try {
            return sportMapper.entityToDto(sportService.getOneById(id));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading sport", e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<SportDto>> findAllHorses(){
        List<Sport> sports = sportService.getAllSports();
        List<SportDto> sportsDto = sportMapper.entityListToDtoList(sports);
        return new ResponseEntity<>(sportsDto,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<SportDto> saveSport(@RequestBody SportDto sportDTO){
        Sport sport = sportMapper.dtoToEntity(sportDTO);


        return new ResponseEntity<>(sportMapper.entityToDto(sportService.saveSport(sport)), HttpStatus.OK);
    }
}
