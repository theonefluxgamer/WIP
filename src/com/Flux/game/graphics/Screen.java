package com.Flux.game.graphics;

import java.util.Random;

public class Screen {

	private int width, height;
	public int[] pixels;
	public final int MAP_Size_Width = 16;
	public final int MapSize_Length = 16;
	public final int MapSize_Mask = MAP_Size_Width - 1;
	
	public int[] tiles = new int[MAP_Size_Width * MapSize_Length];
	
	private Random random = new Random();
	
	public Screen (int width, int height){
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
		for(int i = 0; i < MAP_Size_Width * MapSize_Length; i++){
			tiles[i] = random.nextInt(0xffffff);
		}
	}
	
	public void clear(){
		for (int i = 0; i < pixels.length; i++){
			pixels[i] = 0;
		}
	}
	
	public void render(int xOffset, int yOffset){
		for (int y = 0; y < height; y++){
			int yp = y + yOffset;
			if (yp < 0 || yp >= height) continue;
			for (int x = 0; x < width; x++){
				int xp = x + xOffset;
				if (xp < 0 || xp >= width) continue;
				pixels[xp + yp * width] = Sprite.grass.pixels[(x&15) + (y&15) * Sprite.grass.SIZE];
			}
		}
	}
}
