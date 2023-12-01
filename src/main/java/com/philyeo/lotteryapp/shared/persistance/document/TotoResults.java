package com.philyeo.lotteryapp.shared.persistance.document;

import com.philyeo.lotteryapp.shared.dto.magnum.MagnumResult;
import com.philyeo.lotteryapp.shared.dto.toto.TotoResult;
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
@Document(collection = "toto_results") // Specifies the MongoDB collection name
public class TotoResults
{
    @Id
    private String id;

    private String drawDate;
    private TotoResult result;

    public TotoResults(String drawDate, TotoResult result) {
        this.drawDate = drawDate;
        this.result = result;
    }

}
