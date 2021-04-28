package at.ac.tuwien.sepm.assignment.individual.endpoint.mapper;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.SportDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HorseMapper {

    SportMapper sportMapper = new SportMapper();

    public HorseDto entityToDto(Horse horse) {
        if (horse == null)
            return null;
        return new HorseDto(horse.getId(), horse.getName(),horse.getDesc(),horse.getDob(),horse.getSex(),sportMapper.entityToDto(horse.getFavoriteSport()),entityToDto(horse.getDad()),entityToDto(horse.getMom()));

    }

    public List<HorseDto> entityListToDtoList(List<Horse> horses) {
        if (horses.isEmpty()){return null;}

        List<HorseDto> dtoList = new ArrayList<>();
        for (int i = 0; i < horses.size(); i++) {
            HorseDto dto = entityToDto(horses.get(i));
            dtoList.add(dto);
        }
        return dtoList;
    }

    public Horse dtoToEntity(HorseDto horse) {
        if (horse == null)
            return null;

        return new Horse(horse.getId(), horse.getName(),horse.getDesc(),horse.getDob(),horse.getSex(),sportMapper.dtoToEntity(horse.getFavoriteSport()), dtoToEntity(horse.getDad()), dtoToEntity(horse.getMom()));
    }
}
