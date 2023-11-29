package com.philyeo.lotteryapp.shared.persistance.repository;

import com.philyeo.lotteryapp.shared.dto.DamacaiResult;
import com.philyeo.lotteryapp.shared.persistance.document.DamacaiResults;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DamacaiRepository extends MongoRepository<DamacaiResult, String> {

    void insert(DamacaiResults results);
}
