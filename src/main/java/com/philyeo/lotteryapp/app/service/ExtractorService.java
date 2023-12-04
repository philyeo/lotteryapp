package com.philyeo.lotteryapp.app.service;

import com.philyeo.lotteryapp.shared.persistance.document.DamacaiResults;
import com.philyeo.lotteryapp.shared.persistance.document.MagnumResults;
import com.philyeo.lotteryapp.shared.persistance.document.TotoResults;
import com.philyeo.lotteryapp.shared.persistance.repository.DamacaiRepository;
import com.philyeo.lotteryapp.shared.persistance.repository.MagnumRepository;
import com.philyeo.lotteryapp.shared.persistance.repository.TotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public String getTotoResultsCSVByDrawDate(String drawDate) {
        TotoResults results = getTotoResultsByDrawDate(drawDate);
        StringBuilder csvData = new StringBuilder();

        if (results != null) {
            appendCSVLine(csvData, results.getResult().getToto4D().getFirstPrize(), results.getDrawDate(), "toto4D.firstPrize");
            appendCSVLine(csvData, results.getResult().getToto4D().getSecondPrize(), results.getDrawDate(), "toto4D.secondPrize");
            appendCSVLine(csvData, results.getResult().getToto4D().getThirdPrize(), results.getDrawDate(), "toto4D.thirdPrize");

            List<String> consolationList = results.getResult().getToto4D().getConsolationPrize();
            if (consolationList != null && !consolationList.isEmpty()) {
                for (String consolation : consolationList) {
                    appendCSVLine(csvData, consolation, results.getDrawDate(), "toto4D.consolationPrize");
                }
            }
        }

        return csvData.toString();
    }

    public MagnumResults getMagnumResultsByDrawDate(String drawDate) {
        return magnumRepository.findByDrawDate(drawDate);
    }

    public DamacaiResults getDamacaiResultsByDrawDate(String drawDate) {
        return damacaiRepository.findByDrawDate(drawDate);
    }

    private void appendCSVLine(StringBuilder csvData, String value, String field1, String field2) {
        if (value != null && !value.isEmpty()) {
            csvData.append(value).append(", ").append(field1).append(", ").append(field2).append("\n");
        }
    }

}
