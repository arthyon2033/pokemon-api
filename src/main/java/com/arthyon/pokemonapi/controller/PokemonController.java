package com.arthyon.pokemonapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.arthyon.pokemonapi.model.PokemonModel;
import com.arthyon.pokemonapi.model.PokemonResume;
import com.arthyon.pokemonapi.service.PokemonService;

@RestController
@CrossOrigin
public class PokemonController {
	
	@Autowired
	PokemonService service;
	
	@GetMapping(value="/pokemons")
	public List<PokemonResume> returnPokemons() {
		return service.definePokemonsFirstGeneration();
	}
	
	@GetMapping(value="/types")
	public List<String> returnTypes() {
		return service.definePokemonTypes();
	}
	
	@GetMapping(value="/detail/{pokemon}")
	public PokemonModel returnPokemonDetails(@PathVariable String pokemon) {
		return service.findPokemonDetails(pokemon);
	}

}
