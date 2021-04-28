package at.ac.tuwien.sepm.assignment.individual.persistence.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.SportDao;
import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<Sport> sports = jdbcTemplate.query(sql, this::mapRow, id);

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
        } catch (NotFoundException e){
            throw new PersistenceException("Could not read any horses" );
        }
        if (sports.isEmpty()) throw new NotFoundException("Could not find any horses");

        return sports;
    }

    @Override
    public List<Long> getIdBySport(Sport sport) {
        LOGGER.trace("getIdBySport({})", sport);
        final String sql = "SELECT id FROM " + TABLE_NAME + " WHERE name=?";
        List<Long> ids = jdbcTemplate.query(sql, this::mapRowId, sport.getName());

        return ids;
    }

    @Override
    public Sport saveSport(Sport sport) {
        LOGGER.trace("saveHorse({})", sport);

        final String sql = "INSERT INTO " + TABLE_NAME + " (name) VALUES (?)";
        final String sqlTest = "INSERT INTO " + TABLE_NAME + " (name, desc, dob, sex) VALUES (?, ?, ?,?)";

        jdbcTemplate.update(sql,sport.getName());




        return sport;
    }


    private Sport mapRow(ResultSet resultSet, int i) throws SQLException {
        final Sport sport = new Sport();
        sport.setId(resultSet.getLong("id"));
        sport.setName(resultSet.getString("name"));
        return sport;
    }

    private Long mapRowId(ResultSet resultSet, int i) throws SQLException {
        Long id;
        id = resultSet.getLong("id");
        return id;
    }
}
