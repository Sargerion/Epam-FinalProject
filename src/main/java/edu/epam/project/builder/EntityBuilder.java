package edu.epam.project.builder;

import edu.epam.project.entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface EntityBuilder <T extends Entity> {
    T build(ResultSet resultSet) throws SQLException;
}