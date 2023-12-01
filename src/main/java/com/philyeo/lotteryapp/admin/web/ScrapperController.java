package com.philyeo.lotteryapp.admin.web;

import com.philyeo.lotteryapp.admin.service.DamacaiScrapperService;
import com.philyeo.lotteryapp.admin.service.MagnumScrapperService;
import com.philyeo.lotteryapp.admin.service.TotoScrapperService;
import com.philyeo.lotteryapp.shared.enums.LotteryCompany;
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
    public void scrapdrawresult(@PathVariable("company") @NotNull final  String company,
        @RequestParam(name = "date", required = true) String date) throws IOException, ParseException { // DATE expected in YYYYMMDD format
        if(company.equals(LotteryCompany.DAMACAI.name())) {
            damacaiScrapperService.scrapDrawResult(date);
        } else if(company.equals(LotteryCompany.MAGNUM.name())) {
            magnumScrapperService.scrapDrawResult(date);
        } else if(company.equals(LotteryCompany.TOTO.name())) {
            totoScrapperService.scrapDrawResult(date);
        }
    }

}

