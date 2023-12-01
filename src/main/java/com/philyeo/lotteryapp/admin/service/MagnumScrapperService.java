package com.philyeo.lotteryapp.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.philyeo.lotteryapp.shared.dto.magnum.MagnumPastResults;
import com.philyeo.lotteryapp.shared.dto.magnum.MagnumResult;
import com.philyeo.lotteryapp.shared.mapper.Dto2DocumentFldMapper;
import com.philyeo.lotteryapp.shared.persistance.document.MagnumResults;
import com.philyeo.lotteryapp.shared.persistance.repository.MagnumRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.philyeo.lotteryapp.shared.EndpointConstants.MAINVIEW_MAGNUM;

@Service
@AllArgsConstructor
@Slf4j
public class MagnumScrapperService {

    private ObjectMapper objectMapper = new ObjectMapper();

    private Dto2DocumentFldMapper mapper;

    private MagnumRepository repository;

    public void scrapDrawResultByDate(String date) throws ParseException {

        if(isValidDateFormat(date)) {
            String convertedDateFormat = convertDateFormat(date);
            String added10daysDate = add10moreDays(convertedDateFormat);
            //"https://app-apdapi-prod-southeastasia-01.azurewebsites.net/results/past/between-dates/31-10-2023/10-11-2023/9";
            String initialUrl = MAINVIEW_MAGNUM + convertedDateFormat + "/" + added10daysDate + "/9";

            repository.insert(MagnumResults.builder()
                    .drawDate(date)
                    .result(getDrawResult(initialUrl))
                .build());
//            log.debug(getDrawResult(initialUrl));

//            DamacaiResult damacaiResult = objectMapper.readValue(getDrawResult(innerLink), DamacaiResult.class);

//            repository.insert(DamacaiResults.builder().drawDate(date).result(damacaiResult).build());
        } else {
            //throw an error here
        }

    }

    private MagnumResult getDrawResult(String initialUrl) {
        try {
            String url = initialUrl;
            URL obj = new URL(url);
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
            // Print the response
//            System.out.println(response.toString());
            MagnumPastResults magnumPastResults = objectMapper.readValue(res, MagnumPastResults.class);
            return mapper.dto2DocumentField(magnumPastResults.getPastResultsRange().getPastResults().get(0));


        } catch (
        IOException e) {
            return null;
        }
    }

    private boolean isValidDateFormat(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        dateFormat.setLenient(false);

        try {
            // Parse the input date string
            Date parsedDate = dateFormat.parse(dateStr);

            // Check if the parsed date matches the input date string
            if (parsedDate != null && dateFormat.format(parsedDate).equals(dateStr)) {
                return true; // Return true if parsing succeeds and the date matches the format
            } else {
                return false; // Return false if parsing succeeds but the date doesn't match the format
            }
        } catch (ParseException e) {
            return false; // Return false if parsing fails
        }
    }

    private String convertDateFormat(String dateStr) throws ParseException {
        String formattedDate = "";

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date date = inputDateFormat.parse(dateStr);
        return outputDateFormat.format(date);
    }

    private String add10moreDays(String dateStr) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = dateFormat.parse(dateStr);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 10);

        Date newDate = calendar.getTime();
        return dateFormat.format(newDate);
    }

}
