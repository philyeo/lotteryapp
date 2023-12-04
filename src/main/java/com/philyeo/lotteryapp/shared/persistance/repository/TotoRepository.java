package com.philyeo.lotteryapp.shared.persistance.repository;

import com.philyeo.lotteryapp.shared.dto.toto.TotoResult;
import com.philyeo.lotteryapp.shared.persistance.document.TotoResults;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotoRepository extends MongoRepository<TotoResult, String> {

    void insert(TotoResults results);
    TotoResults findByDrawDate(String drawDate);



}
