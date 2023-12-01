package com.philyeo.lotteryapp.shared.dto.toto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Toto4D {

    private String firstPrize;

    private String secondPrize;

    private String thirdPrize;

    private List<String> specialPrize;

    private List<String> consolationPrize;



    @Override
    public String toString() {
        return "Toto4D{" +
              "firstPrize=" + firstPrize +
              ", secondPrize=" + secondPrize +
              ", thirdPrize=" + thirdPrize +
              ", specialPrize=" + specialPrize +
              ", consolationPrize=" + consolationPrize +
              '}';
    }
}
