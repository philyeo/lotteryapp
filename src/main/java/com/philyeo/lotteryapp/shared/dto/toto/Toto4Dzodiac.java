package com.philyeo.lotteryapp.shared.dto.toto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Toto4Dzodiac {

    private String zodiacImgUrl;

    private String firstPrize;

    private String secondPrize;

    private String thirdPrize;

    private String fourthPrize;

    private String fifthPrize;

    private String sixthPrize;

}
