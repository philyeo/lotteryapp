package com.philyeo.lotteryapp.admin;



import com.philyeo.lotteryapp.shared.TriFunction;
import com.philyeo.lotteryapp.shared.dto.toto.*;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.philyeo.lotteryapp.shared.EndpointConstants.MAINVIEW_TOTO;
import static com.philyeo.lotteryapp.shared.EndpointConstants.TEST_TOTO_NEW;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.philyeo"})
public class Java4DscrapperToto {

    private static Logger logger = LoggerFactory.getLogger(Java4DscrapperToto.class);

    static String drawDate;
    static String drawNumber;
    static String gameType1;
    static List<String> toto4Dtop3Results;
    static List<String> toto4DspecialPrizes;
    static List<String> toto4DconsolationPrizes;
    static String zodiacURL;


    public static void main(String[] args) {


        try {
            // Fetch and parse the HTML content from a URL
            Document doc = Jsoup.connect(TEST_TOTO_NEW).get();

            // Using CSS selectors to select specific elements
            Element el1 = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(2) > td > div > table > tbody > tr > td:nth-child(1) > span > font > b");
            String drawDate = getDrawHeaderDetailsTriFunction.apply(el1.childNode(0).toString(), ":", 1);

            System.out.println(drawDate);

            Elements el2 = doc.selectXpath("/html/body/div/center/table/tbody/tr[2]/td/div/table/tbody/tr/td[2]/span/b/font");
            String drawNo = getDrawHeaderDetailsTriFunction.apply(el2.get(0).childNode(0).toString(), "No.", 1);

            System.out.println(drawNo);

            Elements top3els = doc.selectXpath("/html/body/div/center/table/tbody/tr[5]/td/table[1]/tbody/tr[2]/td/span");
            List<String> top3PrizeText = top3els.eachText();

            //Elements elSpecialPrize = doc.selectXpath("/html/body/div/center/table/tbody/tr[5]/td/table[2]/tbody/tr");
            Elements elSpecialPrize = doc.selectXpath("/html/body/div/center/table/tbody/tr[5]/td/table[2]/tbody/tr[position()>1]");
            List<String> specialPrizeText = elSpecialPrize.eachText();

            //        Toto4D toto4D = new Toto4D(Optional.of(toto4Dtop3Results.get(0)), Optional.of(toto4Dtop3Results.get(1)), Optional.of(toto4Dtop3Results.get(2)),
            //                                    Optional.of(toto4DspecialPrizes), Optional.of(toto4DconsolationPrizes));
            List<String> allSpecialPrizeList = specialPrizeText.stream()
                .flatMap(str -> Arrays.stream(str.split("\\s+")))
                .collect(Collectors.toList());

            Elements elConsolationPrize = doc.selectXpath("/html/body/div/center/table/tbody/tr[5]/td/table[3]/tbody/tr[position()>1]");
            List<String> consolationPrizeText = elConsolationPrize.eachText();

            List<String> allConsolationPrizeList = consolationPrizeText.stream()
                .flatMap(str -> Arrays.stream(str.split("\\s+")))
                .collect(Collectors.toList());

            System.out.println(allSpecialPrizeList);
            System.out.println(allConsolationPrizeList);

            Toto4D toto4D = new Toto4D(Optional.of(top3PrizeText.get(0)), Optional.of(top3PrizeText.get(1)), Optional.of(top3PrizeText.get(2)),
                                        Optional.of(allSpecialPrizeList), Optional.of(allConsolationPrizeList));

            System.out.println(toto4D);


            Element el4DjackpotAmt1 = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(8) > td > table > tbody > tr:nth-child(1) > td:nth-child(2) > span > b > font");
            System.out.println(getTextValue.apply(el4DjackpotAmt1.text(), " ", 1));

            Elements el4DJackpotNumbers = doc.selectXpath("/html/body/div/center/table/tbody/tr[8]/td/table/tbody/tr[position() >= 2 and position() <= 3]/td");
            List<String> el4DJackpotNumbersText = el4DJackpotNumbers.eachText();
            System.out.println(el4DJackpotNumbers.eachText());

            Element el4DjackpotAmt2 = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(8) > td > table > tbody > tr:nth-child(4) > td:nth-child(2) > span > b > font");
            System.out.println(getTextValue.apply(el4DjackpotAmt2.text(), " ", 1));

            Toto4Djackpot toto4Djackpot = new Toto4Djackpot(Optional.of(getTextValue.apply(el4DjackpotAmt1.text(), " ", 1)),
                                                            Optional.of(getTextValue.apply(el4DjackpotAmt2.text(), " ", 1)),
                                                            Optional.of(el4DJackpotNumbers.eachText()));

            System.out.println(toto4Djackpot);

            Elements elsZodiacURL = doc.getElementsByClass("img-responsive");
            Attributes attrElZodiacUrl = elsZodiacURL.get(0).attributes();

            Element el4DZodiac1stPrize = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(10) > td > table > tbody > tr:nth-child(1) > td:nth-child(2) > span > b > font");
            Element el4DZodiac2ndPrize = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(10) > td > table > tbody > tr:nth-child(2) > td:nth-child(2) > span > b > font");
            Element el4DZodiac3rdPrize = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(10) > td > table > tbody > tr:nth-child(3) > td:nth-child(2) > span > b > font");
            Element el4DZodiac4thPrize = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(10) > td > table > tbody > tr:nth-child(4) > td:nth-child(2) > span > b > font");
            Element el4DZodiac5thPrize = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(10) > td > table > tbody > tr:nth-child(5) > td:nth-child(2) > span > b > font");
            Element el4DZodiac6thPrize = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(10) > td > table > tbody > tr:nth-child(6) > td:nth-child(2) > span > b > font");

            Toto4Dzodiac toto4Dzodiac = new Toto4Dzodiac(Optional.ofNullable(MAINVIEW_TOTO + attrElZodiacUrl.get("src")), Optional.ofNullable(el4DZodiac1stPrize.text()),
                                                         Optional.ofNullable(el4DZodiac2ndPrize.text()), Optional.ofNullable(el4DZodiac3rdPrize.text()),
                                                         Optional.ofNullable(el4DZodiac4thPrize.text()), Optional.ofNullable(el4DZodiac5thPrize.text()),
                                                         Optional.ofNullable(el4DZodiac6thPrize.text()));

            System.out.println(toto4Dzodiac);

            Elements elsSupremeTotoNumber = doc.selectXpath("/html/body/div/center/table/tbody/tr[12]/td/table[1]/tbody/tr/td[2]/table[1]/tbody/tr/td");
            Element elSupremeTotoPrizeMoney = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(12) > td > table:nth-child(1) > tbody > tr > td:nth-child(2) > table:nth-child(2) > tbody > tr > td:nth-child(2) > span > b > font");

            SupremeToto supremeToto = new SupremeToto(Optional.ofNullable(getTextValue.apply(elSupremeTotoPrizeMoney.text(), " ", 1)),
                                                      Optional.ofNullable(elsSupremeTotoNumber.eachText()));

            System.out.println(supremeToto);

            Elements elsPowerTotoNumber = doc.selectXpath("/html/body/div/center/table/tbody/tr[12]/td/table[2]/tbody/tr/td[2]/table[1]/tbody/tr/td");
            Element elPowerTotoPrizeMoney = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(12) > td > table:nth-child(2) > tbody > tr > td:nth-child(2) > table:nth-child(2) > tbody > tr > td:nth-child(2) > span > b > font");

            PowerToto powerToto = new PowerToto(Optional.ofNullable(getTextValue.apply(elPowerTotoPrizeMoney.text(), " ", 1)),
                                                Optional.ofNullable(elsPowerTotoNumber.eachText()));
            System.out.println(powerToto);

            Elements elsStarTotoNumber = doc.selectXpath("/html/body/div/center/table/tbody/tr[12]/td/table[3]/tbody/tr/td[2]/table[1]/tbody/tr/td");
            Element elStarTotoPrizeMoney1 = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(12) > td > table:nth-child(3) > tbody > tr > td:nth-child(2) > table:nth-child(2) > tbody > tr:nth-child(1) > td:nth-child(2) > span > b > font");
            Element elStarTotoPrizeMoney2 = doc.selectFirst("body > div > center > table > tbody > tr:nth-child(12) > td > table:nth-child(3) > tbody > tr > td:nth-child(2) > table:nth-child(2) > tbody > tr:nth-child(2) > td:nth-child(2) > span > b > font");
            StarToto starToto = new StarToto(Optional.ofNullable(getTextValue.apply(elStarTotoPrizeMoney1.text(), " ", 1)),
                                             Optional.ofNullable(getTextValue.apply(elStarTotoPrizeMoney2.text(), " ", 1)),
                                             Optional.ofNullable(elsStarTotoNumber.eachText()));
            System.out.println(starToto);

//            Elements elsToto5D1stPrize = doc.selectXpath("/html/body/div/center/table/tbody/tr[14]/td/table[1]/tbody/tr[1]/td[3]/table/tbody/tr/td");
            Elements elsToto5D1stPrize = doc.selectXpath("/html/body/div/center/table/tbody/tr[14]/td/table[1]/tbody/tr[1]/td[3]/table/tbody/tr");
            System.out.println(elsToto5D1stPrize.eachText());
            System.out.println(elsToto5D1stPrize.eachText().stream().map(str -> str.replaceAll("\\s+", "")).collect(Collectors.joining()));
//            System.out.println(elsToto5D1stPrize.eachText().stream().collect(Collectors.joining()));

        } catch (IOException e) {
            e.printStackTrace();
        }

//
//        SpringApplication.run(Java4DscrapperToto.class, args);
//
//        System.setProperty("webdriver.chrome.driver", "C:\\DevTools\\chromedriver_win32\\chromedriver.exe");
//
//        WebDriver driver = new ChromeDriver();
//
//        driver.get(TEST_TOTO_NEW);
//
//        List<WebElement> els1 = driver.findElements(By.cssSelector("div#popup_container * span.txt_black6"));
//
//        drawDate = getDrawHeaderDetailsFunction.apply(els1.get(0).getText(), 0);
//        drawNumber = els1.get(1).getText();
//
//        logger.info("drawDate: " + drawDate);
//        logger.info("drawNumber: " + drawNumber);
//
//        List<WebElement> els2 = driver.findElements(By.cssSelector("div#popup_container * div.col-sm-6 * td.txt_white5"));
//        gameType1 = els2.get(0).getText();
//
//        logger.info(gameType1);
//
//        toto4Dtop3Results = driver.findElements(By.cssSelector("div#popup_container * tr.txt_black2")).get(0).findElements(
//              By.cssSelector("td")).stream().map(e -> e.getText()).collect(Collectors.toList());
//
//        logger.info(toto4Dtop3Results.toString());
//
//        toto4DspecialPrizes = driver.findElements(By.cssSelector("div#popup_container * div.col-sm-6 > table > tbody > tr > td > table > tbody")).get(1)
//                                    .findElements(By.cssSelector("td")).stream()
//                                                                .map(e ->  e.getText())
//                                                                .filter(k -> !k.isBlank())
//                                                                .filter(k -> k.matches("[0-9]+"))
//                                                    .collect(Collectors.toList());
//
//
//        toto4DconsolationPrizes = driver.findElements(By.cssSelector("div#popup_container > div > div > div:nth-child(1) > table:nth-child(1) > "
//                    + "tbody > tr:nth-child(3) > td > table:nth-child(3) > tbody")).get(0)
//              .findElements(By.cssSelector("tr.txt_black2 > td"))
//              .stream()
//              .map(e -> e.getText())
//              .filter(k -> !k.isBlank())
//              .filter(k -> k.matches("[0-9]+"))
//              .collect(Collectors.toList());
//
//        logger.info(toto4DspecialPrizes.toString());
//        logger.info(toto4DconsolationPrizes.toString());
//
//        Toto4D toto4D = new Toto4D(Optional.of(toto4Dtop3Results.get(0)), Optional.of(toto4Dtop3Results.get(1)), Optional.of(toto4Dtop3Results.get(2)),
//                                    Optional.of(toto4DspecialPrizes), Optional.of(toto4DconsolationPrizes));
//
//        logger.info(toto4D.toString());
////        logger.info("special prizes: " + toto4D.getSpecialPrize().toString());
//
//
//        List<String> toto4dJackpotraw = driver.findElements(By.cssSelector("div#popup_container * div.col-sm-6 > table > tbody > tr > td > table > tbody")).get(3)
//                                        .findElements(By.cssSelector("td")).stream().map(e -> e.getText()).collect(Collectors.toList());
//
//        logger.info("toto4dJackpotraw: " + toto4dJackpotraw.toString());
//
//        Toto4Djackpot toto4Djackpot = new Toto4Djackpot(Optional.of(toto4dJackpotraw.get(1).replaceAll("\\s", "")),
//                                                        Optional.of(toto4dJackpotraw.get(9).replaceAll("\\s", "")),
//                                                        Optional.of(toto4dJackpotraw.subList(2, 8).stream()
//                                                              .map(e -> e.replaceAll("\\s", ""))
//                                                              .collect(Collectors.toList())));
//
//        logger.info(toto4Djackpot.toString());
//
//
//        zodiacURL = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(1) > table:nth-child(4) > tbody > "
//              + "tr:nth-child(1) > td.txt_black2.txt_left > span > img")).getAttribute("src");
//
//        String toto4Dzodiac1stPrize = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(1) > table:nth-child(4) >"
//              + " tbody > tr:nth-child(1) > td:nth-child(2)")).getText();
//        String toto4Dzodiac2ndPrize = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(1) > table:nth-child(4) >"
//              + " tbody > tr:nth-child(2) > td:nth-child(2)")).getText();
//        String toto4Dzodiac3rdPrize = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(1) > table:nth-child(4) >"
//              + " tbody > tr:nth-child(3) > td:nth-child(2)")).getText();
//        String toto4Dzodiac4thPrize = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(1) > table:nth-child(4) >"
//              + " tbody > tr:nth-child(4) > td:nth-child(2)")).getText();
//        String toto4Dzodiac5thPrize = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(1) > table:nth-child(4) >"
//              + " tbody > tr:nth-child(5) > td:nth-child(2)")).getText();
//        String toto4Dzodiac6thPrize = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(1) > table:nth-child(4) >"
//              + " tbody > tr:nth-child(6) > td:nth-child(2)")).getText();
//
//        Toto4Dzodiac toto4Dzodiac = new Toto4Dzodiac(Optional.ofNullable(zodiacURL), Optional.ofNullable(toto4Dzodiac1stPrize),
//                                                     Optional.ofNullable(toto4Dzodiac2ndPrize), Optional.ofNullable(toto4Dzodiac3rdPrize),
//                                                     Optional.ofNullable(toto4Dzodiac4thPrize), Optional.ofNullable(toto4Dzodiac5thPrize),
//                                                     Optional.ofNullable(toto4Dzodiac6thPrize));
//
//        logger.info(toto4Dzodiac.toString());
//
//
//        String supremeToto_jackpotAmt = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(2) > "
//              + "table:nth-child(2) > tbody > tr:nth-child(2) > td.txt_red1")).getText();
//        List<Integer> supremeToto_winningNumbers = driver.findElements(By.cssSelector("#popup_container > div > div > "
//              + "div:nth-child(2) > table:nth-child(2) > tbody > tr:nth-child(1) > td.txt_black2")).stream().map(e -> Integer.parseInt(e.getText())).collect(
//              Collectors.toList());
//
//        SupremeToto supremeToto = new SupremeToto(Optional.ofNullable(supremeToto_jackpotAmt), Optional.ofNullable(supremeToto_winningNumbers));
//        logger.info(supremeToto.toString());
//
//        String powerToto_jackpotAmt = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(2) > "
//              + "table:nth-child(3) > tbody > tr:nth-child(2) > td.txt_red1")).getText();
//
//        List<String> powerToto_winningNumbers = driver.findElements(By.cssSelector("#popup_container > div > div > "
//                                                                        + "div:nth-child(2) > table:nth-child(3) > tbody > tr:nth-child(1) > td.txt_black2")).stream()
//                                                        .map(e -> e.getText()).collect(Collectors.toList());
//
//        PowerToto powerToto = new PowerToto(Optional.ofNullable(powerToto_jackpotAmt), Optional.ofNullable(powerToto_winningNumbers));
//
//        logger.info(powerToto.toString());
//
//
//        String starToto_jackpot1Amt = driver.findElement(By.cssSelector("#popup_container > div > div > "
//              + "div:nth-child(2) > table:nth-child(4) > tbody > tr:nth-child(2) > td.txt_red1")).getText();
//
//        String starToto_jackpot2Amt = driver.findElement(By.cssSelector("#popup_container > div > div > "
//              + "div:nth-child(2) > table:nth-child(4) > tbody > tr:nth-child(3) > td.txt_red1")).getText();
//
//        List<String> starToto_winningNumbers = driver.findElements(By.cssSelector("#popup_container > div > div > div:nth-child(2) > "
//                                                            + "table:nth-child(4) > tbody > tr:nth-child(1) > td.txt_black2")).stream()
//                                                        .map(e -> e.getText()).collect(Collectors.toList());
//
//        StarToto starToto = new StarToto(Optional.ofNullable(starToto_jackpot1Amt),
//                                         Optional.ofNullable(starToto_jackpot2Amt),
//                                         Optional.ofNullable(starToto_winningNumbers));
//
//        logger.info(starToto.toString());
//
//        String toto5D_firstPrize = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(2) >"
//                                                        + " table:nth-child(6) > tbody > tr:nth-child(1) > td:nth-child(3)")).getText();
//
//        String toto5D_secondPrize = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(2) > "
//              + "table:nth-child(6) > tbody > tr:nth-child(2) > td:nth-child(2)")).getText();
//        String toto5D_thirdPrize = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(2) > "
//              + "table:nth-child(6) > tbody > tr:nth-child(3) > td:nth-child(2)")).getText();
//        String toto5D_fourthPrize = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(2) > "
//              + "table:nth-child(6) > tbody > tr:nth-child(1) > td:nth-child(5)")).getText();
//        String toto5D_fifthPrize = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(2) > "
//              + "table:nth-child(6) > tbody > tr:nth-child(2) > td:nth-child(4)")).getText();
//        String toto5D_sixthPrize = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(2) > "
//              + "table:nth-child(6) > tbody > tr:nth-child(3) > td:nth-child(4)")).getText();
//
//        Toto5D toto5D = new Toto5D(Optional.of(toto5D_firstPrize), Optional.of(toto5D_secondPrize), Optional.of(toto5D_thirdPrize),
//                                   Optional.of(toto5D_fourthPrize), Optional.of(toto5D_fifthPrize), Optional.of(toto5D_sixthPrize));
//
//        logger.info(toto5D.toString());
//
//        String toto6D_firstPrize = driver.findElement(By.cssSelector("#popup_container > div > div > div:nth-child(2) >"
//                                            + " table:nth-child(7) > tbody > tr:nth-child(1) > td.txt_black4")).getText();
//
//        List<String> toto6D_secondPrize = driver.findElements(By.cssSelector("#popup_container > div > div > div:nth-child(2) >"
//                                            + " table:nth-child(7) > tbody > tr:nth-child(2) > td.txt_black4")).stream()
//                    .map(e -> e.getText()).collect(Collectors.toList());
//
//        List<String> toto6D_thirdPrize = driver.findElements(By.cssSelector("#popup_container > div > div > div:nth-child(2) > "
//                    + "table:nth-child(7) > tbody > tr:nth-child(3) > td.txt_black4")).stream()
//              .map(e -> e.getText()).collect(Collectors.toList());
//
//        List<String> toto6D_fourthPrize = driver.findElements(By.cssSelector("#popup_container > div > div > div:nth-child(2) > "
//                    + "table:nth-child(7) > tbody > tr:nth-child(4) > td.txt_black4")).stream()
//              .map(e -> e.getText()).collect(Collectors.toList());
//
//        List<String> toto6D_fifthPrize = driver.findElements(By.cssSelector("#popup_container > div > div > div:nth-child(2) > "
//                    + "table:nth-child(7) > tbody > tr:nth-child(5) > td.txt_black4")).stream()
//              .map(e -> e.getText()).collect(Collectors.toList());
//
//        Toto6D toto6D = new Toto6D(Optional.of(toto6D_firstPrize),Optional.of(toto6D_secondPrize),Optional.of(toto6D_thirdPrize),
//                                   Optional.of(toto6D_fourthPrize),Optional.of(toto6D_fifthPrize));
//
//        logger.info(toto6D.toString());
//
//        TotoResult totoResult = new TotoResult(drawDate, drawNumber, toto4D, toto4Djackpot, toto4Dzodiac, supremeToto,
//                                                powerToto, starToto, toto5D, toto6D);
//
////        System.out.println(new Gson().toJson(totoResult));
//        logger.info(new Gson().toJson(totoResult));
//
//        driver.quit();

    }


    static BiFunction<String, Integer, String> getDrawHeaderDetailsBiFunction = (extractedText, pos) -> extractedText.split(",")[pos];

    static TriFunction<String, String, Integer, String> getDrawHeaderDetailsTriFunction = (extractedText, splitChar, pos) -> extractedText.split(splitChar)[pos];

    static TriFunction<String, String, Integer, String> getTextValue= (extractedText, splitChar, pos) -> extractedText.split(splitChar)[pos];



}
