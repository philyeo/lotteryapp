package com.philyeo.lotteryapp.shared.dto.toto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TotoResult {

    private String drawDate;

    private String drawNumber;

    private Toto4D toto4D;

    private Toto4Djackpot toto4Djackpot;

    private Toto4Dzodiac toto4Dzodiac;

    private SupremeToto supremeToto;

    private PowerToto powerToto;

    private StarToto starToto;

    private Toto5D toto5D;

    private Toto6D toto6D;

}
