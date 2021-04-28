package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.*;
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
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse saveHorse(Horse horse) throws ServiceException {
        LOGGER.trace("saveHorse({})", horse);

        //validate Input
        try {
            Validator.validateNewHorse(horse);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        //check for duplicates
        try {
            List<Long> id = dao.getIdByHorse(horse, false);

            if (id.size() > 0) {
                throw new DuplicatesFoundException("Horse is already stored in database");
            }
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        //save Horse
        try {
            return dao.saveHorse(horse);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Horse> finAllHorses() throws ServiceException, NotFoundException {
        LOGGER.trace("findAllHorses()");
        try {
            return dao.findAllHorses();
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse updateHorse(Horse horse) throws ServiceException {
        LOGGER.trace("updateHorse({})", horse);

        //validate Input
        try {
            Validator.validateNewHorse(horse);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        }


        try {
            return dao.updateHorse(horse);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteHorseById(Long id) throws ServiceException, NotFoundException {
        LOGGER.trace("deleteHorseById({})", id);

        try {
            dao.deleteHorseById(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<Horse> searchByAttributes(Horse horse) throws ServiceException, NotFoundException {
        LOGGER.trace("searchByAttributes({})", horse);

        //validate Input
        try {
            Validator.validateGivenHorse(horse);
        } catch (ValidationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
        List<Long> ids;
        try {
            ids = dao.getIdByHorse(horse, true);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        List<Horse> hits = new LinkedList<>();
        if (ids.isEmpty())
            throw new NotFoundException("Could not find any horses with attributes of: " + horse.toString());
        for (int i = 0; i < ids.size(); i++) {
            Horse hit = getOneById(ids.get(i));
            hits.add(hit);
        }
        return hits;

    }

    @Override
    public List<Horse> finAllChildren() throws ServiceException, NotFoundException {
        LOGGER.trace("findAllChildren()");

        try {
            return dao.findAllChildren();
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }


}





