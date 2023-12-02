package com.philyeo.lotteryapp.admin;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.philyeo.lotteryapp.shared.dto.magnum.DrawDatesByMonth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.philyeo.lotteryapp.shared.EndpointConstants.DRAWDATES_BY_YEAR;

public class MagnumDrawDatesByYearScrapper {

    private static ObjectMapper objectMapper = new ObjectMapper();


    public static void main(String[] args) throws IOException {
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        for (int m = 1; m <= 12; m++) {
            System.out.println(getPastDrawDatesByMonth("2023", Integer.toString(m)));
        }

    }

    //looks like this curl endpoint gets all the drawdates/drawno for the year
    //    curl 'https://app-apdapi-prod-southeastasia-01.azurewebsites.net/draw-dates/past/2021/12' \
    //        -H 'Accept: */*' \
    //        -H 'Accept-Language: en-GB,en-US;q=0.9,en;q=0.8' \
    //        -H 'Connection: keep-alive' \
    //        -H 'Origin: https://magnum4d.my' \
    //        -H 'Referer: https://magnum4d.my/' \
    //        -H 'Sec-Fetch-Dest: empty' \
    //        -H 'Sec-Fetch-Mode: cors' \
    //        -H 'Sec-Fetch-Site: cross-site' \
    //        -H 'User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36' \
    //        -H 'sec-ch-ua: "Google Chrome";v="119", "Chromium";v="119", "Not?A_Brand";v="24"' \
    //        -H 'sec-ch-ua-mobile: ?0' \
    //        -H 'sec-ch-ua-platform: "Windows"' \
    //        --compressed



    private static DrawDatesByMonth getPastDrawDatesByMonth(String year, String month) throws IOException {
        String endpointLink = DRAWDATES_BY_YEAR.replace("%y", year).replace("%m", month);
        URL obj = new URL(endpointLink);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        // Set request headers
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("Accept-Language", "en-GB,en-US;q=0.9,en;q=0.8");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Origin", "https://magnum4d.my");
        connection.setRequestProperty("Referer", "https://magnum4d.my/");
        connection.setRequestProperty("Sec-Fetch-Dest", "empty");
        connection.setRequestProperty("Sec-Fetch-Mode", "cors");
        connection.setRequestProperty("Sec-Fetch-Site", "cross-site");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36");
        connection.setRequestProperty("sec-ch-ua", "\"Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"");
        connection.setRequestProperty("sec-ch-ua-mobile", "?0");
        connection.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
        connection.setDoOutput(true);


        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String res = response.toString();
        DrawDatesByMonth drawDatesByMonth = objectMapper.readValue(res, DrawDatesByMonth.class);
        drawDatesByMonth.setMonth(month);
        drawDatesByMonth.setYear(year);
        return drawDatesByMonth;

    }
}
