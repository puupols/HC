function getData(request, elementId, resultElement){
    var xhttp = new XMLHttpRequest();
    var result;
    var value;
    xhttp.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            result = JSON.parse(this.responseText);            
            if(resultElement == "logDate"){
              value = new Date(result[resultElement]).toLocaleString();
            } else {
              value = result[resultElement];
            }
            document.getElementById(elementId).innerHTML = value;
        }
    };
    xhttp.open("GET", request, true);
    xhttp.send();
}

function saveData(request, elementId){
    var xhttp = new XMLHttpRequest();
    var finalRequest = request + document.getElementById(elementId).value;
    xhttp.onreadystatechange = function(){
      if(this.readyState == 4 && this.status == 200){
        location.reload();
      }
    }
    xhttp.open("GET", finalRequest, true);
    xhttp.send();
}
