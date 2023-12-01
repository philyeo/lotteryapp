package com.philyeo.lotteryapp.shared.dto.toto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameResult {

    @NotBlank
    private Optional<String> drawDate;

    @NotBlank
    private Optional<String> drawNo;

    @NotBlank
    private GameType gameType;

    public Function<GameType, List<String>> getGameAttributes = e -> e.getAttributes();

    public GameResult(Optional<String> drawDate, Optional<String> drawNo, GameType gameType) {
        this.drawDate = drawDate;
        this.drawNo = drawNo;
        this.gameType = gameType;
    }

}
