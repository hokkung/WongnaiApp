package com.wongnai.interview.movie.search;

import java.util.*;
import java.util.stream.Collectors;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.MovieSearchService;

import javax.annotation.PostConstruct;
import javax.swing.*;

@Component("invertedIndexMovieSearchService")
@DependsOn("movieDatabaseInitializer")
public class InvertedIndexMovieSearchService implements MovieSearchService, InitializingBean {
	@Autowired
	private MovieRepository movieRepository;

    private Map<String, List<Long>> map = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {

        // Loop all in-memory
        for (Movie movie : movieRepository.findAll()) {
            String name = movie.getName().trim().toLowerCase();
            Long id = movie.getId();

            for (String word : name.split(" ")) {
                List<Long> ids = map.get(word);
                if (ids == null) {
                    // New
                    List<Long> oldId = new ArrayList<>(Arrays.asList(id));
                    map.put(word, oldId);
                } else {
                    // Update
                    boolean isSame = false;
                    for (Long same : ids) {
                        if (same.equals(id)) {
                            isSame = true;
                        }
                    }
                    if (!isSame) {
                        ids.add(id);
                        map.put(word, ids);
                    }
                }
            }
        }
    }

	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 4 => Please implement in-memory inverted index to search movie by keyword.
		// You must find a way to build inverted index before you do an actual search.
		// Inverted index would looks like this:
		// -------------------------------
		// |  Term      | Movie Ids      |
		// -------------------------------
		// |  Star      |  5, 8, 1       |
		// |  War       |  5, 2          |
		// |  Trek      |  1, 8          |
		// -------------------------------
		// When you search with keyword "Star", you will know immediately, by looking at Term column, and see that
		// there are 3 movie ids contains this word -- 1,5,8. Then, you can use these ids to find full movie object from repository.
		// Another case is when you find with keyword "Star War", there are 2 terms, Star and War, then you lookup
		// from inverted index for Star and for War so that you get movie ids 1,5,8 for Star and 2,5 for War. The result that
		// you have to return can be union or intersection of those 2 sets of ids.
		// By the way, in this assignment, you must use intersection so that it left for just movie id 5.

        List<Movie> movies = new ArrayList<>();
        Map<String, List<Long>> newMap = new HashMap<>();

        // List word
        String[] words = queryText.trim().toLowerCase().split(" ");
        for (String word : words) {
            // If has key in in-memory
            if (map.get(word) != null) {
                // keep in hash map
                newMap.put(word, map.get(word));
            }
        }
        if (newMap.size() > 1) {
            List<Long> eachIdPerMovie = new ArrayList<>();
            for (Map.Entry entry : newMap.entrySet()) {
                print(entry.getKey().toString());
                eachIdPerMovie.addAll((List<Long>) entry.getValue());
            }
            movies = (List<Movie>) movieRepository.findAllById(findDuplicate(eachIdPerMovie));
        } else if (newMap.size() == 1) {
            Map.Entry<String, List<Long>> entry = newMap.entrySet().iterator().next();
            movies = (List<Movie>) movieRepository.findAllById(entry.getValue());
        }
        return movies;
    }

    private List<Long> findDuplicate(List<Long> all) {
        Map<Long, Integer> score = new HashMap<>();
        List<Long> real = new ArrayList<>();
        int max = 0;

        // Find duplicate
        for (int i = 0 ; i < all.size(); i++) {
            Long key = all.get(i);
            if (score.get(key) != null) {
                score.put(key, score.get(key) + 1);
            } else {
                score.put(key, 1);
            }
        }

        // Find max value
        for (int j : score.values()) {
            if (j > max) {
                max = j;
            }
        }

        // Find real max
        for (Map.Entry entry : score.entrySet()) {
            if ((int)entry.getValue() == max) {
                real.add((Long)entry.getKey());
            }
        }

        return real;
    }

    private void print(String msg) {
        System.out.println(msg);
    }

}
