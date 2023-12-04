package com.philyeo.lotteryapp.shared.dto.damacai;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Data
public class DrawDatesByYearDamacaiDto {
    private Map<String, List<String>> drawDatesByYear;

    private String year;
    private List<String> drawDates;  //each drawdate needs to be in the format of YYYYMMDD 20231115

    public DrawDatesByYearDamacaiDto() {
        this.drawDatesByYear = new HashMap<>();
    }

    public void addDrawDate(String date) {
        String year = date.substring(0, 4); // Extract year from date string

        // If the year is not already present in the map, create a new list for that year
        drawDatesByYear.computeIfAbsent(year, k -> new ArrayList<>());

        // Add the date to the list of that year
        drawDatesByYear.get(year).add(date);
    }

}
