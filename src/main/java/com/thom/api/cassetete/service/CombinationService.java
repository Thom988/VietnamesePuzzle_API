package com.thom.api.cassetete.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thom.api.cassetete.model.Combination;
import com.thom.api.cassetete.repository.CombinationRepository;

@Service
public class CombinationService {
    
    @Autowired
    CombinationRepository combinationRepository;
    
    private static final Integer RESULT = 66;
    private long startTime = 0;
    private long endTime = 0;
    // A FAIRE 
    
    // depart de la génération des combinaisons possibles et test des combinaisons valides + calcul du temps d'execution
    public Integer generateSolutions() {
	int[] userVal = {9, 8, 7, 6, 5, 4, 3, 2, 1};
	startTime = System.currentTimeMillis(); // Enregistrer l'heure de début
	generateCombinations(userVal, 0);
	endTime = System.currentTimeMillis(); // Enregistrer l'heure de fin
	long executionTime = endTime - startTime; // Calculer le temps d'exécution en millisecondes
	System.out.println("Temps d'exécution de generateSolutions : " + executionTime + " millisecondes");
	return Math.toIntExact(executionTime);
    }
    
    // Génère toutes les combinaisons possibles (récursivité) pour un tableau d'entier unique de 1 à 9 = 362880 possibilité, soit 9!
    private void generateCombinations(int[] tableau, int debut) {
	if (debut == tableau.length - 1) {
	    checkAndSaveValidCombination(tableau);
            return;
        }
        for (int i = debut; i < tableau.length; i++) {
             swap(tableau, debut, i);
             generateCombinations(tableau, debut + 1);
             swap(tableau, debut, i);
        }
    }
    
    // Permutation de deux entiers dans une combinaison
    private void swap(int[] tableau, int i, int j) {
	int temp = tableau[i];
        tableau[i] = tableau[j];
        tableau[j] = temp;
    }
    
    // Test de la validiter d'une combinaison. Si valide, enregistrement en BDD
    private void checkAndSaveValidCombination(int[] combVal) {
	if (isCombinationValid(combVal)) {
	    Combination comb = new Combination();
	    String valuesAsString = intArrayToString(combVal);
	    comb.setValue(valuesAsString);
	    comb.setValid(true);
	    saveCombination(comb);
	}
    }
    
    public boolean isCombinationValid(int[] combVal) {
	// Test l'egalité de l'equation
	float result = combVal[0] + (13* (float) combVal[1]/combVal[2]) + combVal[3] + (12*combVal[4]) - combVal[5] - 11 + ((float) combVal[6]*combVal[7]/combVal[8]) - 10;
	return result == RESULT;
    }
    
    // transforme le tableau d'entier en une chaine de caractère
    public String intArrayToString(int[] combination) {
	// A FAIRE => StringJoiner à la place de StringBuilder pour ne pas avoir à gerer le dernier cas avec la virgule
	StringBuilder valuesBuilder = new StringBuilder();
	for (int i = 0; i < combination.length; i++) {
	    valuesBuilder.append(combination[i]);
	    if (i < combination.length - 1) {
	        valuesBuilder.append(",");
	    }
	}
	return valuesBuilder.toString();
    }
    
    // transforme une chaine de caractère en tableau d'entier
    public int[] stringToIntArray(String stringVal) {
	String[] numbersAsString = stringVal.split("");    
	int[] numbers = new int[numbersAsString.length];  
	for (int i = 0; i < numbersAsString.length; i++) {
	    numbers[i] = Integer.parseInt(numbersAsString[i]);
	}
	return numbers;
    }
    
    public String formatString(String stringVal) {
        return String.join(",", stringVal.split(""));
    }
    
    // METHODES CRUD BDD ---------------------------------------------------------------------------------------------------------
    
    /**
     * Sauvegarde ou modification d'une combinaison valide en base de données
     * @param combination
     * @return
     */
    public Combination saveCombination(Combination combination) {
	return combinationRepository.save(combination);
    }
    
    /**
     * Suppression d'une combinaison valide (par Id)
     * @param id
     */
    public void deleteCombination(Integer id) {
	combinationRepository.deleteById(id);
    }
    
    /**
     * Suppression de toutes les combinaisons de la base de données
     */
    public void deleteCombinations() {
	combinationRepository.deleteAll();
	System.out.println("Suppression des combinaisons");
    }
    
    /**
     * obtenir une combinaison (par id)
     * @param id
     * @return
     */
    public Optional<Combination> getCombination(Integer id) {
	return combinationRepository.findById(id);
    }
    
    /**
     * obtenir toutes les combinaisons 
     * @return
     */
    public Iterable<Combination> getCombinations() {
	return combinationRepository.findAll();
    }
    
    public Iterable<Combination> getCombinationsContaining(String value) {
	return this.combinationRepository.findByValueContaining(value);
    }
    
    
//  // Suppression d'une combinaison valide (Par entité)
//  public void deleteCombination(Combination combination) {
//	combinationRepository.delete(combination);
//  }
    
//  // Obtenir une combinaison
//  public Optional<Combination> getCombination(Combination combination) {
//	return combinationRepository.findByValue(combination.getValue());
//  }
   
    
}
