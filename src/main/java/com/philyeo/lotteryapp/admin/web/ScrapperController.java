package com.philyeo.lotteryapp.admin.web;

import com.philyeo.lotteryapp.admin.service.DamacaiScrapperService;
import com.philyeo.lotteryapp.shared.enums.LotteryCompany;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.NotNull;


import java.io.IOException;

@Slf4j
@RestController
@AllArgsConstructor
public class ScrapperController {

    @Autowired
    private final DamacaiScrapperService damacaiScrapperService;

    @PostMapping("{company}/actions/send")
    @ResponseStatus(HttpStatus.OK)
    public void scrapdrawresult(@PathVariable("company") @NotNull final  String company,
        @RequestParam(name = "date", required = true) String date) throws IOException { // DATE expected in YYYYMMDD format
        if(company.equals(LotteryCompany.DAMACAI.name())) {
            damacaiScrapperService.scrapDrawResult(date);
        }
    }

}

