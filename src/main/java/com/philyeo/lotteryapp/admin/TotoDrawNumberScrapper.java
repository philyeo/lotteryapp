package com.philyeo.lotteryapp.admin;

import com.philyeo.lotteryapp.shared.TriFunction;
import com.philyeo.lotteryapp.shared.dto.toto.DrawNumbersByYearTotoDto;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.philyeo.lotteryapp.shared.EndpointConstants.MONTHVIEW_TOTO;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.philyeo"})
public class TotoDrawNumberScrapper {

    public static void main(String[] args) {

        try {
            // all toto's link to get the dates are in the format
            // 11/11/2023 for nov 2023, where the day is a reflection of the month
            // thus april 2023 is 4/4/2023
            String year = "2022";

            DrawNumbersByYearTotoDto dto = new DrawNumbersByYearTotoDto();
            dto.setYear(year);
            for(int m = 1; m <=12; m++) {
                Map<String, String> map = getDateDrawNoMap(MONTHVIEW_TOTO + m + "/" + m + "/" + year);
                dto.addDrawNumbers(new ArrayList<>(map.values()));
                System.out.println(map);
            }

            System.out.println(dto);
//            System.out.println(getDateForDateDrawMapKey("20230107"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Map<String, String> getDateDrawNoMap(String url) throws IOException {

        Document doc = Jsoup.connect(url).get();

        Elements els = doc.getElementsByClass("col-sm-4 col-xs-6");
        Map<String, String> dateDrawMap = new HashMap<>();
        for (Element element : els) {
            Element element1 = element.child(0);

            Elements sibEls = element.siblingElements();

            // key is date in format dd/mm/yyyy
            // value is drawnumber in format e.g. 5622/23
            dateDrawMap.put(getTextValue.apply(element1.text(), ",", 0), getTextValue.apply(sibEls.get(1).text(), ": ", 1));

        }
        return dateDrawMap;
    }

    private static String getDateForDateDrawMapKey(String endpointDate) {
        int year = Integer.parseInt(endpointDate.substring(0, 4));
        int month = Integer.parseInt(endpointDate.substring(4, 6));
        int day = Integer.parseInt(endpointDate.substring(6));
        return String.format("%d/%d/%d", day, month, year);
    }

    static TriFunction<String, String, Integer, String> getTextValue= (extractedText, splitChar, pos) -> extractedText.split(splitChar)[pos];


}
