#include<LPC214x.h>     // Define LPC2148 Header File
#include <stdio.h>
 
#define SW1    24    //SW1 (P1.24)
#define SW2    25    //SW2 (P1.25)
#define SW3    26    //SW3 (P1.26)
#define COIL_A 16    //change the Stepper Motor Port!
 
void motor_cw(void);
void motor_ccw(void);
void delay(int);
   
unsigned char STEP[] = {0x09, 0x08, 0x0C, 0x04, 0x06, 0x02, 0x03, 0x01};
void main(void)
{
       unsigned char i = 0;          
   PINSEL2 &= 0xFFFFFFF3; // P1.16 - P1.31 as GPIO
   IODIR1 = 0x000F0000;   // P1.16 - P1.19 as Output
  
   while(1)                           // Loop forever
   {
           if (!(IOPIN1 & (1<<SW1))) // Switch SW1 ON/OFF
      {
         motor_cw();            // Stepper Motor clockwise
           }
      else if (!(IOPIN1 & (1<<SW2)))// Switch SW2 ON/OFF
      {
         motor_ccw();     // Stepper Motor anticlockwise
           }
 
      else if (!(IOPIN1 & (1<<SW3)))// Switch SW3 ON/OFF
 {
             while (i < 12)
             {
            motor_cw (); // clockwise for req. angle
               i++;
            }  
     }
          else
         i = 0;
  }
}
 
void delay(int n)
{
   int i,j;
   for(i=0;i<n;i++)
   {
      for(j=0;j<0x3FF0;j++)
           {;}
   }
}
 
void motor_ccw(void)
{
   unsigned int i=0;
 
   while (STEP[i] != '\0')
   {
      IOSET1 = STEP[i] << COIL_A;
      delay(1);
      IOCLR1 = STEP[i] << COIL_A;
      delay(1);
      i++;
   }
}
   
void motor_cw(void)
{
   int i = 7;
 
   while (i >= 0)
   {
      IOSET1 = STEP[i] << COIL_A;
      delay(1);
      IOCLR1 = STEP[i] << COIL_A;
      delay(1);
      i--;
   }
}
 
