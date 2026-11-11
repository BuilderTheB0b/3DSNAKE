#include <SPI.h>
#include <Ethernet.h>

byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
IPAddress ip(192, 168, 50, 50);
int localPort = 8888;
byte server[] = {192, 168, 50, 194};

// viktiga värden för Ethernet TCP connection
EthernetClient client; // definiera client för använding i loop

int direction;
const int buttonPins[] = {2, 3, 4, 5}; // array med pins för knapparna
const int numButtons = sizeof(buttonPins) / sizeof(buttonPins[0]);

void setup() {

  Ethernet.begin(mac, ip); // starta connection
  Serial.begin(9600); // startar monitor
  pinMode(9, OUTPUT);

  // definera pins som används med interna pullup-motstånd
  for (int i = 0; i < numButtons; i++) {
    pinMode(buttonPins[i], INPUT_PULLUP);
  }

  Serial.println("My IP: ");
  Serial.println(Ethernet.localIP());
  Serial.println("Connecting ..... ");

  if (client.connect(server, 6000)) {  // connectar till server ip genom port 6000
    Serial.println("Connected to Java server!");
  } else {
    Serial.println("Connection failed!");
  }

  // hanterar connection och visar text för användare
}

void loop() {

  int procent = 70; // hur stark pwm ska vara
  int z = (procent * 255)/100; // ändra värdet för pwm

  direction = 0; // nollställ direction varje loop

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
  if (direction != 0){
    Serial.print("Direction sent: ");
    Serial.println(direction);
    client.write(direction); // värdet som skickas till server

    delay(10);  // Liten paus för serverns svar

    if (client.available()) 
    {  // Check if server replied
        int returnedValue = client.read();  // Read response
        Serial.print("Received from server: ");
        Serial.println(returnedValue);
    } 
    else 
    {
        //Serial.println("No response from server.");
    }
  }

  delay(250); // för att undvika spam och bättre kunna läsa debug
}

void disconnect() {
  Serial.println();
  Serial.println("disconnecting.");
  client.stop();
  // metod ifall det behövs
}
