package com.ahmeterdogan;

import com.ahmeterdogan.data.entity.Movie;
import com.ahmeterdogan.data.repository.MovieRepository;
public class MovieApp {
    public static void main(String[] args) {
        System.out.println("Find All");
        MovieRepository.findAll().stream().forEach(System.out::println);
        System.out.println("*******************");
        System.out.println("SAVE");
        Movie titanic = new Movie(-1,"Pulp Fiction", "Quentin Tarantino", 1990);
        MovieRepository.save(titanic);
        System.out.println(titanic);
        System.out.println("*******************");
        System.out.println("Find By Director");
        MovieRepository.findByDirector("Quentin Tarantino").stream().forEach(System.out::println);
        System.out.println("*******************");
        System.out.println("Find By Id");
        System.out.println(MovieRepository.findById(1));
        System.out.println("*******************");


    }
}
