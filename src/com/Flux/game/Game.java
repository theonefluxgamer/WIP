package com.Flux.game;
 
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.Flux.game.graphics.Screen;
import com.Flux.game.input.Keyboard;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 11;
	
	public static int width = 300;
	public static int height = 168;
	public static int scale = 3;
	public static String title = "WIP";

	private Thread thread;
	private JFrame Frame;
	private Keyboard key;
	private boolean running = false;
	
	private Screen screen;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		
		screen = new Screen(width, height);
		Frame = new JFrame();
		key = new Keyboard();
		
		addKeyListener(key);
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try{
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
	}
	}
	
	public void run(){
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; 
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now-lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " ups, " + frames + "fps");
				Frame.setTitle(title + "  |  " + frames + "fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	int x = 0, y = 0;
	public void update() {	
		key.update();
		if (key.up) y--;
		if (key.down) y++;
		if (key.left) x--;
		if (key.right ) x++;
	}
	
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		screen.render(x, y);
		
		for (int i = 0; i < pixels.length; i++) {
		pixels[i] =	screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0 , 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.Frame.setResizable(false);
		game.Frame.setTitle(title);
		game.Frame.add(game);
		game.Frame.pack();
		game.Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.Frame.setLocationRelativeTo(null);
		game.Frame.setVisible(true);
		
		game.start();
	}
	
}