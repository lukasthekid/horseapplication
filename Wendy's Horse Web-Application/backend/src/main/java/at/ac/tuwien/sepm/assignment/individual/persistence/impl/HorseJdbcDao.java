package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.SportDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.service.SportService;
import at.ac.tuwien.sepm.assignment.individual.service.impl.SportServiceImpl;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HorseJdbcDao implements HorseDao {

    private static final String TABLE_NAME = "Horse";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HorseJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    private Horse mapRow(ResultSet resultSet, int i) throws SQLException {
        final Horse horse = new Horse();
        final SportService sport = null;

        horse.setId(resultSet.getLong("id"));
        horse.setName(resultSet.getString("name"));
        horse.setDesc(resultSet.getString("desc"));
        horse.setDob(LocalDate.parse(resultSet.getString("dob")));
        horse.setSex(resultSet.getString("sex"));
        horse.setFavoriteSport(new Sport(resultSet.getLong("fav_sport")));

        //recursion check
        Long dadId = resultSet.getLong("dad_id");
        Long momId = resultSet.getLong("mom_id");
        if (dadId!=0 ){
            horse.setDad(getOneById(dadId));
        }
        if(momId!=0){
            horse.setMom(getOneById(momId));
        }



        return horse;
    }


    @Override
    public Horse saveHorse(Horse horse) {
        LOGGER.trace("saveHorse({})", horse);

        final String sql = "INSERT INTO " + TABLE_NAME + " (name, desc, dob, sex,fav_sport, dad_id, mom_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        final String sqlTest = "INSERT INTO " + TABLE_NAME + " (name, desc, dob, sex) VALUES (?, ?, ?,?)";

        //get Sport id
        Long id = getSportId(horse.getFavoriteSport());
        //get Dad id
        Long dadId = getParentId(horse.getDad());
        //get Mom id
        Long momId = getParentId(horse.getMom());

        jdbcTemplate.update(sql,horse.getName(),horse.getDesc(),horse.getDob(),horse.getSex(), id,
            dadId, momId);




        return horse;
    }

    @Override
    public Horse getOneById(Long id) {
        LOGGER.trace("getOneById({})", id);
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        List<Horse> horses = null;
        try {
            horses = jdbcTemplate.query(sql, this::mapRow, id);
        } catch (NotFoundException e){
            throw new PersistenceException("Could not read horses with id" + id, e);
        }
        if (horses.isEmpty()) throw new NotFoundException("Could not find horse with id " + id);

        return horses.get(0);
    }

    @Override
    public List<Horse> findAllHorses() {
        LOGGER.trace("findAllHorses()");
        final String sql = "SELECT * FROM " + TABLE_NAME;
        List<Horse> horses = null;

        try {
            horses = jdbcTemplate.query(sql, this::mapRow);
        } catch (NotFoundException e){
            throw new PersistenceException("Could not read any horses" );
        }
        if (horses.isEmpty()) throw new NotFoundException("Could not find any horses");

        return horses;

    }

    @Override
    public Horse updateHorse(Horse horse) {
        LOGGER.trace("updateHorse({})", horse);
        final String sql = "UPDATE " + TABLE_NAME + " SET name=?, desc=?, dob=?, sex=?, fav_sport=?, dad_id=?, mom_id=? WHERE id=?";


        //get Sport id
        Long id = getSportId(horse.getFavoriteSport());
        //get Dad id
        Long dadId = getParentId(horse.getDad());
        //get Mom id
        Long momId = getParentId(horse.getMom());

        jdbcTemplate.update(sql,horse.getName(),horse.getDesc(),horse.getDob(),horse.getSex(), id,
            dadId, momId, horse.getId());

        return horse;
    }

    @Override
    public void deleteHorseById(Long id) {
        LOGGER.trace("deleteHorseById({})", id);
        final String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
        final String sqlDad = "UPDATE " + TABLE_NAME + " SET dad_id=? WHERE dad_id=?";
        final String sqlMom = "UPDATE " + TABLE_NAME + " SET mom_id=? WHERE mom_id=?";

        try {

            jdbcTemplate.update(sqlDad, null, id);
            jdbcTemplate.update(sqlMom, null, id);
            jdbcTemplate.update(sql, id);
        } catch (NotFoundException e) {
            throw new PersistenceException("Nothing to delete");
        }
    }

    @Override
    public List<Long> getIdByHorse(Horse horse) {
        LOGGER.trace("getIdByHorse({})", horse);
        StringBuilder sql = new StringBuilder();
        List<Object> arguments = new ArrayList<>();
        sql.append("SELECT id FROM " + TABLE_NAME + " WHERE ");

        if(horse.getName()!=null){sql.append("name=? AND "); arguments.add(horse.getName());}
        if(horse.getDesc()!=null){sql.append("desc=? AND "); arguments.add(horse.getDesc());}
        if(horse.getDob()!=null){sql.append("dob=? AND "); arguments.add(horse.getDob());}
        if(horse.getSex()!=null){sql.append("sex=? AND "); arguments.add(horse.getSex());}


        //if(horse.getFavoriteSport()!=null){sql.append("fav_sport=? AND "); arguments.add(horse.getFavoriteSport().getName());}
        sql.delete(sql.length()-4,sql.length()-1);
        final String sqlStat = sql.toString();

        List<Long> ids = null;

        try {
            ids = jdbcTemplate.query(sqlStat, this::mapRowId, arguments.toArray());
        } catch (NotFoundException e){
            throw new PersistenceException("Could not read id with horse: " + horse.toString(), e);
        }


        return ids;


    }

    private Long mapRowId(ResultSet resultSet, int i) throws SQLException {
        Long id;
        id = resultSet.getLong("id");
        return id;
    }

    private Long getSportId(Sport sport){
        Long id = null;
        if(sport!=null){
            SportDao sportDao = new SportJdbcDao(jdbcTemplate);

                List<Long> sportId = sportDao.getIdBySport(sport);
                if (sportId.isEmpty()) throw new NotFoundException("Sport must be initialized before you store the horse");
                id = sportId.get(0);
        }
        return id;
    }

    private Long getParentId(Horse parent){
        Long id = null;
        if(parent!=null) {

                List<Long> parentId = getIdByHorse(parent);
                if (parentId.isEmpty()) throw new NotFoundException("Parents must be initialized before you store the horse");
                id = parentId.get(0);

        }

        return id;

    }
}
