package com.philyeo.lotteryapp.shared.dto.toto;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@ToString
public class SupremeToto {

    private Optional<String> jackpotAmt;

    private Optional<List<Integer>> winningNumbers;

}
