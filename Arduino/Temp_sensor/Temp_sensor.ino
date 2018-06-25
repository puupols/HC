#include <ESP8266HTTPClient.h>

#include <ESP8266WiFi.h>
#include <DHT.h>
#define DHTPIN D4
#define DHTTYPE DHT22

DHT dht(DHTPIN, DHTTYPE);


float temp;
float humidity;

const char* ssid = "OPTIC8C8D";
const char* password = "B1DC8C8D";
String serverAddress = "http://192.168.1.3:8080/storeTemperature?temperature=";


void setup() {

    Serial.begin(115200);    
    WiFi.begin(ssid, password);    
    while (WiFi.status() != WL_CONNECTED) {
      delay(500);
      Serial.println("Waiting to connect...");    
    }
    Serial.println(WiFi.localIP());  
    dht.begin();
}

void loop() {
  
  if(WiFi.status() == WL_CONNECTED){
    
    String url;
    float temperature = dht.readTemperature();
    String tempS = String(temperature);
    
    HTTPClient http;
    url = serverAddress + tempS;
    http.begin(url);  
    http.POST("Message from ESP8266");
    http.end();    
    }
  delay(30000);
  }
