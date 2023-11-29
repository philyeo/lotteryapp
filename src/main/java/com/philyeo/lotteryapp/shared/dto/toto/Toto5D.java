package com.philyeo.lotteryapp.shared.dto.toto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class Toto5D {

    private Optional<String> firstPrize;

    private Optional<String> secondPrize;

    private Optional<String> thirdPrize;

    private Optional<String> fourthPrize;

    private Optional<String> fifthPrize;

    private Optional<String> sixthPrize;

}
