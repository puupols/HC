package controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.DataBaseService;
import pojo.Switch;
import pojo.SwitchStatus;
import pojo.SwitchType;

public class SwitchService {
	
	private DataBaseService dataBaseService;
	private Map<SwitchType, Switch> switchMap = new HashMap<SwitchType, Switch>();
	private Logger logger = LoggerFactory.getLogger(SwitchService.class);
	
	public SwitchService(DataBaseService dataBaseService) {
		this.dataBaseService = dataBaseService;
	}
	
	public void storeSwitch(Switch receivedSwitch) {		
		if(getLastSwitch(receivedSwitch.getType()) == null ||
				getLastSwitch(receivedSwitch.getType()).getStatus() != receivedSwitch.getStatus()) {
			dataBaseService.saveSwitchStatus(receivedSwitch);		
		}
		switchMap.put(receivedSwitch.getType(), receivedSwitch);
		logger.info("Switch stored in runtime map");
	}
	
	public Switch getLastSwitch(SwitchType type) {
		return switchMap.get(type);
	}
	
	public boolean isSwitchOn(SwitchType type) {
		SwitchStatus currentStatus;		
		if(switchMap.get(type) == null) {
			logger.info("Switch status not received yet. Uses default value: ON");
			currentStatus = SwitchStatus.ON;
		} else {
			currentStatus = switchMap.get(type).getStatus();
		}
		return currentStatus == SwitchStatus.ON;
	}
}
