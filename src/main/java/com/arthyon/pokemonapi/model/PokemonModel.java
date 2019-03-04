package com.arthyon.pokemonapi.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PokemonModel {
	
	@Id
	private String hash;
	private int id;
	private String name;
	private List<String> type;
	private int height;
	private int weight;
	private List<String> abilities;
	private String urlImage;
	private List<Status> status;
	
	public PokemonModel() {
		this.id = 0;
		this.name = "";
		this.type = new ArrayList<String>();
		this.height = 0;
		this.weight = 0;
		this.abilities = new ArrayList<String>();
		this.urlImage = "";
		this.status = new ArrayList<Status>();
	}

	public PokemonModel(int id, String name, List<String> type, int height, int weight, List<String> abilities, String urlImage, List<Status> status) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.height = height;
		this.weight = weight;
		this.abilities = abilities;
		this.urlImage = urlImage;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getType() {
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public List<String> getAbilities() {
		return abilities;
	}

	public void setAbilities(List<String> abilities) {
		this.abilities = abilities;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public List<Status> getStatus() {
		return status;
	}

	public void setStatus(List<Status> status) {
		this.status = status;
	}
	
}
