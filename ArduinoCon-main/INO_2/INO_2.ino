#include <SPI.h>
#include <Ethernet.h>

byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
IPAddress ip(10,0,0,3);
int localPort = 8888;
byte server[] = {10,0,0,2};

// viktiga värden för Ethernet TCP connection
EthernetClient client; // definiera client för använding i loop
const int LEDPin = 6;
int direction;
const int buttonPins[] = {2, 3, 8, 5}; // array med pins för knapparna
const int numButtons = sizeof(buttonPins) / sizeof(buttonPins[0]);
int previous_button=0;

void setup() {

  Ethernet.begin(mac, ip); // starta connection
  Serial.begin(9600); // startar monitor
  pinMode(9, OUTPUT);
  digitalWrite(9, LOW);

  // definera pins som används med interna pullup-motstånd
  for (int i = 0; i < numButtons; i++) {
    pinMode(buttonPins[i], INPUT_PULLUP);
  }

  Serial.println("My IP: ");
  Serial.println(Ethernet.localIP());
  Serial.println("Connecting ..... ");

  if (client.connect(server, 6001)) {  // connectar till server ip genom port 6000
    Serial.println("Connected to Java server!");
  } else {
    Serial.println("Connection failed!");
  }

  // hanterar connection och visar text för användare
}

void loop() 
{

  int procent = 70; // hur stark pwm ska vara
  int z = (procent * 255)/100; // ändra värdet för pwm
  direction = 0; // nollställ direction varje loop
  digitalWrite(9, LOW);
  // Debug: skriv ut alla knappars status
  /*for (int i = 0; i < numButtons; i++) {
    int btnState = digitalRead(buttonPins[i]);
    Serial.print("Button ");
    Serial.print(i);
    Serial.print(" (pin ");
    Serial.print(buttonPins[i]);
    Serial.print("): ");
    Serial.println(btnState == LOW ? "PRESSED" : "RELEASED");
  }*/

  // läs av knapparna – så fort en är tryckt, sätt riktning och bryt loopen
  for (int i = 0; i < numButtons; i++) {
    if (digitalRead(buttonPins[i]) == LOW) {
      direction = i + 1; // mappar knapp till direction (t.ex. knapp 0 => 1)
      break;
    }
  }
  

  // skicka endast värde om en knapp trycks
  if (direction != 0  &&  direction != previous_button){
    Serial.print("Direction sent: ");
    Serial.println(direction);
    client.write(direction); // värdet som skickas till server

    delay(10);  // Liten paus för serverns svar
    previous_button=direction;
  } else if (direction == 0) {
   previous_button = 0; // återställ när ingen knapp trycks
  }
    
      if (client.available()) 
      {  // Check if server replied
     
        int returnedValue = client.read();  // Read response
        Serial.print("Received from server: ");
        Serial.println(returnedValue);
        if(returnedValue == 6)
        {
          Serial.print("Tjemam");
          digitalWrite(9,HIGH);
          delay(100);
          digitalWrite(9,LOW);
        }
      }  

  delay(10); // för att undvika spam och bättre kunna läsa debug
  if(!client.connected()){
    Reconnect();
  }
}

void disconnect() {
  Serial.println();
  Serial.println("disconnecting.");
  client.stop();
  delay(1000);
   
}


void Reconnect(){
  Ethernet.begin(mac, ip);
  // metod ifall det behövs
  boolean reconnect = true;
  int i =0;
  while(reconnect){
    
    if(i<=30){
    client.connect(server,6001);
    Serial.println();
    Serial.println("reconnecting");
    delay(500);
    Serial.println(i);
    delay(250);
    if(client.connected()){
      reconnect=false;
      Serial.println("Reconnected");
    }else if(i>=30){
      reconnect=false;
    }
     
    
    }
    i++;
  }
  
}
