package com.philyeo.lotteryapp.shared.persistance.repository;

import com.philyeo.lotteryapp.shared.dto.magnum.MagnumResult;
import com.philyeo.lotteryapp.shared.persistance.document.MagnumResults;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagnumRepository extends MongoRepository<MagnumResult, String> {

    void insert(MagnumResults results);

    MagnumResults findByDrawDate(String drawDate);

}
