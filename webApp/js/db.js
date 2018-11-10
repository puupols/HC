function getData(request, elementId, value){
    var xhttp = new XMLHttpRequest();
    var result;    
    xhttp.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
            result = JSON.parse(this.responseText);
            document.getElementById(elementId).innerHTML = result[value];
        }
    };
    xhttp.open("GET", request, true);
    xhttp.send();
}

function saveData(request, elementId){
    var xhttp = new XMLHttpRequest();    
    var finalRequest = request + document.getElementById(elementId).value;
    xhttp.open("GET", finalRequest, true);
    xhttp.send();
}