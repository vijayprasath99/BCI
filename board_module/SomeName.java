import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class SomeName extends Frame implements Runnable,KeyListener
{
	int currentFocusedbtn = 0; //Char displayed
	int numberOfButtons = 28;
	int start=0,end=0; //row limits array indexing
	int delay=500;//thread delay
	Button kbBtn[];
	Boolean rowSelection = true;//row or letter select
	int rowSelected =-1;//out of three which
	TextArea output = new TextArea("",100,10,TextArea.SCROLLBARS_HORIZONTAL_ONLY);//SCROLLBARS_NONE
	
        SomeName()
	{
		Thread eachSec = new Thread(this,"ChangeEachSecond");
		eachSec.setPriority(Thread.MAX_PRIORITY);
		eachSec.start();
		kbBtn = new Button[numberOfButtons];
		this.setSize(480,300);
		this.setLayout(null);
		int i,j;
		for(j=0;j<3;j++)// Button placement
		{
			for(i=0;i<10;i++)
			{
				int index = i+(j*10);//2D to 1D indexing i=col j=row
				if(index>=26)//del n space
					break;
				char btnLabel = (char)(97+index);
				kbBtn[index] = new Button(Character.toString(btnLabel));
				kbBtn[index].setFont(new Font("Arial",Font.PLAIN,24));
				add(kbBtn[index]);
				kbBtn[index].setBounds((40*(i+1)),40*(j+1)+100,40,40);//Fix pos
				kbBtn[index].addKeyListener(this);
			}
		}
                
		//Del n Space 
                kbBtn[26]=new Button("DEL");
		kbBtn[26].setFont(new Font("Arial",Font.PLAIN,24));
		add(kbBtn[26]);
		kbBtn[26].setBounds(280,220,80,40);
		kbBtn[27]=new Button("SPACE");
		kbBtn[27].setFont(new Font("Arial",Font.PLAIN,24));
		add(kbBtn[27]);
		kbBtn[27].setBounds(360,220,80,40);
	
                output.setEditable(false);//disable normal typing
		output.setBounds(40,70,400,50);
		add(output);
		output.addKeyListener(this);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);//Application Exit
			}
		});
	}
	public void run()
	{
		try
		{
			
			while(true)
			{
				Thread.sleep(delay);
				if(rowSelection)
				{
					rowSelected=(rowSelected+1)%3;
					for(int i=start;i<end;i++)
					{
						kbBtn[i].setForeground(Color.BLACK);
					}
					switch(rowSelected)
					{
						case 0:start=0;end=10;break;
						case 1:start=10;end=20;break;
						case 2:start=20;end=28;break;
					}
					for(int i=start;i<end;i++)
					{
						kbBtn[i].setForeground(Color.BLUE);
					}
				}
				else
				{
					for(int i=start;i<end;i++)
					{
						kbBtn[i].setForeground(Color.BLACK);
					}
					if(currentFocusedbtn < (end-1))//row looping
					{
						currentFocusedbtn++;
					}
					else
					{
						currentFocusedbtn=start;//last button in row
					}
					kbBtn[currentFocusedbtn].setForeground(Color.BLUE);
				}				
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
		if(rowSelection)
		{
			switch(rowSelected)
			{
				case 0:start=0;end=10;break;
				case 1:start=10;end=20;break;
				case 2:start=20;end=28;break;
			}
			currentFocusedbtn=start;
			rowSelection=false;
		}
		else{
			if(e.getKeyCode() == 10)
			{
				
				if(kbBtn[currentFocusedbtn].getLabel() == "SPACE")
				{
					output.append(" ");
				}
				else if (kbBtn[currentFocusedbtn].getLabel() == "DEL")
				{
					if(output.getText().length() > 0)
					{
						output.setText(output.getText().substring(0,output.getText().length()-1));
					}
				}
				else
				{
					output.append(kbBtn[currentFocusedbtn].getLabel());
				}
                                rowSelection=true;
				//output.append(kbBtn[currentFocusedbtn].getText());
			}
		}
    }
	public static void main(String str[])
	{
		new SomeName().setVisible(true);
	}
}