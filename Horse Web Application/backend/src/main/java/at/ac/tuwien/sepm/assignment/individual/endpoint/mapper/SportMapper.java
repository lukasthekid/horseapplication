package at.ac.tuwien.sepm.assignment.individual.endpoint.mapper;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.SportDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SportMapper {

    public SportDto entityToDto(Sport sport) {
        if (sport == null)
            return null;
        return new SportDto(sport.getId(), sport.getName(), sport.getDesc());
    }

    public List<SportDto> entityListToDtoList(List<Sport> sports) {
        if (sports.isEmpty()){return null;}

        List<SportDto> dtoList = new ArrayList<>();
        for (int i = 0; i < sports.size(); i++) {
            SportDto dto = entityToDto(sports.get(i));
            dtoList.add(dto);
        }
        return dtoList;

    }

    public Sport dtoToEntity(SportDto sport) {
        if (sport == null)
            return null;
        return new Sport(sport.getId(), sport.getName(), sport.getDesc());
    }
}
