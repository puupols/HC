#include <ArduinoJson.h>


#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>

#define relay D1

const char* ssid = "OPTIC8C8D";
const char* password = "B1DC8C8D";
String url = "http://192.168.1.3:8080/getHeaterStatus";
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
  if(WiFi.status() == WL_CONNECTED){
    HTTPClient http;
    http.begin(url);
    int httpCode = http.GET();
    // httpCode will be negative on error
    if(httpCode > 0){
      response = http.getString();
      Serial.println(response);
      DynamicJsonBuffer jsonBuffer;
      JsonObject& root = jsonBuffer.parseObject(response);
      String HSstatus = root["status"];
      Serial.println(HSstatus);
      if(HSstatus == "OFF"){
        digitalWrite(relay, HIGH);
        Serial.println("HIGH");
        errorCount = 0;
        } else {
          digitalWrite(relay, LOW);
          Serial.println("LOW");
          errorCount = 0;
          };      
      } else {
        errorCount = errorCount + 1;
        Serial.println("Error reading server");
        } 
        
        if(errorCount > 3){
          digitalWrite(relay, LOW);
          Serial.println("Switch to emergency mode");
          }         
    http.end();
  } 
  delay(10000);
}
