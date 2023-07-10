package com.ahmeterdogan;

import com.ahmeterdogan.data.entity.Movie;
import com.ahmeterdogan.data.repository.MovieRepository;
import com.ahmeterdogan.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MovieApp {
    public static void main(String[] args) {
//        System.out.println("Find All");
//        MovieRepository.findAll().stream().forEach(System.out::println);
//        System.out.println("*******************");
//        System.out.println("SAVE");
//        Movie pulpFiction = new Movie(-1,"Pulp Fiction", "Quentin Tarantino", 1800);
//        MovieRepository.save(pulpFiction);
//        System.out.println(pulpFiction);
//        System.out.println("*******************");
//        System.out.println("Find By Director");
//        MovieRepository.findByDirector("Quentin Tarantino").stream().forEach(System.out::println);
//        System.out.println("*******************");
//        System.out.println("Find By Id");
//        System.out.println(MovieRepository.findById(1));
//        System.out.println("*******************");
          commitExample();

    }

    private static void commitExample() {
        try{
            Connection connection = DatabaseUtil.getConnection();
            connection.setAutoCommit(false);//autoCommit false çektin o zaman kendin commit yapmalısın. true bıraktığımız statement1 çalışıp statement2'den hata alsak bile statement1'deki update veritabanına yansır
            PreparedStatement statement1 = connection.prepareStatement("insert into movie.movies(name, director, year) values (?, ?, ?)");
            statement1.setString(1, "Uncut Gems");
            statement1.setString(2, "Safdie Brothers");
            statement1.setInt(3, 2019);
            statement1.executeUpdate();
            Statement statement2 = connection.createStatement();
            statement2.executeUpdate("update movie.movies set year = 1991 where name = 'Goodfellas'");
            connection.commit(); //autoCommit'i false'a çektiğimiz için bu satır çalışmazsa örneğin yukardan catch'e düşülürse otomatik rollback yapılacaktır zaten

            //başka faktörlere bağlı olarak rollback yapma durumun varsa yapabilirsin ancak autoCommit false çektikten sonra exception alırsan zaten otomatik rollback yapılacaktır.

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
