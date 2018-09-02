package managers;

import org.springframework.beans.factory.annotation.Autowired;

import pojo.SwitchType;

public class SwitchManagerFactory {
	
	@Autowired
	HeaterSwitchManager heaterSwitchManager;
	
	public SwitchManager getSwitchManager(SwitchType switchType){		
		if(switchType == SwitchType.HEATER){
			return heaterSwitchManager;
		}		
		return null;
	}
}
