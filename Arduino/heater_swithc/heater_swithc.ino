#include <ArduinoJson.h>


#include <ESP8266HTTPClient.h>
#include <ESP8266WiFi.h>

#define relay D1

const char* ssid = "OPTIC8C8D";
const char* password = "B1DC8C8D";
String url = "http://192.168.1.3:8080/getHeaterStatus";


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

  if(WiFi.status() == WL_CONNECTED){
    HTTPClient http;
    http.begin(url);
    http.GET();
    String response = http.getString();
    http.end();
    Serial.println(response);
    DynamicJsonBuffer jsonBuffer;
    JsonObject& root = jsonBuffer.parseObject(response);
    String HSstatus = root["status"];
    Serial.println(HSstatus);
    if(HSstatus == "OFF"){
      digitalWrite(relay, HIGH);
      Serial.println("HIGH");
      } else {
        digitalWrite(relay, LOW);
        Serial.println("LOW");
        };

  } 
  delay(10000);
}
