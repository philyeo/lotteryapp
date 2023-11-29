package com.philyeo.lotteryapp.shared.persistance.document;

import com.philyeo.lotteryapp.shared.dto.magnum.MagnumResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "magnum_results") // Specifies the MongoDB collection name
public class MagnumResults {
    @Id
    private String id;

    private String drawDate;
    private MagnumResult result;

    public MagnumResults(String drawDate, MagnumResult result) {
        this.drawDate = drawDate;
        this.result = result;
    }
}
