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
public class PastResults {

    private String drawDate;
    private String drawID;
    private String drawDay;
    private String drawDayZh;

}
