function buildRerpotDataTable(){
  var table = document.getElementById("reportData");
  var host = configuration.general.host;
  createReportDataTableHeader(table);
  var fillReportDataTable = function fillReportDataTable(result){
    for(var key in result){
      table.insertRow();
      var lastRow = table.rows.length -1;
      table.rows[lastRow].insertCell().innerHTML = result[key].date;
      table.rows[lastRow].insertCell().innerHTML = result[key].onTime;
    }
  }
  getReportData(host + configuration.reportDataConfig.dataSource, fillReportDataTable);
}

function createReportDataTableHeader(table){
  table.insertRow();
  var lastRow = table.rows.length -1;
  for(var key in configuration.reportDataConfig){
    if(configuration.reportDataConfig.hasOwnProperty(key) && key != 'dataSource'){
      table.rows[lastRow].insertCell().innerHTML = configuration.reportDataConfig[key].label;
    }
  }
}
