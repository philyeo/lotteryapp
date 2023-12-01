package com.philyeo.lotteryapp.shared.dto.toto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Toto4Djackpot {

    private String jackpot1Amt;

    private String jackpot2Amt;

    private List<String> numbers;

}
