package com.philyeo.lotteryapp.app.service;

import com.philyeo.lotteryapp.shared.persistance.document.DamacaiResults;
import com.philyeo.lotteryapp.shared.persistance.document.MagnumResults;
import com.philyeo.lotteryapp.shared.persistance.document.TotoResults;
import com.philyeo.lotteryapp.shared.persistance.repository.DamacaiRepository;
import com.philyeo.lotteryapp.shared.persistance.repository.MagnumRepository;
import com.philyeo.lotteryapp.shared.persistance.repository.TotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtractorService {

    private final TotoRepository totoRepository;

    private final MagnumRepository magnumRepository;

    private final DamacaiRepository damacaiRepository;
    @Autowired
    public ExtractorService(TotoRepository totoRepository, MagnumRepository magnumRepository, DamacaiRepository damacaiRepository) {
        this.totoRepository = totoRepository;
        this.magnumRepository = magnumRepository;
        this.damacaiRepository = damacaiRepository;
    }

    public TotoResults getTotoResultsByDrawDate(String drawDate) {
        return totoRepository.findByDrawDate(drawDate);
    }

    public MagnumResults getMagnumResultsByDrawDate(String drawDate) {
        return magnumRepository.findByDrawDate(drawDate);
    }

    public DamacaiResults getDamacaiResultsByDrawDate(String drawDate) {
        return damacaiRepository.findByDrawDate(drawDate);
    }

}
