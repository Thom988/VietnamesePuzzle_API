package com.thom.api.cassetete.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.thom.api.cassetete.model.Combination;

@Repository
public interface CombinationRepository extends CrudRepository<Combination, Integer> {

//    public Optional<Combination> findByValue(String value);
    
    public Iterable<Combination> findByValueContaining(String partialValue);
}
