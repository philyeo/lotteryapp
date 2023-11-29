package com.philyeo.lotteryapp.shared.mapper;

import com.philyeo.lotteryapp.shared.dto.magnum.MagnumResult;
import com.philyeo.lotteryapp.shared.dto.magnum.PastResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface Dto2DocumentFldMapper {

    MagnumResult dto2DocumentField(PastResult pastResult);

}
