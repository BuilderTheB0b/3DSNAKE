#include <SPI.h>
#include <Ethernet.h>
#include <Adafruit_NeoPixel.h>
int latchPin = 12;
int clockPin = 11;
int dataPin = 10;
int ledPin = 7;
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
IPAddress ip(10, 0, 0, 3);
byte server[] = { 10, 0, 0, 2 };
byte x = 0;
byte y = 0;
byte z = 0;
// viktiga värden för Ethernet TCP connection
EthernetClient client;  // definiera client för använding i loop
int direction;
int ledIndex;
const int buttonPins[] = { 2, 3, 5, 6 };  // array med pins för knapparna
const int numButtons = sizeof(buttonPins) / sizeof(buttonPins[0]);
int previous_button = 0;

int rows[8] = { 0, 0, 0, 0, 0, 0, 0, 0 };

Adafruit_NeoPixel leds(64, ledPin, NEO_GRB);
int r = 0;
int g = 0;
int b = 0;


void setup() {

  Ethernet.begin(mac, ip);  // starta connection
  Serial.begin(9600);       // startar monitor

  pinMode(latchPin, OUTPUT);
  pinMode(clockPin, OUTPUT);
  pinMode(dataPin, OUTPUT);

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
  leds.begin();
  leds.clear();
  leds.show();
}

void loop() {
  inputTask();
  outputTask();
  if (!client.connected()) {
    Serial.println("Lost connection to server. Reconnecting...");
    reconnect();
    return;
  }
}


void inputTask() {
  for (int i = 0; i < numButtons; i++) {
    if (digitalRead(buttonPins[i]) == LOW) {
      direction = i + 1;  // mappar knapp till direction (t.ex. knapp 0 => 1)
      break;
    }
  }


  // skicka endast värde om en knapp trycks
  if (direction != 0 && direction != previous_button) {

    client.write(direction);  // värdet som skickas till server

    delay(1);  // Liten paus för serverns svar
    previous_button = direction;
  } else if (direction == 0) {
    previous_button = 0;  // återställ när ingen knapp trycks
  }

  delay(10);  // Run every 1 ms
}



void outputTask() {

  if (client.available() >= 1) {
    z = client.read();  // läser värden från server x= rad y= column = z typ
    y = client.read();
    x = client.read();


    if (x % 2 == 0) {
      ledIndex = (x * 8) + y;
    } else {
      ledIndex = ((x * 8) + 7) - y;
    }



    byte col = 1 << y;  // gör om till bytes för registern

    switch (z) {
      case 0:

        rows[x] &= ~col;
        r = 0;
        g = 0;
        b = 0;
        break;
      case 1:

        rows[x] |= col;
        r = 0;
        g = 255;
        b = 0;
        break;
      case 2:

        rows[x] |= col;
        r = 255;
        g = 0;
        b = 0;

        break;

      case 3:

        leds.clear();
        break;


      case 255:
        r = 0;
        g = 0;
        b = 0;
        lose();
        break;

    }  //  annorlunda logik baserat på vilken typ av "message"


    //updateShiftRegister()



    leds.setPixelColor(ledIndex, leds.Color(r, g, b));
    leds.show();

    Serial.println("Bytes: ");
    Serial.print(z);
    Serial.print(", ");
    Serial.print(y);
    Serial.print(", ");
    Serial.println(x);

    delay(10);
  }
}


void updateShiftRegister() {
  digitalWrite(latchPin, LOW);
  for (int i = 0; i < 8; i++) {
    shiftOut(dataPin, clockPin, MSBFIRST, rows[i]);
  }
  digitalWrite(latchPin, HIGH);  // uppdaterar alla registers och solenoiderna
}



void lose() {
  Serial.println("LOSER");
  // int losePattern[8] = { 0, 36, 36, 0, 60, 66, 0, 0 };

  // for (int i = 0; i < 8; i++) {
  //   rows[i] = losePattern[i];
  // }
  // ledIndex = 0;
  leds.clear();
  leds.setPixelColor(10, leds.Color(255, 0, 0));
  leds.setPixelColor(13, leds.Color(255, 0, 0));
  leds.setPixelColor(18, leds.Color(255, 0, 0));
  leds.setPixelColor(21, leds.Color(255, 0, 0));
  leds.setPixelColor(34, leds.Color(255, 0, 0));
  leds.setPixelColor(35, leds.Color(255, 0, 0));
  leds.setPixelColor(36, leds.Color(255, 0, 0));
  leds.setPixelColor(37, leds.Color(255, 0, 0));
  leds.setPixelColor(41, leds.Color(255, 0, 0));
  leds.setPixelColor(46, leds.Color(255, 0, 0));
  leds.show();
}

void disconnect() {
  Serial.println();
  Serial.println("disconnecting.");
  client.stop();
  delay(1000);
}


void reconnect() {
  Ethernet.begin(mac, ip);


  for (int i = 0; i <= 30; i++) {
    Serial.println("reconnecting");
    delay(100);
    Serial.println(i);
    delay(50);

    if (client.connect(server, 6001)) {
      Serial.println("Reconnected");
      break;
    } else if (i == 30) {
      Serial.println("failed to connect");
      disconnect();
    }
  }
}
