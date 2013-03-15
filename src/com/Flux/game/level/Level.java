package com.Flux.game.level;

import com.Flux.game.graphics.Screen;

public class Level {

	protected int width, height;
	protected int[] tiles;
	
	
	public Level(int width, int height){
		this.width = width;
		this.height = height;
		tiles = new int[width * height];
		GenerateLevel();
	}

	public Level(String path){
		loadlevel(path);
	}
	
	protected void GenerateLevel() {
	}
	
	private void loadlevel(String path) {
	}
	
	public void update() {
	}
	
	private void time(){	
	}
	
	public void render( int xScroll, int yScroll, Screen screen){
	}
	
}
