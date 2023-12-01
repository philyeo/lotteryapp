package com.philyeo.lotteryapp.admin;

import com.philyeo.lotteryapp.shared.TriFunction;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.philyeo"})
public class TotoDrawNumberScrapper {

    public static void main(String[] args) {

        try {
            Document doc = Jsoup.connect("https://www.sportstoto.com.my/results_past.asp?date=11/11/2023").get();

            Elements els = doc.getElementsByClass("col-sm-4 col-xs-6");
//            System.out.println(els);
            Map<String, String> dateDrawMap = new HashMap<>();
            for (Element element: els) {
                Element element1 = element.child(0);
                System.out.println(element1.text());

                Elements sibEls = element.siblingElements();
                System.out.println(sibEls.eachText());
                System.out.println(sibEls.get(1).text());

                dateDrawMap.put(getTextValue.apply(element1.text(), ",", 0),
                                getTextValue.apply(sibEls.get(1).text(), ": ", 1));

            }

            System.out.println("");

//            System.out.println(dateDrawMap);

//            Elements els2 = doc.getElementsByClass("txt_black6");
//            System.out.println(els2);
            System.out.println(getDateForDateDrawMapKey("20230107"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String getDateForDateDrawMapKey(String endpointDate) {
        int year = Integer.parseInt(endpointDate.substring(0, 4));
        int month = Integer.parseInt(endpointDate.substring(4, 6));
        int day = Integer.parseInt(endpointDate.substring(6));
        return String.format("%d/%d/%d", day, month, year);
    }

    static TriFunction<String, String, Integer, String> getTextValue= (extractedText, splitChar, pos) -> extractedText.split(splitChar)[pos];


}
