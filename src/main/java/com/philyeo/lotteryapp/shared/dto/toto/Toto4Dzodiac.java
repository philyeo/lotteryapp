package com.philyeo.lotteryapp.shared.dto.toto;

import lombok.*;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Toto4Dzodiac {

    private Optional<String> zodiacImgUrl;

    private Optional<String> firstPrize;

    private Optional<String> secondPrize;

    private Optional<String> thirdPrize;

    private Optional<String> fourthPrize;

    private Optional<String> fifthPrize;

    private Optional<String> sixthPrize;

}
