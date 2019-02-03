function buildCurrentDataTable(){
  var table = document.getElementById("currentData");
  var host = configuration.general.host;
    for (var key in configuration.currentDataConfig){
      if(configuration.currentDataConfig.hasOwnProperty(key)){
        var currentDataConfig = configuration.currentDataConfig[key];
        insertCurrentDataRow(table, currentDataConfig.label, currentDataConfig.id);
        getOneAttributeData(host + currentDataConfig.dataSource, currentDataConfig.id, currentDataConfig.dataSourceVariable);
      }
    }
}

function insertCurrentDataRow(table, name, valueId){
    table.insertRow();
    var lastRow = table.rows.length -1;
    table.rows[lastRow].insertCell().innerHTML = name;
    table.rows[lastRow].insertCell().setAttribute("id", valueId);
}
