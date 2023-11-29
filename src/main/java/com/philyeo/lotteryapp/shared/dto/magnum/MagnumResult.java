package com.philyeo.lotteryapp.shared.dto.magnum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class MagnumResult {
    private String drawDate;
    private String drawID;
    private String drawDay;
    private String drawDayZh;
    private String firstPrize;
    private String secondPrize;
    private String thirdPrize;
    private String special1;
    private String special2;
    private String special3;
    private String special4;
    private String special5;
    private String special6;
    private String special7;
    private String special8;
    private String special9;
    private String special10;
    private String console1;
    private String console2;
    private String console3;
    private String console4;
    private String console5;
    private String console6;
    private String console7;
    private String console8;
    private String console9;
    private String console10;
    private String powerball1;
    private String powerball2;
    private String lifeNum1;
    private String lifeNum2;
    private String lifeNum3;
    private String lifeNum4;
    private String lifeNum5;
    private String lifeNum6;
    private String lifeNum7;
    private String lifeNum8;
    private String lifeBonusNum1;
    private String lifeBonusNum2;
    private String jackpot1Amount;
    private String jackpot2Amount;
    private String jackpot1Winner;
    private String jackpot2Winner;
    private String goldJackpot1Amount;
    private String goldJackpot2Amount;
    private String goldJackpot1Winner;
    private String goldJackpot2Winner;
    private String powerballJackpot1Amount;
    private String powerballJackpot2Amount;
    private String powerballJackpot1aWinner;
    private String powerballJackpot1bWinner;
    private String powerballJackpot1cWinner;
    private String powerballJackpot2Winner;
    private String lifePrize1Winner;
    private String lifePrize2Winner;
}
