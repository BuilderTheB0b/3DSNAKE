#include <SPI.h>
#include <Ethernet.h>



byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
IPAddress ip(10, 0, 0, 5);
int localPort = 8888;
byte server[] = { 10, 0, 0, 1 };

//viktiga värden för Ethernet TCP connection

EthernetClient client; // definiera client för använding i loop



int direction;
int btns[] = {1,2,3,4}; // array med knappar som används



void setup() {

  Ethernet.begin(mac, ip); // starta connection
  Serial.begin(9600); // startar monitor
  pinMode(9, OUTPUT);
  pinMode(2, INPUT_PULLUP);
  pinMode(3, INPUT_PULLUP);
  pinMode(5, INPUT_PULLUP);
  pinMode(6, INPUT_PULLUP);

// definera pins som används
 



  Serial.println("My IP: ");
  Serial.println(Ethernet.localIP());
  Serial.println("Connecting ..... ");


  if (client.connect(server, 600)) {  // connectar till server ip genom port 600
    Serial.println("Connected to Java server!");

  } else {
    Serial.println("Connection failed!");
  }

  // hanterar connection och visar text för användare

}

void loop() {

  int procent = 70; // hur stark pwm ska vara
  int z = (procent * 255)/100; // ändra värdet för pwm

 

  btns[0] = digitalRead(2);
  btns[1] = digitalRead(3);
  btns[2] = digitalRead(5);
  btns[3] = digitalRead(6);
 
 // definera knapparna vilken pin de ska läsa



  Serial.println(z); // test för skriva ut pvm värde
  
 


if(btns[0] == LOW)analogWrite(9,z); direction = 1; // test för sylonoid
else if(btns[1]== LOW)direction = 2;
else if(btns[2]== LOW)direction = 3;
else if(btns[3]== LOW)direction = 4;

// ger direction värde



  if(direction != 0){
    Serial.println(direction);
    client.write(direction);
    // värdet esom skickas till server
  }

 delay(10); // viktig för buffer
 }



void disconnect() {

  Serial.println();
  Serial.println("disconnecting.");
  client.stop();
  //metod  ifall det behövs
}