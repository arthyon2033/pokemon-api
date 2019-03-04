package com.arthyon.pokemonapi.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.arthyon.pokemonapi.model.PokemonModel;

@Repository
public interface PokemonRepository extends MongoRepository<PokemonModel, String> {
	
	public PokemonModel findByName(String name);

}
