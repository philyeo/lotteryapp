package com.philyeo.lotteryapp.shared.dto.toto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class Toto6D {

    private Optional<String> firstPrize;

    private Optional<List<String>> secondPrize;

    private Optional<List<String>> thirdPrize;

    private Optional<List<String>> fourthPrize;

    private Optional<List<String>> fifthPrize;
}
