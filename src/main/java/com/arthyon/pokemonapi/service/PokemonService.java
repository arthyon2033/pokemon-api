package com.arthyon.pokemonapi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.arthyon.pokemonapi.model.PokemonModel;
import com.arthyon.pokemonapi.model.PokemonResume;
import com.arthyon.pokemonapi.model.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PokemonService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	RepositoryService repository;

	private String url_base = "https://pokeapi.co/api/v2/";

	public List<String> definePokemonTypes() {
		List<String> types = new ArrayList<>();
		HttpEntity<String> httpEntity = configHttpEntity();
		try {
			ResponseEntity<String> response = restTemplate.exchange(url_base + "type", HttpMethod.GET, httpEntity,
					String.class);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.getBody());
			JsonNode results = root.path("results");
			for (int i = 0; i < results.size(); i++) {
				types.add(results.get(i).get("name").asText());
			}
			Collections.sort(types);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return types;
	}

	public List<PokemonResume> definePokemonsFirstGeneration() {
		List<PokemonResume> pokemons = new ArrayList<>();
		HttpEntity<String> httpEntity = configHttpEntity();
		try {
			ResponseEntity<String> response = restTemplate.exchange(url_base + "pokemon?offset=0&limit=20",
					HttpMethod.GET, httpEntity, String.class);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.getBody());
			JsonNode results = root.path("results");
			for (int i = 0; i < results.size(); i++) {
				PokemonResume pokemonTemp = new PokemonResume(results.get(i).get("name").asText(),
						"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + (i + 1) + ".png");
//				PokemonResume pokemonTemp = new PokemonResume(results.get(i).get("name").asText(), (i + 1) +".png");
				pokemons.add(pokemonTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pokemons;
	}

	public PokemonModel findPokemonDetails(String pokemon) {
		HttpEntity<String> httpEntity = configHttpEntity();
		PokemonModel pokemonDetail = new PokemonModel();
		PokemonModel pokemonDb = repository.findByName(pokemon);
		if (pokemonDb != null) {
			return pokemonDb;
		} else {
			try {
				ResponseEntity<String> response = restTemplate.exchange(url_base + "pokemon/" + pokemon, HttpMethod.GET,
						httpEntity, String.class);
				ObjectMapper mapper = new ObjectMapper();
				JsonNode root = mapper.readTree(response.getBody());
				pokemonDetail.setId(root.get("id").asInt());
				pokemonDetail.setName(StringUtils.capitalize(root.get("name").asText()));
				pokemonDetail.setHeight(root.get("height").asInt());
				pokemonDetail.setWeight(root.get("weight").asInt());
				pokemonDetail.setUrlImage(root.get("sprites").get("front_default").asText());
				List<String> abilities = new ArrayList<>();
				List<String> type = new ArrayList<>();
				List<Status> status = new ArrayList<>();
				JsonNode abilitiesTemp = root.get("abilities");
				JsonNode typeTemp = root.get("types");
				JsonNode statusTemp = root.get("stats");
				for (int i = 0; i < abilitiesTemp.size(); i++) {
					abilities.add(StringUtils.capitalize(abilitiesTemp.get(i).get("ability").get("name").asText()));
				}
				for (int i = 0; i < typeTemp.size(); i++) {
					type.add(StringUtils.capitalize(typeTemp.get(i).get("type").get("name").asText()));
				}
				for (int i = 0; i < statusTemp.size(); i++) {
					int valueTemp = statusTemp.get(i).get("base_stat").asInt();
					String nameTemp = StringUtils.capitalize(statusTemp.get(i).get("stat").get("name").asText());
					Status stats = new Status(nameTemp, valueTemp);
					status.add(stats);
				}
				pokemonDetail.setAbilities(abilities);
				pokemonDetail.setType(type);
				pokemonDetail.setStatus(status);
//				PokemonModel pokemonDetail = new PokemonModel(id, name, type, height, weight, abilities, urlImage, status);
				this.repository.insertPokemon(pokemonDetail);
				return pokemonDetail;
			} catch (Exception e) {
				e.printStackTrace();
			}
//			PokemonModel pokemonEmpty = new PokemonModel();
			return pokemonDetail;
		}
	}

	public HttpEntity<String> configHttpEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		return httpEntity;
	}

}
