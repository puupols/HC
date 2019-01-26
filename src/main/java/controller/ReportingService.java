package controller;

import database.DataBaseService;
import pojo.SwitchOnTime;

import java.util.List;

public class ReportingService {
    private DataBaseService dataBaseService;

    public ReportingService(DataBaseService dataBaseService){
        this.dataBaseService = dataBaseService;
    }

    public List<SwitchOnTime> getSwitchOnTime(){
        return dataBaseService.getSwtchOnTime();
    }


}
