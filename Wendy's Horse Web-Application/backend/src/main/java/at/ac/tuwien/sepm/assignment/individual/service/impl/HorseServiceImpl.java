package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.DuplicatesFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.SportDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.service.SportService;
import at.ac.tuwien.sepm.assignment.individual.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.DuplicatesPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.DuplicateFormatFlagsException;
import java.util.LinkedList;
import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HorseDao dao;
    private final Validator validator;


    @Autowired
    public HorseServiceImpl(HorseDao horseDao, Validator validator) {
        this.dao = horseDao;
        this.validator = validator;

    }



    @Override
    public Horse getOneById(Long id) throws ServiceException, NotFoundException {
        LOGGER.trace("getOneById({})", id);
        try {
            return dao.getOneById(id);
        }catch(PersistenceException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public Horse saveHorse(Horse horse) {

       //check for duplicates
        List<Long> id = dao.getIdByHorse(horse);
        if(id.size()>0){
            throw new DuplicatesFoundException("Horse is already stored in database");
        }

        //save Horse
        try{
            return dao.saveHorse(horse);
        } catch(PersistenceException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public List<Horse> finAllHorses() {
        return dao.findAllHorses();
    }

    @Override
    public Horse updateHorse(Horse horse) {
        return dao.updateHorse(horse);
    }

    @Override
    public void deleteHorseById(Long id) {
            dao.deleteHorseById(id);
    }

    @Override
    public List<Long> getIdByHorse(Horse horse) {
        List<Long> ids = dao.getIdByHorse(horse);
        if (ids.isEmpty()) throw new NotFoundException("Could not find id with horse: " + horse.toString());
        return ids;

    }




}
