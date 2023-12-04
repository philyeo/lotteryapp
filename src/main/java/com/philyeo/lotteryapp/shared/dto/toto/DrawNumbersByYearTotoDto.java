package com.philyeo.lotteryapp.shared.dto.toto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
@ToString
public class DrawNumbersByYearTotoDto {
    private Map<String, List<String>> drawDatesByYear;

    private String year;
    private List<String> drawNumbers;  //each drawdate needs to be in the format of YYYYMMDD 20231115

    public DrawNumbersByYearTotoDto() {
        this.drawDatesByYear = new HashMap<>();
        this.drawNumbers = new ArrayList<>();
    }

    public void addDrawNumbers(List<String> additionalDrawNumbers) {
        this.drawNumbers.addAll(additionalDrawNumbers);
    }
}
