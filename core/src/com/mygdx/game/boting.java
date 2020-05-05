package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.screens.SplashScreen;

public class boting extends Game{
	public static int WIDTH=640;
	public static int HEIGHT=400;
	
	public static int Start_X=4;
	public static int Start_Y=2;
	public static int Ground_Y=2;
	
	public static final int LIFES=3000;
	public static final int POINT_PRE_LIFE=100;
	public static final int POINT_CRYSTAL=10;
	public static final int POINT_COIN=2;
	public static final int POINT_TIME=20;
	
	public static final int TIME_LEVEL01=100;
	
	private SplashScreen introScreen;
	@Override
	public void create() {
		// TODO Auto-generated method stub
		introScreen=new SplashScreen();
		setScreen(introScreen);
	}
	@Override
	public void dispose () {
		
	}

	
}
