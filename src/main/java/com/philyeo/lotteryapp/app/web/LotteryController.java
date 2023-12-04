package com.philyeo.lotteryapp.app.web;

import com.philyeo.lotteryapp.app.service.ExtractorService;
import com.philyeo.lotteryapp.shared.dto.damacai.DamacaiResult;
import com.philyeo.lotteryapp.shared.dto.magnum.MagnumResult;
import com.philyeo.lotteryapp.shared.dto.toto.TotoResult;
import com.philyeo.lotteryapp.shared.persistance.document.DamacaiResults;
import com.philyeo.lotteryapp.shared.persistance.document.MagnumResults;
import com.philyeo.lotteryapp.shared.persistance.document.TotoResults;
import com.philyeo.lotteryapp.shared.web.errors.LotteryAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@RestController
public class LotteryController {

    private final ExtractorService extractorService;

    @Autowired
    public LotteryController(ExtractorService extractorService) {
        this.extractorService = extractorService;
    }

    @GetMapping("/TOTO/{drawDate}") //must be in the format YYYYMMDD
    public ResponseEntity<TotoResult> getTotoResultByDrawDate(@PathVariable String drawDate) throws ParseException {
        if (isValidDateFormat(drawDate)) {
            //then date needs to be converted to DD/MM/yyyy format before calling service
            TotoResults result = extractorService.getTotoResultsByDrawDate(convertDateFormat(drawDate, 2));
            if (result != null) {
                return ResponseEntity.ok(result.getResult());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            throw new LotteryAppException("Wrong format", "L0002", "Date not in YYYYMMDD format");
        }
    }

    @GetMapping("/MAGNUM/{drawDate}") //must be in the format YYYYMMDD
    public ResponseEntity<MagnumResult> getMagnumResultByDrawDate(@PathVariable String drawDate) throws ParseException {
        if (isValidDateFormat(drawDate)) {
            //then date needs to be converted to DD/MM/yyyy format before calling service
            MagnumResults result = extractorService.getMagnumResultsByDrawDate(convertDateFormat(drawDate, 2));
            if (result != null) {
                return ResponseEntity.ok(result.getResult());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            throw new LotteryAppException("Wrong format", "L0002", "Date not in YYYYMMDD format");
        }
    }

    @GetMapping("/DAMACAI/{drawDate}") //must be in the format YYYYMMDD
    public ResponseEntity<DamacaiResult> getDamacaiResultByDrawDate(@PathVariable String drawDate) throws ParseException {
        if (isValidDateFormat(drawDate)) {
            //then date needs to be converted to DD/MM/yyyy format before calling service
            DamacaiResults result = extractorService.getDamacaiResultsByDrawDate(convertDateFormat(drawDate, 2));
            if (result != null) {
                return ResponseEntity.ok(result.getResult());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            throw new LotteryAppException("Wrong format", "L0002", "Date not in YYYYMMDD format");
        }
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


}
