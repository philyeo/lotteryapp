package com.philyeo.lotteryapp.app.service;

import com.philyeo.lotteryapp.shared.Utils;
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

        Integer year = Integer.parseInt(drawDate.substring(drawDate.length() - 4));
        System.out.println(year);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        LocalDate date = LocalDate.parse(drawDate, formatter);
//        System.out.println(date.toString());
        String zodiacYear = Utils.getChineseZodiac(year);

        if (results != null) {
            appendCSVLine(csvData, results.getResult().getToto4D().getFirstPrize(), results.getDrawDate(), "toto", "toto4D.firstPrize", zodiacYear);
            appendCSVLine(csvData, results.getResult().getToto4D().getSecondPrize(), results.getDrawDate(), "toto", "toto4D.secondPrize", zodiacYear);
            appendCSVLine(csvData, results.getResult().getToto4D().getThirdPrize(), results.getDrawDate(), "toto", "toto4D.thirdPrize", zodiacYear);

            List<String> consolationList = results.getResult().getToto4D().getConsolationPrize();
            if (consolationList != null && !consolationList.isEmpty()) {
                for (String consolation : consolationList) {
                    appendCSVLine(csvData, consolation, results.getDrawDate(), "toto", "toto4D.consolationPrize", zodiacYear);
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

    private void appendCSVLine(StringBuilder csvData, String number, String drawDate, String lottery, String category, String zodiacYear) {
        if (number != null && !number.isEmpty()) {
            csvData.append(number).append(", ").append(drawDate).append(", ").append(lottery).append(", ").append(category).append(", ").append(zodiacYear).append("\n");
        }
    }

}
