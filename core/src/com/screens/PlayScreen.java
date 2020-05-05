package com.screens;

import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PlayScreen extends GameScreen {
	private Image bgPlayScreen;
	private Button level01,level02;
	private int level_1,level_2;
	
	@Override
	public void show() {
		super.show();
		bgPlayScreen=new Image(getSkin(),"bg_playscreen");
		bgPlayScreen.setPosition(0, 0);
		bgPlayScreen.addAction(Actions.sequence(
				Actions.alpha(0f),
				Actions.fadeIn(1f),
				Actions.delay(0.5f))
		);
		putPrefs();
		getPrefs();
		if(level_1==1) {
			level01=new Button(getSkin(),"btn_level01_unlock_UP");
		}else if(level_1==2) {
			level01=new Button(getSkin(),"btn_level01_lock");
		}
		if(level_2==1) {
			level02=new Button(getSkin(),"btn_level02_unlock_UP");
		}else if(level_2==2) {
			level02=new Button(getSkin(),"btn_level02_lock");
		}
		//Set position of button
		level01.setPosition(190,240);
		level02.setPosition(280,240);
		if(level_1==1) {
		level01.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new LevelOne());
			}
		});
		}
		if(level_2==1) {
		level02.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new LevelTwo());
			}
		});
		}
		stageAddActor();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		
	}
	private void stageAddActor() {
		stage.addActor(bgPlayScreen);
		stage.addActor(level01);
		stage.addActor(level02);
		stage.addActor(backButton);
	}
	private void putPrefs() {
		Preferences pre=Gdx.app.getPreferences("BotingPregs");
		Map tmpMap=pre.get();
		if(tmpMap.isEmpty()) {
			pre.putInteger("level01_Status",1);
			pre.putInteger("level01_HiScore",0);
			
			pre.putInteger("level02_Status",2);
			pre.putInteger("level02_HiScore",0);
			pre.flush();
		}
	}
	private void getPrefs() {
		Preferences pre=Gdx.app.getPreferences("BotingPregs");
		level_1=pre.getInteger("level01_Status",1);
		level_2=pre.getInteger("level02_Status",2);
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		super.hide();
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}
}
