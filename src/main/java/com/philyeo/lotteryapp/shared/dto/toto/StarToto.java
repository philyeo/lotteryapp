package com.philyeo.lotteryapp.shared.dto.toto;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@ToString
public class StarToto {

    private Optional<String> jackpot1Amt;

    private Optional<String> jackpot2Amt;

    private Optional<List<String>> winningNumbers;


}
