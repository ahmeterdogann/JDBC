package com.ahmeterdogan.data.repository;

import com.ahmeterdogan.data.entity.Movie;
import com.ahmeterdogan.exception.MovieAppException;
import com.ahmeterdogan.util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;

public class MovieRepository {
    private static final String FIND_ALL_SQL = "select * from movie.movies";
    private static final String FIND_BY_ID_SQL = "select * from movie.movies where id = ?";
    private static final String FIND_BY_DIRECTOR_SQL = "select * from movie.movies where director = ?";
    private static final String SAVE_SQL = "insert into movie.movies(name, director, year) values (?, ?, ?)";
    public static Movie findById(long id) {
        Movie movie = null;

        try(Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            movie = fillMovie(resultSet).get(0);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return movie;
    }

    public static ArrayList<Movie> findAll() {
        ArrayList<Movie> movies = new ArrayList<>();

        try(Connection connection = DatabaseUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL_SQL);
            movies = fillMovie(resultSet);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return movies;
    }

    public static ArrayList<Movie> findByDirector(String director) {
        ArrayList<Movie> movies = new ArrayList<>();

        try(Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_DIRECTOR_SQL);
            preparedStatement.setString(1, director);
            ResultSet resultSet = preparedStatement.executeQuery();
            movies = fillMovie(resultSet);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return movies;
    }

    public static Movie save(Movie movie) {
        try(Connection connection = DatabaseUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, movie.getName());
            preparedStatement.setString(2, movie.getDirector());
            preparedStatement.setInt(3, movie.getYear());
            int effectedRows = preparedStatement.executeUpdate();
            if (effectedRows == 0)
                throw new MovieAppException("save failed");

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            long autoGeneratedId = -1;
            if (resultSet.next())
                autoGeneratedId = resultSet.getLong(1);

            movie.setId(autoGeneratedId);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return movie;
    }



    private static ArrayList<Movie> fillMovie(ResultSet resultSet) {
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            while (resultSet.next()) {
               var id = resultSet.getLong(1);
               var name = resultSet.getString(2);
               var director = resultSet.getString(3);
               var year = resultSet.getInt(4);
               Movie movie = new Movie(id, name, director, year);
               movies.add(movie);
           }

       }
       catch (SQLException e) {
           System.out.println(e.getMessage());
           e.printStackTrace();
       }

        return movies;
    }

}