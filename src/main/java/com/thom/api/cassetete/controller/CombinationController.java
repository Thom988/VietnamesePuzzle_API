package com.thom.api.cassetete.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.thom.api.cassetete.model.Combination;
import com.thom.api.cassetete.service.CombinationService;

@RestController
public class CombinationController {
    
    @Autowired
    CombinationService combinationService;
    
    // Obtenir toutes les combinaisons de la BDD
    @GetMapping("/combinations")
    public Iterable<Combination> getCombinations() {
	return combinationService.getCombinations();
    }
    
    // tester la présence et renvoyer une combinaison si elle existe
    @GetMapping("/combination/{id}")
    public ResponseEntity<Combination> getCombination(@PathVariable("id") final Integer id){
	Optional<Combination> combination = combinationService.getCombination(id);
	return combination.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    // Supprimer une combinaison (par id)
    @DeleteMapping("/combination/{id}")
    public ResponseEntity<Void> deleteCombination(@PathVariable("id") final Integer id) {
	if (combinationService.getCombination(id).isPresent()) {
	        combinationService.deleteCombination(id);
	        return ResponseEntity.noContent().build(); // la suppression réussie
	    } else {
	        return ResponseEntity.notFound().build(); // La combinaison n'a pas été trouvée
	    }
    }
    
    // Modifier une combinaison, tester sa validité et la sauvegarder en BDD
    @PutMapping("/combination/{id}")
    public ResponseEntity<Combination> updateCombination(@PathVariable("id") final Integer id, @RequestBody Combination combination) {
	Optional<Combination> comb = combinationService.getCombination(id);
	if(comb.isPresent()) {
	    Combination currentComb = comb.get();
	    String value = combination.getValue();
	    if (value != null) {
		int[] intValue = combinationService.stringToIntArray(value);
		currentComb.setValue(value);
		currentComb.setValid(combinationService.isCombinationValid(intValue));
	    }
	    combinationService.saveCombination(currentComb);
	    return ResponseEntity.ok(currentComb);
	} else {
	    return ResponseEntity.notFound().build();
	}
    }
}
