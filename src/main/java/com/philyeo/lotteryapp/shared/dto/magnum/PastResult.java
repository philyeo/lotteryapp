package com.philyeo.lotteryapp.shared.dto.magnum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PastResult {

    @JsonProperty("DrawDate")
    private String drawDate;
    @JsonProperty("DrawID")
    private String drawID;
    @JsonProperty("DrawDay")
    private String drawDay;
    @JsonProperty("DrawDayZh")
    private String drawDayZh;
    @JsonProperty("FirstPrize")
    private String firstPrize;
    @JsonProperty("SecondPrize")
    private String secondPrize;
    @JsonProperty("ThirdPrize")
    private String thirdPrize;
    @JsonProperty("Special1")
    private String special1;
    @JsonProperty("Special2")
    private String special2;
    @JsonProperty("Special3")
    private String special3;
    @JsonProperty("Special4")
    private String special4;
    @JsonProperty("Special5")
    private String special5;
    @JsonProperty("Special6")
    private String special6;
    @JsonProperty("Special7")
    private String special7;
    @JsonProperty("Special8")
    private String special8;
    @JsonProperty("Special9")
    private String special9;
    @JsonProperty("Special10")
    private String special10;
    @JsonProperty("Console1")
    private String console1;
    @JsonProperty("Console2")
    private String console2;
    @JsonProperty("Console3")
    private String console3;
    @JsonProperty("Console4")
    private String console4;
    @JsonProperty("Console5")
    private String console5;
    @JsonProperty("Console6")
    private String console6;
    @JsonProperty("Console7")
    private String console7;
    @JsonProperty("Console8")
    private String console8;
    @JsonProperty("Console9")
    private String console9;
    @JsonProperty("Console10")
    private String console10;
    @JsonProperty("Powerball1")
    private String powerball1;
    @JsonProperty("Powerball2")
    private String powerball2;
    @JsonProperty("LifeNum1")
    private String lifeNum1;
    @JsonProperty("LifeNum2")
    private String lifeNum2;
    @JsonProperty("LifeNum3")
    private String lifeNum3;
    @JsonProperty("LifeNum4")
    private String lifeNum4;
    @JsonProperty("LifeNum5")
    private String lifeNum5;
    @JsonProperty("LifeNum6")
    private String lifeNum6;
    @JsonProperty("LifeNum7")
    private String lifeNum7;
    @JsonProperty("LifeNum8")
    private String lifeNum8;
    @JsonProperty("LifeBonusNum1")
    private String lifeBonusNum1;
    @JsonProperty("LifeBonusNum2")
    private String lifeBonusNum2;
    @JsonProperty("Jackpot1Amount")
    private String jackpot1Amount;
    @JsonProperty("Jackpot2Amount")
    private String jackpot2Amount;
    @JsonProperty("Jackpot1Winner")
    private String jackpot1Winner;
    @JsonProperty("Jackpot2Winner")
    private String jackpot2Winner;
    @JsonProperty("GoldJackpot1Amount")
    private String goldJackpot1Amount;
    @JsonProperty("GoldJackpot2Amount")
    private String goldJackpot2Amount;
    @JsonProperty("GoldJackpot1Winner")
    private String goldJackpot1Winner;
    @JsonProperty("GoldJackpot2Winner")
    private String goldJackpot2Winner;
    @JsonProperty("PowerballJackpot1Amount")
    private String powerballJackpot1Amount;
    @JsonProperty("PowerballJackpot2Amount")
    private String powerballJackpot2Amount;
    @JsonProperty("PowerballJackpot1aWinner")
    private String powerballJackpot1aWinner;
    @JsonProperty("PowerballJackpot1bWinner")
    private String powerballJackpot1bWinner;
    @JsonProperty("PowerballJackpot1cWinner")
    private String powerballJackpot1cWinner;
    @JsonProperty("PowerballJackpot2Winner")
    private String powerballJackpot2Winner;
    @JsonProperty("LifePrize1Winner")
    private String lifePrize1Winner;
    @JsonProperty("LifePrize2Winner")
    private String lifePrize2Winner;
}
