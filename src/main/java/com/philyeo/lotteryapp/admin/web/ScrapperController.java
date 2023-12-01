package com.philyeo.lotteryapp.admin.web;

import com.philyeo.lotteryapp.admin.service.DamacaiScrapperService;
import com.philyeo.lotteryapp.admin.service.MagnumScrapperService;
import com.philyeo.lotteryapp.admin.service.TotoScrapperService;
import com.philyeo.lotteryapp.shared.enums.LotteryCompany;
import com.philyeo.lotteryapp.shared.web.errors.LotteryAppException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.ParseException;

@Slf4j
@RestController
@AllArgsConstructor
public class ScrapperController {

    @Autowired
    private final DamacaiScrapperService damacaiScrapperService;

    @Autowired
    private final MagnumScrapperService magnumScrapperService;

    @Autowired
    private final TotoScrapperService totoScrapperService;

    @PostMapping("{company}/actions/scrap")
    @ResponseStatus(HttpStatus.OK)
    public void scrapdrawresultByDate(@PathVariable("company") @NotNull final  String company,
                                      @RequestParam(name = "date", required = false) String date,
                                      @RequestParam(name = "year", required = false) String year)
            throws IOException, ParseException { // DATE expected in YYYYMMDD format

        if ((date == null && year == null) || (date != null && year != null)) {
            throw new LotteryAppException("Missing parameters.", "0001", "Please provide either 'date' or 'year' parameter, not both or none.");
        }

        if(null!=date) {
            if (company.equals(LotteryCompany.DAMACAI.name())) {
                damacaiScrapperService.scrapDrawResultByDate(date);
            } else if (company.equals(LotteryCompany.MAGNUM.name())) {
                magnumScrapperService.scrapDrawResultByDate(date);
            } else if (company.equals(LotteryCompany.TOTO.name())) {
                totoScrapperService.scrapDrawResultByDate(date);
            }
        } else if(null!=year) {

        }
    }

}

