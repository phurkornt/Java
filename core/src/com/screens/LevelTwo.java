package com.screens;

import com.badlogic.gdx.Screen;
import com.utils.WorldController;
import com.utils.WorldLevelOneRenderer;

public class LevelTwo implements Screen{
	private WorldController worldController;
	private WorldLevelOneRenderer worldRenderer;
	@Override
	public void show() {
		worldController=new WorldController() ;
		worldRenderer=new WorldLevelOneRenderer(worldController,"maps/xbot_map_level02.tmx");
	}
	
	@Override
	public void render(float delta) {
		worldController.update();
		worldRenderer.render();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		
		
	}
}
