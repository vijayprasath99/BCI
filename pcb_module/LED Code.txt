#include<LPC214x.h>     // Define LPC2148 Header File
#define led  IOPIN1     // Define LED to Port1
#define tled IO1DIR     // Define Port1 as output
 
void delay(int x);
void main()
{
      PINSEL2 = 0x00000000; // Define port lines as GPIO
      tled = 0x00FF0000;    // Define P1.16 – P1.23 as O/P
      led = 0x00000000;     // Define P1.16 – P1.23 as zero
      while(1)              // Loop forever
      {
        led = 0x00FF0000;   // Turn ON P1.16 – P1.23
        delay(2000);
        led = 0x00000000;   // Turn OFF P1.16 – P1.23
        delay(2000);
      }
    }
 
void  delay(int x)
{
         unsigned  int  k,l;
      for(k = x;k > 0;k--)
        for(l = 0;l < x;l++);
}
