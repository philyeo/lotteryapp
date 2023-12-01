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
public class Toto6D {

    private String firstPrize;

    private List<String> secondPrize;

    private List<String> thirdPrize;

    private List<String> fourthPrize;

    private List<String> fifthPrize;
}
