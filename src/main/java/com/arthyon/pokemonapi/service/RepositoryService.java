package com.arthyon.pokemonapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arthyon.pokemonapi.model.PokemonModel;
import com.arthyon.pokemonapi.repository.PokemonRepository;

@Service
public class RepositoryService {
	
	@Autowired
	private PokemonRepository repository;
	
	public List<PokemonModel> findAllPokemons() {
		return this.repository.findAll();
	}
	
	public PokemonModel findByName(String name) {
		return this.repository.findByName(name);
	}
	
	public PokemonModel insertPokemon(PokemonModel pokemon) {
		return this.repository.save(pokemon);
	}
	
	public void removePokemon(String id) {
		this.repository.deleteById(id);
	}

}
