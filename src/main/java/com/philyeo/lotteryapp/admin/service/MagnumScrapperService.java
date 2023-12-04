package com.philyeo.lotteryapp.admin.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.philyeo.lotteryapp.shared.dto.magnum.*;
import com.philyeo.lotteryapp.shared.mapper.Dto2DocumentFldMapper;
import com.philyeo.lotteryapp.shared.persistance.document.MagnumResults;
import com.philyeo.lotteryapp.shared.persistance.repository.MagnumRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.philyeo.lotteryapp.shared.EndpointConstants.DRAWDATES_BY_YEAR;
import static com.philyeo.lotteryapp.shared.EndpointConstants.MAINVIEW_MAGNUM;

@Service
@Slf4j
public class MagnumScrapperService {

    private ObjectMapper objectMapper = new ObjectMapper();

    private Dto2DocumentFldMapper mapper;

    private MagnumRepository repository;

    public MagnumScrapperService(ObjectMapper objectMapper, Dto2DocumentFldMapper mapper, MagnumRepository repository) {
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        this.objectMapper = objectMapper;
        this.mapper = mapper;
        this.repository = repository;
    }

    public void scrapDrawResultByYear(String year) throws IOException, ParseException {
        for (int m = 1; m <= 12; m++) {
            //System.out.println(getPastDrawDatesByMonth("2023", Integer.toString(m)));
            DrawDatesByMonth drawDatesByMonth = getPastDrawDatesByMonth("2023", Integer.toString(m));
            List<Draw> draws = drawDatesByMonth.getPastDrawDates().getDraws();
            for(Draw draw: draws) {
                LocalDate date = LocalDate.parse(draw.getDrawDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                // Format the LocalDate object to the desired format "dd-MM-yyyy"
//                System.out.println(date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                scrapDrawResultByDate(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            }

        }
    }

    public void scrapDrawResultByDate(String date) throws ParseException {

        if(isValidDateFormat(date)) {
            String convertedDateFormat = convertDateFormat(date, 1);
            String added10daysDate = add10moreDays(convertedDateFormat);
            //"https://app-apdapi-prod-southeastasia-01.azurewebsites.net/results/past/between-dates/31-10-2023/10-11-2023/9";
            String initialUrl = MAINVIEW_MAGNUM + convertedDateFormat + "/" + added10daysDate + "/9";
            System.out.println(initialUrl);
            MagnumResult result = getDrawResult(initialUrl, convertDateFormat(date, 2));
            repository.insert(MagnumResults.builder()
                    .drawDate(result.getDrawDate().trim())
                    .result(result)
                .build());
        } else {
            //throw an error here
        }

    }

    private MagnumResult getDrawResult(String initialUrl, String date) {
        try {
            String url = initialUrl;
            URL obj = new URL(url);
            HttpURLConnection connection = getHttpURLConnection(obj);

            int responseCode = connection.getResponseCode();
//            System.out.println("Response Code: " + responseCode);

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
            List<PastResult> pastResults = magnumPastResults.getPastResultsRange().getPastResults();
            MagnumResult magnumResult = null;
            for(PastResult pastResult: pastResults) {
                if(pastResult.getDrawDate().equals(date)) {
                    magnumResult = mapper.dto2DocumentField(pastResult);
                }
            }
            System.out.println(magnumResult);
            return magnumResult;


        } catch (
        IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    private DrawDatesByMonth getPastDrawDatesByMonth(String year, String month) throws IOException {
        String endpointLink = DRAWDATES_BY_YEAR.replace("%y", year).replace("%m", month);
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
        DrawDatesByMonth drawDatesByMonth = objectMapper.readValue(res, DrawDatesByMonth.class);
        drawDatesByMonth.setMonth(month);
        drawDatesByMonth.setYear(year);
        return drawDatesByMonth;

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

    private String convertDateFormat(String dateStr, Integer formatType) throws ParseException {
        String formattedDate = "";
        SimpleDateFormat outputDateFormat = null;
        
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyyMMdd");
        if (formatType == 1) {
            outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        } else if (formatType ==2){
            outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        }

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

    private HttpURLConnection getHttpURLConnection(URL obj) throws IOException {

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
