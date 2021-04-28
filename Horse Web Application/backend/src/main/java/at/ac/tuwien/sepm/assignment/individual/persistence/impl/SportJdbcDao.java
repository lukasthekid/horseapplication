package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.SportDao;

import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SportJdbcDao implements SportDao {

    private static final String TABLE_NAME = "Sport";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SportJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Sport getOneById(Long id) {
        LOGGER.trace("getOneById({})", id);
        final String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
        List<Sport> sports = null;

        try {
            sports = jdbcTemplate.query(sql, this::mapRow, id);
        } catch (NotFoundException e) {
            throw new PersistenceException("Could not read any sport with id " + id);
        }

        if (sports.isEmpty()) throw new NotFoundException("Could not find sport with id " + id);

        return sports.get(0);

    }

    @Override
    public List<Sport> getAllSports() {
        LOGGER.trace("getAllSports()");
        final String sql = "SELECT * FROM " + TABLE_NAME;
        List<Sport> sports = null;

        try {
            sports = jdbcTemplate.query(sql, this::mapRow);
        } catch (NotFoundException e) {
            throw new PersistenceException("Could not read any sports");
        }
        if (sports.isEmpty()) throw new NotFoundException("Could not find any sports");

        return sports;
    }

    @Override
    public List<Long> getIdBySport(Sport sport) {
        LOGGER.trace("getIdBySport({})", sport);
        StringBuilder sql = new StringBuilder();
        List<Object> arguments = new ArrayList<>();
        sql.append("SELECT id FROM " + TABLE_NAME + " WHERE ");
        if (sport.getName() != null) {
            sql.append("name=? AND ");
            arguments.add(sport.getName());
        }
        if (sport.getDesc() != null) {
            sql.append("desc=? AND ");
            arguments.add(sport.getDesc());
        }

        sql.delete(sql.length() - 4, sql.length() - 1);
        final String sqlStat = sql.toString();


        List<Long> ids = null;

        try {
            ids = jdbcTemplate.query(sqlStat, this::mapRowId, arguments.toArray());
        } catch (NotFoundException e) {
            throw new PersistenceException("Could not read id with horse: " + sport.toString(), e);
        }


        return ids;
    }

    @Override
    public Sport saveSport(Sport sport) {
        LOGGER.trace("saveSport({})", sport);

        final String sql = "INSERT INTO " + TABLE_NAME + " (name, desc) VALUES (?, ?)";
        final String sqlTest = "INSERT INTO " + TABLE_NAME + " (name, desc, dob, sex) VALUES (?, ?, ?,?)";

        try {
            jdbcTemplate.update(sql, sport.getName(), sport.getDesc());
        } catch (DataAccessException e) {
            throw new PersistenceException("Error while saving sport: " + sport.toString(), e);
        }


        return sport;
    }

    @Override
    public void deleteSportById(Long id) {
        LOGGER.trace("deleteSportById({})", id);

        final String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
        final String sqlHorse = "UPDATE Horse" + " SET fav_sport=? WHERE fav_sport=?";

        try {

            jdbcTemplate.update(sqlHorse, null, id);
            jdbcTemplate.update(sql, id);
        } catch (NotFoundException e) {
            throw new PersistenceException("Nothing to delete");
        }
    }


    private Sport mapRow(ResultSet resultSet, int i) throws SQLException {
        final Sport sport = new Sport();
        sport.setId(resultSet.getLong("id"));
        sport.setName(resultSet.getString("name"));
        sport.setDesc(resultSet.getString("desc"));
        return sport;
    }

    private Long mapRowId(ResultSet resultSet, int i) throws SQLException {
        Long id;
        id = resultSet.getLong("id");
        return id;
    }
}
