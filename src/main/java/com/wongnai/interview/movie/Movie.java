package com.wongnai.interview.movie;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "movie")
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name")
	private String name;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "MOVIE_ACTORS",
			joinColumns=@JoinColumn(name = "id", referencedColumnName = "id")
	)
	@Column(name = "actors")
	private List<String> actors = new ArrayList<>();

	/**
	 * Required by JPA.
	 */
	protected Movie() {
	}

	public Movie(String name, List<String> actors) {
		this.name = name;
		this.actors = actors;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getActors() {
		return actors;
	}

}
