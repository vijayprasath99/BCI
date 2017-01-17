import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class p300 extends Frame implements Runnable,KeyListener
{
	int currentFocusedbtn = 0;
	int numberOfButtons = 28;
	Button kbBtn[];
	TextArea output = new TextArea("",100,10,TextArea.SCROLLBARS_HORIZONTAL_ONLY);
	p300()
	{
		Thread eachSec = new Thread(this,"ChangeEachSecond");
		eachSec.setPriority(Thread.MAX_PRIORITY);
		eachSec.start();
		kbBtn = new Button[numberOfButtons];
		this.setSize(480,300);
		this.setLayout(null);
		int i,j;
		for(j=0;j<3;j++)
		{
			for(i=0;i<10;i++)
			{
				int index = i+(j*10);
				if(index>=26)
					break;
				char btnLabel = (char)(97+index);
				kbBtn[index] = new Button(Character.toString(btnLabel));
				kbBtn[index].setFont(new Font("Arial",Font.PLAIN,24));
				add(kbBtn[index]);
				kbBtn[index].setBounds((40*(i+1)),40*(j+1)+100,40,40);
				kbBtn[index].addKeyListener(this);
			}
		}
		kbBtn[26]=new Button("DEL");
		kbBtn[26].setFont(new Font("Arial",Font.PLAIN,24));
		add(kbBtn[26]);
		kbBtn[26].setBounds(280,220,80,40);
		kbBtn[27]=new Button("SPACE");
		kbBtn[27].setFont(new Font("Arial",Font.PLAIN,24));
		add(kbBtn[27]);
		kbBtn[27].setBounds(360,220,80,40);
		//output.setEditable(false);
		output.setBackground(new Color(240,240,240));
		output.setBounds(40,70,400,50);
		add(output);
		kbBtn[0].setForeground(Color.BLUE);
		output.addKeyListener(this);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
	}
	public void run()
	{
		try
		{
			while(true)
			{
				Thread.sleep(500);
				kbBtn[currentFocusedbtn].setForeground(Color.BLACK);
				currentFocusedbtn = (currentFocusedbtn+1)%numberOfButtons;
				kbBtn[currentFocusedbtn].setForeground(Color.BLUE);
			}
		}	
		catch(InterruptedException ie){
			System.out.println(ie);
		}
	}
	public void keyReleased(KeyEvent e)
	{
	}
	public void keyTyped(KeyEvent e)
	{}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 10)
		{
			if(kbBtn[currentFocusedbtn].getLabel() == "SPACE")
			{
				output.append(" ");
			}
			else if (kbBtn[currentFocusedbtn].getLabel() == "DEL")
			{
				output.setText(output.getText().substring(0,output.getText().length()-1));
			}
			else
			{
				output.append(kbBtn[currentFocusedbtn].getLabel());
			}
			//output.append(kbBtn[currentFocusedbtn].getText());
		}
    }
	public static void main(String str[])
	{
		new p300().setVisible(true);
	}
}
