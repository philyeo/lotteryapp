package com.philyeo.lotteryapp.shared.dto.toto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Toto4Djackpot {

    private Optional<String> jackpot1Amt;

    private Optional<String> jackpot2Amt;

    private Optional<List<String>> numbers;

}
