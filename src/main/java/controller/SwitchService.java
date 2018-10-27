package controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.DataBaseService;
import pojo.StatusCalculationType;
import pojo.Switch;
import pojo.SwitchStatus;
import pojo.SwitchType;

public class SwitchService {
	
	private DataBaseService dataBaseService;
	private ConfigurationService configurationService;
	private Map<SwitchType, Switch> switchMap = new HashMap<SwitchType, Switch>();
	private Logger logger = LoggerFactory.getLogger(SwitchService.class);
	
	public SwitchService(DataBaseService dataBaseService, ConfigurationService configurationService) {
		this.dataBaseService = dataBaseService;	
		this.configurationService = configurationService;
		createDefaultSwitches();
	}
	
	private void createDefaultSwitches(){
		for(SwitchType switchType : SwitchType.values()){
			Switch defaultSwitch = new Switch();			
			defaultSwitch.setStatus(SwitchStatus.ON);
			defaultSwitch.setType(switchType);
			StatusCalculationType calculationType = StatusCalculationType.valueOf(configurationService
					.getProperty("SWITCH_STATUS_CALCULATION_TYPE_" + switchType));
			defaultSwitch.setStatusCalculationType(calculationType);
			switchMap.put(defaultSwitch.getType(),defaultSwitch);			
		}
	}
	
	public void storeSwitch(Switch receivedSwitch) {
		Switch lastSwitch = getLastSwitch(receivedSwitch.getType());
		if(lastSwitch.getStatus() == null ||
				lastSwitch.getStatus() != receivedSwitch.getStatus()) {
			dataBaseService.saveSwitchStatus(receivedSwitch);		
		}
		lastSwitch.setStatus(receivedSwitch.getStatus());
		lastSwitch.setLogDate(receivedSwitch.getLogDate());
		logger.info("Switch stored in runtime map");
	}
	
	public Switch storeSwitchStatusCalculationType(Switch receivedSwitch){
		Switch lastSwitch = getLastSwitch(receivedSwitch.getType());
		lastSwitch.setStatusCalculationType(receivedSwitch.getStatusCalculationType());
		return lastSwitch;
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
