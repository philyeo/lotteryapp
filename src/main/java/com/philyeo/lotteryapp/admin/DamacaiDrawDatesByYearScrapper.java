package com.philyeo.lotteryapp.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.philyeo.lotteryapp.shared.dto.damacai.DrawDateResult;
import com.philyeo.lotteryapp.shared.dto.damacai.DrawDatesByYearDamacaiDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static com.philyeo.lotteryapp.shared.EndpointConstants.DAMACAI_LIST_DATES;

public class DamacaiDrawDatesByYearScrapper {

    private static ObjectMapper objectMapper = new ObjectMapper();


    public static void main(String[] args) throws IOException {
        // https://www.damacai.com.my/ListPastResult
        // this endpoint is use by Damacai to store all the past drawdates
        // result is in space separate format

        String endpointLink = DAMACAI_LIST_DATES;
        URL obj = new URL(endpointLink);
        HttpURLConnection connection = getHttpURLConnection(obj);

        int responseCode = connection.getResponseCode();
        //        System.out.println("Response Code: " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String res = response.toString();
        System.out.println(res);

        DrawDateResult drawDateResult = objectMapper.readValue(res, DrawDateResult.class);
        System.out.println(drawDateResult);

        String datesString = drawDateResult.getDrawDate();

        DrawDatesByYearDamacaiDto dto = new DrawDatesByYearDamacaiDto();

        String[] dateStrings = datesString.split("\\s+");

        for(String dateString : dateStrings) {
            dto.addDrawDate(dateString);
        }

        // Accessing the stored draw dates by year
        Map<String, List<String>> drawDatesByYearMap = dto.getDrawDatesByYear();

        // Display the stored draw dates by year
        for (Map.Entry<String, List<String>> entry : drawDatesByYearMap.entrySet()) {
            System.out.println("Year: " + entry.getKey());
            System.out.println("Draw Dates: " + entry.getValue());
        }

    }

    private static HttpURLConnection getHttpURLConnection(URL obj) throws IOException {

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
        return connection;
    }

}
