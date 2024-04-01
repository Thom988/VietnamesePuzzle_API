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
    
    // depart de la génération des combinaisons possibles et test des combinaisons valides + calcul du temps d'execution
    public void generateSolutions() {
	long startTime = System.currentTimeMillis(); // Enregistrer l'heure de début
        
        int[] userVal = {9,8,7,6,5,4,3,2,1}; // 52.888885
        generateCombinations(userVal, 0);
        
        long endTime = System.currentTimeMillis(); // Enregistrer l'heure de fin
        long executionTime = endTime - startTime; // Calculer le temps d'exécution en millisecondes
        System.out.println("Temps d'exécution de generateSolutions : " + executionTime + " millisecondes");
    }
    
    // Génère toutes les combinaisons possibles (récursivité) pour un tableau d'entier unique de 1 à 9 = 362880 possibilité, soit 9!
    private void generateCombinations(int[] tableau, int debut) {
	if (debut == tableau.length - 1) {
	    checkAndSaveValidCombination(tableau);
            return;
        } else {
            for (int i = debut; i < tableau.length; i++) {
                swap(tableau, debut, i);
                generateCombinations(tableau, debut + 1);
                swap(tableau, debut, i);
            }
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
	float result = combVal[0] + (13* (float) combVal[1]/combVal[2]) + combVal[3] + (12*combVal[4]) - combVal[5] - 11 + ((float) combVal[6]*combVal[7]/combVal[8]) - 10;
	if (result == 66) {
	    Combination comb = new Combination();
	    String valuesAsString = intArrayToString(combVal);
	    comb.setValue(valuesAsString);
	    comb.setValid(true);
	    saveCombination(comb);
	}
    }
    
    public boolean isCombinationValid(int[] combVal) {
	float result = combVal[0] + (13* (float) combVal[1]/combVal[2]) + combVal[3] + (12*combVal[4]) - combVal[5] - 11 + ((float) combVal[6]*combVal[7]/combVal[8]) - 10;
	if (result == 66) {
	    return true;
	} else {
	    return false;
	}
    }
    
    // transforme le tableau d'entier en une chaine de caractère
    public String intArrayToString(int[] combination) {
	StringBuilder valuesBuilder = new StringBuilder();
	for (int i = 0; i < combination.length; i++) {
	    valuesBuilder.append(combination[i]);
	    if (i < combination.length - 1) {
	        valuesBuilder.append(",");
	    }
	}
	String valuesAsString = valuesBuilder.toString();
	return valuesAsString;
    }
    
    // transforme une chaine de caractère en tableau d'entier
    public int[] stringToIntArray(String stringVal) {
	// Divisez la chaîne en sous-chaînes en utilisant la virgule comme séparateur
	String[] numbersAsString = stringVal.split(",");    
	// Créer un tableau pour stocker les entiers
	int[] numbers = new int[numbersAsString.length];  
	// Convertir chaque sous-chaîne en un entier et stocker dans le tableau
	for (int i = 0; i < numbersAsString.length; i++) {
	    numbers[i] = Integer.parseInt(numbersAsString[i].trim()); // Utilisez trim() pour supprimer les espaces éventuels
	}
	return numbers;
    }
    
    // METHODES CRUD BDD ---------------------------------------------------------------------------------------------------------
    
    // Sauvegarde ou modification d'une combinaison valide en base de données
    public Combination saveCombination(Combination combination) {
	return combinationRepository.save(combination);
    }
    
    // Suppression d'une combinaison valide (par Id)
    public void deleteCombination(Integer id) {
	combinationRepository.deleteById(id);
    }
    
    // Suppression de toutes les combinaisons de la base de données
    public void deleteCombinations(Combination combination) {
	combinationRepository.deleteAll();
    }
    
    // obtenir une combinaison (par id)
    public Optional<Combination> getCombination(Integer id) {
	return combinationRepository.findById(id);
    }
    
    // obtenir toutes les combinaisons 
    public Iterable<Combination> getCombinations() {
	return combinationRepository.findAll();
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
