function loadCurrentData(){

    buildCurrentDataTable();    
    buildDataManageTable();
}

function buildCurrentDataTable(){
    var table = document.getElementById("currentData");
    insertCurrentDataRow(table, "Current temperature", "currentTemperature");
    getData("http://192.168.1.4:8080/getLastTemperature?type=MEASURED", "currentTemperature", "value"); 
    insertCurrentDataRow(table, "Last temperature datatime", "currentTemperatureDatatime");
    getData("http://192.168.1.4:8080/getLastTemperature?type=MEASURED", "currentTemperatureDatatime", "logDate");    
    insertCurrentDataRow(table, "Desired temperature Day", "desiredTemperatureDay");
    getData("http://192.168.1.4:8080/getDesiredTemperature?dayPeriod=DAY","desiredTemperatureDay", "value");    
    insertCurrentDataRow(table, "Desired temperature Night", "desiredTemperatureNight");
    getData("http://192.168.1.4:8080/getDesiredTemperature?dayPeriod=NIGHT","desiredTemperatureNight", "value");
    insertCurrentDataRow(table, "Heater switch status", "heaterSwitchStatus");
    getData("http://192.168.1.4:8080/getLastSwitch?type=HEATER","heaterSwitchStatus", "status");
}

function insertCurrentDataRow(table, name, valueId){    
    table.insertRow();
    var lastRow = table.rows.length -1;
    table.rows[lastRow].insertCell().innerHTML = name;
    table.rows[lastRow].insertCell().setAttribute("id", valueId);
}

function buildDataManageTable(){
    var table = document.getElementById("manageData");
    insertDataManageRow(table, "Desired temperature Day", "setDesiredTemperatureDay", "http://localhost:8080/storeDesiredTemperature?dayPeriod=DAY&temperature=");
    insertDataManageRow(table, "Desired temperature Night", "setDesiredTemperatureNight", "http://localhost:8080/storeDesiredTemperature?dayPeriod=NIGHT&temperature=");
}


function insertDataManageRow(table, name, valueId, buttonCall){    
    var inputNumber = createInputNumber(valueId);
    var button = createButton(buttonCall, valueId);        
    table.insertRow();
    var lastRow = table.rows.length -1;
    table.rows[lastRow].insertCell().innerHTML = name;
    table.rows[lastRow].insertCell().appendChild(inputNumber);
    table.rows[lastRow].insertCell().appendChild(button);
}

function createInputNumber(valueId){
    var num = document.createElement('input');
    num.type = "number";
    num.setAttribute("id", valueId);
    return num;
}

function createButton(buttonCall, valueId){
    var btn = document.createElement('input');
    btn.type = "button";
    btn.className = "btn";
    btn.value = "Send";
    btn.onclick = function(){saveData(buttonCall, valueId)};
    return btn;
}