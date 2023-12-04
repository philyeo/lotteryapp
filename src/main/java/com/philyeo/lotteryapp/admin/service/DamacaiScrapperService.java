package com.philyeo.lotteryapp.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.philyeo.lotteryapp.shared.dto.damacai.DamacaiDrawResultResponse;
import com.philyeo.lotteryapp.shared.dto.damacai.DamacaiResult;
import com.philyeo.lotteryapp.shared.dto.damacai.DrawDateResult;
import com.philyeo.lotteryapp.shared.dto.damacai.DrawDatesByYearDamacaiDto;
import com.philyeo.lotteryapp.shared.persistance.document.DamacaiResults;
import com.philyeo.lotteryapp.shared.persistance.repository.DamacaiRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import static com.philyeo.lotteryapp.shared.EndpointConstants.DAMACAI_LIST_DATES;
import static com.philyeo.lotteryapp.shared.EndpointConstants.MAINVIEW_DAMACAI;

@Service
@AllArgsConstructor
public class DamacaiScrapperService {

    private static Logger logger = LoggerFactory.getLogger(DamacaiScrapperService.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    private DamacaiRepository repository;


    public void scrapDrawResultByYear(String year) throws IOException {
        List<String> drawDates = getDrawDates(year);

        for(String drawDate: drawDates) {
            scrapDrawResultByDate(drawDate);
        }
    }

    public void scrapDrawResultByDate(String date) throws IOException {

        if(isValidDateFormat(date)) {
            String initialUrl = MAINVIEW_DAMACAI + date;
            String innerLink = getDrawResultURL(initialUrl).getLink();

            DamacaiResult damacaiResult = objectMapper.readValue(getDrawResult(innerLink), DamacaiResult.class);

            repository.insert(DamacaiResults.builder()
                .drawDate(damacaiResult.getDrawDate().trim())
                .result(damacaiResult)
                .build());
        } else {
            //throw an error here
        }
    }

    private List<String> getDrawDates(String year) throws IOException {
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
        return drawDatesByYearMap.get(year);
    }

    private DamacaiDrawResultResponse getDrawResultURL(String initialUrl) {
        try {
            URL obj = new URL(initialUrl);
            HttpURLConnection con = getHttpURLConnection(obj);

            // Set the request method to GET
            con.setRequestMethod("GET");

            // Set request headers as specified in the curl command
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("accept-language", "en-GB,en-US;q=0.9,en;q=0.8");
            con.setRequestProperty("cookie", "_gid=GA1.3.244083715.1701090867; _ga_HMH99E1KTY=GS1.1.1701173012.3.1.1701173026.0.0.0; _ga=GA1.1.2134452614.1701090867");
            con.setRequestProperty("cookiesession", "403");
            con.setRequestProperty("referer", "https://www.damacai.com.my/past-draw-result/");
            con.setRequestProperty("sec-ch-ua", "\"Google Chrome\";v=\"119\", \"Chromium\";v=\"119\", \"Not?A_Brand\";v=\"24\"");
            con.setRequestProperty("sec-ch-ua-mobile", "?0");
            con.setRequestProperty("sec-ch-ua-platform", "\"Windows\"");
            con.setRequestProperty("sec-fetch-dest", "empty");
            con.setRequestProperty("sec-fetch-mode", "cors");
            con.setRequestProperty("sec-fetch-site", "same-origin");
            con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36");
            con.setRequestProperty("x-requested-with", "XMLHttpRequest");

            // Get the response code
            int responseCode = con.getResponseCode();

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return objectMapper.readValue(response.toString(), DamacaiDrawResultResponse.class);

        } catch (IOException e) {
            return null;
        }
    }


    private static String getDrawResult(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        String responseBody = response.toString();
        System.out.println("Response: " + responseBody);

        connection.disconnect();
        return  responseBody;
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
