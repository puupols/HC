var configuration = {
  'general': {
    'host': 'http://192.168.1.4:8080/'
  },
  'currentDataConfig': {
    'currentTemperature' : {
      'id' : 'currentTemperature',
      'label' : 'Current temperature',
      'dataSource' : 'getLastTemperature?type=MEASURED',
      'dataSourceVariable' : 'value'
    },
    'currentTemperatureDatatime' : {
      'id' : 'currentTemperatureDatatime',
      'label' : 'Last temperature datatime',
      'dataSource' : 'getLastTemperature?type=MEASURED',
      'dataSourceVariable' : 'logDate'
    },
    'desiredTemperatureDay' : {
      'id' : 'desiredTemperatureDay',
      'label' : 'Desired temperature Day',
      'dataSource' : 'getDesiredTemperature?dayPeriod=DAY',
      'dataSourceVariable' : 'value'
    },
    'desiredTemperatureNight' : {
      'id' : 'desiredTemperatureNight',
      'label' : 'Desired temperature Night',
      'dataSource' : 'getDesiredTemperature?dayPeriod=NIGHT',
      'dataSourceVariable' : 'value'
    },
    'heaterSwitchStatus' : {
      'id' : 'heaterSwitchStatus',
      'label' : 'Heater switch status',
      'dataSource' : 'getLastSwitch?type=HEATER',
      'dataSourceVariable' : 'status'
    }
  },
  'dataManageConfig': {
    'setDesiredTemperatureDay' : {
      'id' : 'setDesiredTemperatureDay',
      'label' : 'Desired temperature Day',
      'dataConsumer' : 'storeDesiredTemperature?dayPeriod=DAY&temperature='
    },
    'setDesiredTemperatureNight' : {
      'id' : 'setDesiredTemperatureNight',
      'label' : 'Desired temperature Night',
      'dataConsumer' : 'storeDesiredTemperature?dayPeriod=NIGHT&temperature='
    }
  },
  'reportDataConfig': {
    'dataSource' : 'getSwitchOnTime',
    'date' : {
      'label' : 'Date',
      'dataSource' : 'date'
    },
    'onTime' : {
      'label' : 'On time',
      'dataSource' : 'onTime'
    }    
  }
}
