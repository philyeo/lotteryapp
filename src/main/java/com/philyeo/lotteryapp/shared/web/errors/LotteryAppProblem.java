package com.philyeo.lotteryapp.shared.web.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

@AllArgsConstructor
@Data
public class LotteryAppProblem extends AbstractThrowableProblem {

    private String title;
    private String detail;

    public LotteryAppProblem(Status status, String title, String detail) {
        super(null, title, status, detail, null, null, null);

        this.setTitle(title);
        this.setDetail(detail);
    }
}
