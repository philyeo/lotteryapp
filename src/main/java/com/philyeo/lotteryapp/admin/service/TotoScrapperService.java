package com.philyeo.lotteryapp.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.philyeo.lotteryapp.shared.TriFunction;
import com.philyeo.lotteryapp.shared.dto.toto.*;
import com.philyeo.lotteryapp.shared.persistance.document.TotoResults;
import com.philyeo.lotteryapp.shared.persistance.repository.TotoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.philyeo.lotteryapp.shared.EndpointConstants.*;

@Service
@AllArgsConstructor
@Slf4j
public class TotoScrapperService {

    private ObjectMapper objectMapper = new ObjectMapper();

    private TotoRepository repository;

    public void scrapDrawResultByDate(String date) throws IOException {

        if(isValidDateFormat(date)) {
            Map<String, String> drawDateMap = getDateDrawMap(MONTHVIEW_TOTO + getFixedDateForUrl(date));
            //get the DrawNo by DrawDate
            String drawNo = drawDateMap.get(getDateForDateDrawMapKey(date));

            TotoResult result = getDrawResult(TEST_TOTO_PRINT + drawNo);
            repository.insert(TotoResults.builder()
                    .drawDate(result.getDrawDate())
                    .result(result)
                .build());

        } else {
            //throw an error here
        }
    }

    private TotoResult getDrawResult(String printUrl) {
        try {
            // Fetch and parse the HTML content from a URL
            Document doc = Jsoup.connect(printUrl).get();

            // Using CSS selectors to select specific elements
            Element el1 = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(2) > td > div > table > tbody > tr > td:nth-child(1) > span > font > b");
            String drawDate = getDrawHeaderDetailsTriFunction.apply(el1.childNode(0).toString(), ":", 1);

            Elements el2 = doc.selectXpath("/html/body/div/center/table/tbody/tr[2]/td/div/table/tbody/tr/td[2]/span/b/font");
            String drawNo = getDrawHeaderDetailsTriFunction.apply(el2.get(0).childNode(0).toString(), "No.", 1);

            Elements top3els = doc.selectXpath("/html/body/div/center/table/tbody/tr[5]/td/table[1]/tbody/tr[2]/td/span");
            List<String> top3PrizeText = top3els.eachText();

            Elements elSpecialPrize = doc.selectXpath("/html/body/div/center/table/tbody/tr[5]/td/table[2]/tbody/tr[position()>1]");
            List<String> specialPrizeText = elSpecialPrize.eachText();

            List<String> allSpecialPrizeList = specialPrizeText.stream()
                .flatMap(str -> Arrays.stream(str.split("\\s+")))
                .collect(Collectors.toList());

            Elements elConsolationPrize = doc.selectXpath("/html/body/div/center/table/tbody/tr[5]/td/table[3]/tbody/tr[position()>1]");
            List<String> consolationPrizeText = elConsolationPrize.eachText();

            List<String> allConsolationPrizeList = consolationPrizeText.stream()
                .flatMap(str -> Arrays.stream(str.split("\\s+")))
                .collect(Collectors.toList());

            Toto4D toto4D = new Toto4D(top3PrizeText.get(0), top3PrizeText.get(1), top3PrizeText.get(2),
                                       allSpecialPrizeList, allConsolationPrizeList);

            Element el4DjackpotAmt1 = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(8) > td > table > tbody > tr:nth-child(1) > td:nth-child(2) > span > b > font");

            Elements el4DJackpotNumbers = doc.selectXpath("/html/body/div/center/table/tbody/tr[8]/td/table/tbody/tr[position() >= 2 and position() <= 3]/td");
            List<String> el4DJackpotNumbersText = el4DJackpotNumbers.eachText();

            Element el4DjackpotAmt2 = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(8) > td > table > tbody > tr:nth-child(4) > td:nth-child(2) > span > b > font");

            Toto4Djackpot toto4Djackpot = new Toto4Djackpot(getTextValue.apply(el4DjackpotAmt1.text(), " ", 1),
                                                            getTextValue.apply(el4DjackpotAmt2.text(), " ", 1),
                                                            el4DJackpotNumbers.eachText());


            Elements elsZodiacURL = doc.getElementsByClass("img-responsive");
            Attributes attrElZodiacUrl = elsZodiacURL.get(0).attributes();

            Element el4DZodiac1stPrize = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(10) > td > table > tbody > tr:nth-child(1) > td:nth-child(2) > span > b > font");
            Element el4DZodiac2ndPrize = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(10) > td > table > tbody > tr:nth-child(2) > td:nth-child(2) > span > b > font");
            Element el4DZodiac3rdPrize = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(10) > td > table > tbody > tr:nth-child(3) > td:nth-child(2) > span > b > font");
            Element el4DZodiac4thPrize = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(10) > td > table > tbody > tr:nth-child(4) > td:nth-child(2) > span > b > font");
            Element el4DZodiac5thPrize = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(10) > td > table > tbody > tr:nth-child(5) > td:nth-child(2) > span > b > font");
            Element el4DZodiac6thPrize = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(10) > td > table > tbody > tr:nth-child(6) > td:nth-child(2) > span > b > font");

            Toto4Dzodiac toto4Dzodiac = new Toto4Dzodiac(MAINVIEW_TOTO + attrElZodiacUrl.get("src"), el4DZodiac1stPrize.text(),
                                                         el4DZodiac2ndPrize.text(), el4DZodiac3rdPrize.text(),
                                                         el4DZodiac4thPrize.text(), el4DZodiac5thPrize.text(),
                                                         el4DZodiac6thPrize.text());

            Elements elsSupremeTotoNumber = doc.selectXpath("/html/body/div/center/table/tbody/tr[12]/td/table[1]/tbody/tr/td[2]/table[1]/tbody/tr/td");
            Element elSupremeTotoPrizeMoney = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(12) > td > table:nth-child(1) > tbody > tr > td:nth-child(2) > table:nth-child(2) > tbody > tr > td:nth-child(2) > span > b > font");

            SupremeToto supremeToto = new SupremeToto(getTextValue.apply(elSupremeTotoPrizeMoney.text(), " ", 1), elsSupremeTotoNumber.eachText());

            Elements elsPowerTotoNumber = doc.selectXpath("/html/body/div/center/table/tbody/tr[12]/td/table[2]/tbody/tr/td[2]/table[1]/tbody/tr/td");
            Element elPowerTotoPrizeMoney = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(12) > td > table:nth-child(2) > tbody > tr > td:nth-child(2) > table:nth-child(2) > tbody > tr > td:nth-child(2) > span > b > font");

            PowerToto powerToto = new PowerToto(getTextValue.apply(elPowerTotoPrizeMoney.text(), " ", 1), elsPowerTotoNumber.eachText());

            Elements elsStarTotoNumber = doc.selectXpath("/html/body/div/center/table/tbody/tr[12]/td/table[3]/tbody/tr/td[2]/table[1]/tbody/tr/td");
            Element elStarTotoPrizeMoney1 = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(12) > td > table:nth-child(3) > tbody > tr > td:nth-child(2) > table:nth-child(2) > tbody > tr:nth-child(1) > td:nth-child(2) > span > b > font");
            Element elStarTotoPrizeMoney2 = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(12) > td > table:nth-child(3) > tbody > tr > td:nth-child(2) > table:nth-child(2) > tbody > tr:nth-child(2) > td:nth-child(2) > span > b > font");
            StarToto starToto = new StarToto(getTextValue.apply(elStarTotoPrizeMoney1.text(), " ", 1),
                                             getTextValue.apply(elStarTotoPrizeMoney2.text(), " ", 1),
                                             elsStarTotoNumber.eachText());

            Elements elsToto5D1stPrize = doc.selectXpath("/html/body/div/center/table/tbody/tr[14]/td/table[1]/tbody/tr[1]/td[3]/table/tbody/tr");
            Elements elsToto5D2ndPrize = doc.selectXpath("/html/body/div/center/table/tbody/tr[14]/td/table[1]/tbody/tr[2]/td[2]/table/tbody/tr");
            Elements elsToto5D3rdPrize = doc.selectXpath("/html/body/div/center/table/tbody/tr[14]/td/table[1]/tbody/tr[3]/td[2]/table/tbody/tr");
            Elements elsToto5D4thPrize = doc.selectXpath("/html/body/div/center/table/tbody/tr[14]/td/table[1]/tbody/tr[1]/td[5]/table/tbody/tr");
            Elements elsToto5D5thPrize = doc.selectXpath("/html/body/div/center/table/tbody/tr[14]/td/table[1]/tbody/tr[2]/td[4]/table/tbody/tr");
            Elements elsToto5D6thPrize = doc.selectXpath("/html/body/div/center/table/tbody/tr[14]/td/table[1]/tbody/tr[3]/td[4]/table/tbody/tr");

            Toto5D toto5D = new Toto5D(elsToto5D1stPrize.eachText().stream().map(str -> str.replaceAll("\\s+", "")).collect(Collectors.joining()),
                                       elsToto5D2ndPrize.eachText().stream().map(str -> str.replaceAll("\\s+", "")).collect(Collectors.joining()),
                                       elsToto5D3rdPrize.eachText().stream().map(str -> str.replaceAll("\\s+", "")).collect(Collectors.joining()),
                                       elsToto5D4thPrize.eachText().stream().map(str -> str.replaceAll("\\s+", "")).collect(Collectors.joining()),
                                       elsToto5D5thPrize.eachText().stream().map(str -> str.replaceAll("\\s+", "")).collect(Collectors.joining()),
                                       elsToto5D6thPrize.eachText().stream().map(str -> str.replaceAll("\\s+", "")).collect(Collectors.joining()));

            Elements elsToto6D1stPrize = doc.selectXpath("/html/body/div/center/table/tbody/tr[14]/td/table[2]/tbody/tr[1]/td[3]/div/table/tbody/tr/td/div/center/table/tbody/tr/td");
            Elements elsToto6D2ndPrizes = doc.selectXpath("/html/body/div/center/table/tbody/tr[14]/td/table[2]/tbody/tr[2]/td[position() >= 2 and position() <=4]");
            Elements elsToto6D3rdPrizes = doc.selectXpath("/html/body/div/center/table/tbody/tr[14]/td/table[2]/tbody/tr[3]/td[position() >= 2 and position() <=4]");
            Elements elsToto6D4thPrizes = doc.selectXpath("/html/body/div/center/table/tbody/tr[14]/td/table[2]/tbody/tr[4]/td[position() >= 2 and position() <=4]");
            Elements elsToto6D5thPrizes = doc.selectXpath("/html/body/div/center/table/tbody/tr[14]/td/table[2]/tbody/tr[5]/td[position() >= 2 and position() <=4]");

            Toto6D toto6D = new Toto6D(elsToto6D1stPrize.eachText().stream().collect(Collectors.joining()),
                                       elsToto6D2ndPrizes.eachText().stream().filter(s -> !"or".equals(s))
                                            .map(str -> str.replaceAll("\\s+", ""))
                                            .collect(Collectors.toList()),
                                       elsToto6D3rdPrizes.eachText().stream().filter(s -> !"or".equals(s))
                                            .map(str -> str.replaceAll("\\s+", ""))
                                            .collect(Collectors.toList()),
                                       elsToto6D4thPrizes.eachText().stream().filter(s -> !"or".equals(s))
                                            .map(str -> str.replaceAll("\\s+", ""))
                                            .collect(Collectors.toList()),
                                       elsToto6D5thPrizes.eachText().stream().filter(s -> !"or".equals(s))
                                            .map(str -> str.replaceAll("\\s+", ""))
                                            .collect(Collectors.toList()));

            TotoResult totoResult = new TotoResult(drawDate, drawNo, toto4D, toto4Djackpot, toto4Dzodiac, supremeToto,
                powerToto, starToto, toto5D, toto6D);
            return totoResult;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getDateForDateDrawMapKey(String endpointDate) {
        int year = Integer.parseInt(endpointDate.substring(0, 4));
        int month = Integer.parseInt(endpointDate.substring(4, 6));
        int day = Integer.parseInt(endpointDate.substring(6));
        return String.format("%d/%d/%d", day, month, year);
    }

    private String getFixedDateForUrl(String endpointDate) {
        int year = Integer.parseInt(endpointDate.substring(0, 4));
        int month = Integer.parseInt(endpointDate.substring(4, 6));
        int day = Integer.parseInt(endpointDate.substring(6));

        return month + "/" + month + "/" + year;
    }

    private Map<String, String> getDateDrawMap(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();

        Elements els = doc.getElementsByClass("col-sm-4 col-xs-6");
        Map<String, String> dateDrawMap = new HashMap<>();
        for (Element element : els) {
            Element element1 = element.child(0);

            Elements sibEls = element.siblingElements();

            dateDrawMap.put(getTextValue.apply(element1.text(), ",", 0), getTextValue.apply(sibEls.get(1).text(), ": ", 1));

        }
        return dateDrawMap;
    }

    private boolean isValidDateFormat(String dateStr) {
        // Set the desired date format (YYYYMMDD)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        dateFormat.setLenient(false); // Set lenient to false for strict date validation

        try {
            // Parse the input date string
            dateFormat.parse(dateStr);
            return true; // Return true if parsing succeeds
        } catch (ParseException e) {
            return false; // Return false if parsing fails
        }
    }

    static TriFunction<String, String, Integer, String> getDrawHeaderDetailsTriFunction = (extractedText, splitChar, pos) -> extractedText.split(splitChar)[pos];

    static TriFunction<String, String, Integer, String> getTextValue= (extractedText, splitChar, pos) -> extractedText.split(splitChar)[pos];

}
