
#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>

#define relay D1

const char* ssid = "OPTIC8C8D";
const char* password = "B1DC8C8D";
String url = "http://192.168.1.5:8080/shouldSwitchBeOn?type=HEATER";
String url_on = "http://192.168.1.5:8080/storeSwitch?status=ON&type=HEATER";
String url_off = "http://192.168.1.5:8080/storeSwitch?status=OFF&type=HEATER";
int errorCount;
String switchPossition;
String  response;

void setup() {
  Serial.begin(115200);    
  pinMode(relay, OUTPUT);  
  WiFi.begin(ssid, password);    
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.println("Waiting to connect...");    
  }
  Serial.println(WiFi.localIP());  

}

void loop() {
  response = "";
  if(WiFi.status() != WL_CONNECTED){
    WiFi.begin(ssid, password);
    delay(500);
  } else {
    HTTPClient http;
    http.begin(url);
    int httpCode = http.GET();
    // httpCode will be negative on error
    if(httpCode > 0){
      response = http.getString();
      http.end();
      Serial.println(response);            
      if(response == "false"){
        digitalWrite(relay, HIGH);
        Serial.println("HIGH");
        http.begin(url_off);
        http.GET();
        http.end();
        errorCount = 0;
        } else {
          digitalWrite(relay, LOW);
          Serial.println("LOW");
          http.begin(url_on);
          http.GET();
          http.end();
          errorCount = 0;
          };      
      } else {
        http.end();
        errorCount = errorCount + 1;
        Serial.println("Error reading server");
        } 
        
        if(errorCount > 3){
          digitalWrite(relay, LOW);
          Serial.println("Switch to emergency mode");
          }         
    
  } 
  delay(10000);
}
