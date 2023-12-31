package com.philyeo.lotteryapp.shared.dto.magnum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Draw {


    @JsonProperty("DrawDate")
    private String drawDate;
    @JsonProperty("DrawID")
    private String drawID;
    @JsonProperty("SpecialDraw")
    private String specialDraw;


}
