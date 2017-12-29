package desu.fullStack.vm.memory.dev;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import desu.fullStack.vm.VM;
import desu.fullStack.vm.memory.Device;

public class DisplayDev implements Device {

	private int[] buffer = new int[6];
	private JFrame frame;
	private JPanel panel;
	private Timer timer;
	private BufferedImage imageBuffer;
	
	@Override
	public int getBase() {
		return 1030;
	}

	@Override
	public int getSize() {
		return 6;
	}

	@Override
	public int get(int address) {
		return 0;
	}

	@Override
	public void set(int address, int value) {
		if(address != 0)
			buffer[address] = value;
		else
			process(VM.fi(value));
	}

	private void process(int op) {
		switch (op) {
			case 0: //Init
				imageBuffer = new BufferedImage(VM.fi(buffer[1]), VM.fi(buffer[2]), BufferedImage.TYPE_INT_RGB);
				
				frame = new JFrame();
				panel = new JPanel();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				panel.setPreferredSize(new Dimension(VM.fi(buffer[1]), VM.fi(buffer[2])));
				frame.getContentPane().add(panel);
				frame.setResizable(false);
				frame.pack();
				frame.setVisible(true);
				timer = new Timer(10, (e)->{
					Graphics g = panel.getGraphics();
					g.drawImage(imageBuffer, 0, 0, null);
				});
				timer.start();
				break;
			case 1: //draw pixel
				imageBuffer.setRGB(VM.fi(buffer[1]), VM.fi(buffer[2]), 
						new Color(VM.f(buffer[3]), VM.f(buffer[4]), VM.f(buffer[5])).getRGB()
				);

		default:
			break;
		}
		
	}
	
	

}
