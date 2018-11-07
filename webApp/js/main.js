function loadCurrentData(){

    buildCurrentDataTable();
    buildDataManageTable();
}

function buildCurrentDataTable(){
  var table = document.getElementById("currentData");
  var host = configuration.general.host;
    for (var key in configuration.currentDataConfig){
      if(configuration.currentDataConfig.hasOwnProperty(key)){
        var currentDataConfig = configuration.currentDataConfig[key];
        insertCurrentDataRow(table, currentDataConfig.label, currentDataConfig.id);
        getData(host + currentDataConfig.dataSource, currentDataConfig.id, currentDataConfig.dataSourceVariable);
      }
    }
}

function buildDataManageTable(){
    var table = document.getElementById("manageData");
    var host = configuration.general.host;
    for(var key in configuration.dataManageConfig){
      if(configuration.dataManageConfig.hasOwnProperty(key)){
        var dataManageConfig = configuration.dataManageConfig[key];
        insertDataManageRow(table, dataManageConfig.label, dataManageConfig.id, host + dataManageConfig.dataConsumer)
      }
    }
}

function insertCurrentDataRow(table, name, valueId){
    table.insertRow();
    var lastRow = table.rows.length -1;
    table.rows[lastRow].insertCell().innerHTML = name;
    table.rows[lastRow].insertCell().setAttribute("id", valueId);
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
