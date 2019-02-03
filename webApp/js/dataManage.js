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
