package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.DuplicatesFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.SportDao;
import at.ac.tuwien.sepm.assignment.individual.service.SportService;
import at.ac.tuwien.sepm.assignment.individual.util.Validator;

import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SportServiceImpl implements SportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final SportDao dao;
    private final Validator validator;

    @Autowired
    public SportServiceImpl(SportDao sportDao, Validator validator) {
        this.dao = sportDao;
        this.validator = validator;
    }

    @Override
    public Sport getOneById(Long id) throws ServiceException, NotFoundException {
        LOGGER.trace("getOneById({})", id);
        try {
            return dao.getOneById(id);
        }catch(PersistenceException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public List<Sport> getAllSports() {
        return dao.getAllSports();
    }

    @Override
    public List<Long> getIdBySport(Sport sport) {
        return dao.getIdBySport(sport);
    }

    @Override
    public Sport saveSport(Sport sport) {

        List<Long> id = dao.getIdBySport(sport);
        if(id.size()>0){
            throw new DuplicatesFoundException("Sport is already stored in database");
        }
        try{
            return dao.saveSport(sport);
        } catch(PersistenceException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }


}
