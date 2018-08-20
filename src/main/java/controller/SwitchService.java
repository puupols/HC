package controller;

import java.util.HashMap;
import java.util.Map;

import database.DataBaseService;
import pojo.Switch;
import pojo.SwitchStatus;
import pojo.SwitchType;

public class SwitchService {
	
	private DataBaseService dataBaseService;
	private Map<SwitchType, Switch> switchMap = new HashMap<SwitchType, Switch>();
	
	public SwitchService(DataBaseService dataBaseService) {
		this.dataBaseService = dataBaseService;
	}
	
	public void storeSwitch(Switch receivedSwitch) {		
		switchMap.put(receivedSwitch.getType(), receivedSwitch);		
		if(getLastSwitch(receivedSwitch.getType()).getStatus() != receivedSwitch.getStatus()) {
			dataBaseService.saveSwitchStatus(receivedSwitch);		
		}
	}
	
	public Switch getLastSwitch(SwitchType type) {
		return switchMap.get(type);
	}
	
	public boolean isSwitchOn(SwitchType type) {
		return switchMap.get(type).getStatus() == SwitchStatus.ON;
	}
}
