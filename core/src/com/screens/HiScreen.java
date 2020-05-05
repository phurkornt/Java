package com.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class HiScreen extends GameScreen{
	private Image bgHiScreen;
	private BitmapFont font;
	private Label lalel1,lalel2;
	private int level1_hi,level2_hi;
	private String strLe1,strLe2;
	
	@Override
	public void show() {
		super.show();
		bgHiScreen=new Image(getSkin(),"bg_hiscorescreen");
		bgHiScreen.setPosition(0, 0);
		bgHiScreen.addAction(Actions.sequence(
				Actions.alpha(0f),
				Actions.fadeIn(1f),
				Actions.delay(0.5f))
		);
		font=new BitmapFont(Gdx.files.internal("fonts/carterone.fnt"),Gdx.files.internal("fonts/carterone.png"),false);
		getPrefs();
		if(level1_hi==0) {
			strLe1="-";
		}else {
			strLe1=String.valueOf(level1_hi);
		}
		if(level2_hi==0) {
			strLe2="-";
		}else {
			strLe2=String.valueOf(level1_hi);
		}
		
		lalel1=new Label("Level 1 : "+strLe1,new Label.LabelStyle(font,Color.WHITE));
		lalel2=new Label("Level 2 : "+strLe2,new Label.LabelStyle(font,Color.WHITE));
		
		lalel1.setPosition(195,240);
		lalel2.setPosition(195,190);
		
		stage.addActor(bgHiScreen);
		stage.addActor(backButton);
		stage.addActor(lalel1);
		stage.addActor(lalel2);
	}
	private void getPrefs() {
		Preferences pre=Gdx.app.getPreferences("BotingPregs");
		level1_hi=pre.getInteger("level01_HiScore",0);
		level2_hi=pre.getInteger("level02_HiScore",0);
		
	}
	@Override
	public void render(float delta) {
		super.render(delta);
		//System.out.println(Gdx.input.getX()+" "+Gdx.input.getY());
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
