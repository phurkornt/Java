package com.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.util.Map;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.boting;

public class LevelOneEndScreen extends GameScreen {
	private String endType;
	private int lifeRemainQTY;
	private int crytalQTY;
	private int coinQTY;
	private int timeQTY;
	
	private Image bgLevel01End;
	
	private BitmapFont fontHead,fontCont,fontTotal;
	private Label labelHead;
	
	private Texture tXbot,tCrytal,tCoin;
	private Image acXbot,acCrytal,acCoin;
	private Label laLife,laCrytal,laCoin,laTime;
	private int lifeTo,CrytalTo,CoinTo,TimeTo;
	private Label laTotal;
	
	
	private int score;
	
	public LevelOneEndScreen(String endType,int lifeRemainQTY,int crytalQTY,int coinQTY,int timeQTY) {			
		this.endType=endType;
		this.lifeRemainQTY=lifeRemainQTY;
		this.crytalQTY=crytalQTY;
		this.coinQTY=coinQTY;
		this.timeQTY=timeQTY;
	}
	

	@Override
	public void show() {
		super.show();
		bgLevel01End=new Image(getSkin(),"bg_level01_endscreen");
		bgLevel01End.setPosition(0, 0);
		
		fontHead=new BitmapFont(Gdx.files.internal("fonts/carterone.fnt"), Gdx.files.internal("fonts/carterone.png"), false);
		fontHead.getData().setScale(1.25f,1.25f);
		if(endType=="Win") {
			labelHead=new Label("Completed",new Label.LabelStyle(fontHead, Color.GREEN));
		}else if (endType=="GameOver"){
			labelHead=new Label("GameOver",new Label.LabelStyle(fontHead, Color.RED));
		}
		labelHead.setPosition(boting.WIDTH/2-fontHead.getData().glyphs.length/2-30f,boting.HEIGHT/2+80f);
		
		//System.out.println(endType+" "+lifeRemainQTY+" "+crytalQTY+" "+coinQTY+" "+timeQTY);
		
		fontHead.getData().setScale(1.5f,1.5f);
	
		fontCont=new BitmapFont(Gdx.files.internal("fonts/carterone.fnt"), Gdx.files.internal("fonts/carterone.png"), false);
		fontCont.getData().setScale(1,1);
		
		tXbot=new Texture("sprites/people.png");
		TextureRegion xbotRegion=new TextureRegion(tXbot);
		acXbot=new Image(xbotRegion);
		acXbot.scaleBy(1, 1);
		
		
		lifeTo=lifeRemainQTY*boting. POINT_PRE_LIFE;
		
		laLife=new Label(": "+lifeRemainQTY+" x "+boting.POINT_PRE_LIFE+" = "+lifeTo,new Label.LabelStyle(fontCont, Color.WHITE) );
		acXbot.setPosition(10, 10);
		laLife.setPosition(50, 50);
		
		tCrytal=new Texture("sprites/crystalblue.png");
		acCrytal=new Image(tCrytal);
		CrytalTo= crytalQTY*boting.POINT_CRYSTAL;
		laCrytal=new Label(": "+crytalQTY+" x "+boting.POINT_CRYSTAL+" = "+CrytalTo,new Label.LabelStyle(fontCont, Color.WHITE));
		
		tCoin=new Texture("sprites/coinyellow.png");
		acCoin=new Image(tCoin);
		CoinTo=coinQTY*boting.POINT_COIN;
		laCoin=new Label(": "+coinQTY+" x "+boting.POINT_COIN+" = "+CoinTo,new Label.LabelStyle(fontCont, Color.WHITE));
		
		TimeTo=timeQTY*boting.POINT_TIME;
		laTime=new Label("Time : "+"="+timeQTY+"x"+boting.POINT_TIME+"= "+TimeTo,new Label.LabelStyle(fontCont, Color.WHITE));
		
		score+=CoinTo+CrytalTo+lifeTo+TimeTo;
		fontTotal=new BitmapFont(Gdx.files.internal("fonts/carterone.fnt"), Gdx.files.internal("fonts/carterone.png"), false);
		fontTotal.getData().setScale(2,2);
		laTotal=new Label("Total Score = "+score,new Label.LabelStyle(fontTotal, Color.BLUE));
		if(endType=="Win") {
		addActor();
		if(score>getLevel01HiScore()) {
			System.out.println(getLevel01HiScore());
			setLevel01Completed(score);
		}
		}else if(endType=="GameOver") {
		addActorOver();
		
		}
		
		
	}
	private int getLevel01HiScore() {
		Preferences prefs=Gdx.app.getPreferences("BotingPregs");
		Map tmpMap=(Map) prefs.get();
		if(!tmpMap.isEmpty()) {
			return prefs.getInteger("level01_HiScore",0);
			
		}else {
			return 0;
		}
	}
	private void setLevel01Completed(int score){
	
		Preferences prefs=Gdx.app.getPreferences("BotingPregs");
		Map tmpMap=(Map) prefs.get();
		if(!tmpMap.isEmpty()) {
			prefs.putInteger("level01_HiScore",score);
			prefs.putInteger("level02_Status",1);
			prefs.putInteger("level02_HiScore",0);
			prefs.flush();
		}
	}
	private void addActorOver() {
		stage.addActor(bgLevel01End);
		stage.addActor(backButton);
		fontHead.getData().setScale(2.5f,2.5f);
		//labelHead.setPosition(x, y);
		labelHead.setPosition(138,168);
		System.out.println(Gdx.input.getX()+" "+Gdx.input.getY());
		stage.addActor(labelHead);
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
		
		
	}

	private void addActor() {
		acXbot.setPosition(127,207);
		laLife.setPosition(200,218);
		
		acCrytal.setPosition(130, 162);
		acCrytal.setSize(30,30);
		laCrytal.setPosition(200, 162);
		
		acCoin.setPosition(130,120);
		acCoin.setSize(30, 30);
		laCoin.setPosition(200, 120);
		
		laTime.setPosition(170, 78);
		laTotal.setPosition(75, 0);
		//laTotal.setPosition(Gdx.input.getX(), Gdx.input.getY());
		//System.out.println(Gdx.input.getX()+" "+Gdx.input.getY());
		
		stage.addActor(bgLevel01End);
		stage.addActor(labelHead);
//		
		stage.addActor(acXbot);
		stage.addActor(laLife);
		
		stage.addActor(acCrytal);
		stage.addActor(laCrytal);
		
		stage.addActor(acCoin);
		stage.addActor(laCoin);
		
		stage.addActor(laTime);
		stage.addActor(laTotal);
		
		stage.addActor(backButton);
		
		
//		
		
//		
	}
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}
}
