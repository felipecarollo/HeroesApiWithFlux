package com.sys.heroesapi.controller;

import static com.sys.heroesapi.constants.HeroesConstat.*;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import com.sys.heroesapi.document.Heroes;
import com.sys.heroesapi.repository.HeroesRepository;
import com.sys.heroesapi.service.HeroesService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class HeroesController {

	
	private HeroesService heroesService;
	
	private HeroesRepository heroesRepository;
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HeroesController.class);
	
	public HeroesController (HeroesService heroesService,
			HeroesRepository heroesRepository) {
		this.heroesRepository = heroesRepository;
		this.heroesService = heroesService;
	}
	
	@GetMapping(HEROES_ENDPOINT_LOCAL)
	public Flux<Heroes> getAllItems(){
		log.info("Requesting the list of all heroes");
		return heroesService.findAll();
	}
	
	@GetMapping(HEROES_ENDPOINT_LOCAL+"/{id}")
	public Mono <ResponseEntity<Heroes>> findByIdHero(
			@PathVariable String id){
		log.info("Requesting the hero by id: "+id);
		return heroesService.findById(id)
				.map((item)-> new ResponseEntity<>(item, org.springframework.http.HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(org.springframework.http.HttpStatus.NOT_FOUND));
	}
	
	@PostMapping(HEROES_ENDPOINT_LOCAL)
	@ResponseStatus(code=HttpStatus.CREATED)
	public Mono<Heroes> createHero(@RequestBody Heroes heroes){
		log.info("A new hero was created");
		return heroesService.save(heroes);
	}
	
	@DeleteMapping(HEROES_ENDPOINT_LOCAL+"/{id}")
	@ResponseStatus(code = org.springframework.http.HttpStatus.CONTINUE)
	public Mono<org.springframework.http.HttpStatus> deleteById(@PathVariable String id){
		heroesService.deleteByIdHero(id);
		log.info("deleting a hero with id {}", id);
		return Mono.just(org.springframework.http.HttpStatus.CONTINUE);
	}
}
