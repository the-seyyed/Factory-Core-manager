package org.example.factory_core_manager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class MonthlyWorkedDetailedInfo {

    private float allAmount ;

    private Long allOverWorkedTime ;

    private Long allUnderWorkedTime ;

    private Long allOfficialWorkedTime ;

    private float allOverWorkedTimeCost ;

    private float allUnderWorkedTimeCost ;

    private float allOfficialWorkedTimeCost ;

    private float backGroundPayment ;

    private float marriagePayment ;

    private float havingChildrenPayment ;



    private List<DailyWorkedDetailedInfo> dailyWorkedDetailedInfoList ;





}
