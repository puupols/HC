package managers;

import pojo.SwitchType;

public class SwitchManagerFactory {
	SwitchManager switchManager;
	
	public SwitchManager getSwitchManager(SwitchType switchType){		
		if(switchType == SwitchType.HEATER){
			switchManager = new HeaterSwitchManager();
		}
		return switchManager;
	}
}
