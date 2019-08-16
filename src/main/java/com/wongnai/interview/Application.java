package com.wongnai.interview;

import com.wongnai.interview.movie.sync.MovieDatabaseInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
public class Application {
	public static void main(String[] args) throws Exception {
//		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
//		MovieDatabaseInitializer movieDatabaseInitializer = context.getBean("movieDatabaseInitializer", MovieDatabaseInitializer.class);
//		movieDatabaseInitializer.afterPropertiesSet();
		SpringApplication.run(Application.class, args);
	}
}
