package com.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.boting;

public class MenuScreen implements Screen{
	private OrthographicCamera cam;
	
	private Stage menuStage;
	private Skin menuSkin;
	
	private Image bgMenu;
	private Button playButtuon;
	private Button hiButtuon;
	private Button exitButtuon;
	
	public MenuScreen() {
		cam=new OrthographicCamera();
		cam.setToOrtho(false, boting.WIDTH,	boting.HEIGHT);
		cam.update();
	}
	@Override
	public void show() {
		menuStage=new Stage(new StretchViewport(boting.WIDTH,boting.HEIGHT,cam));
		menuSkin=new Skin(Gdx.files.internal("screens/xbot_button.json"), new TextureAtlas("screens/xbot_spritesheet_screen.pack"));//This Json
		
		bgMenu=new Image(new Texture("screens/back.png"));
		
		playButtuon=new Button(menuSkin,"btn_menu_play_UP");
		hiButtuon=new Button(menuSkin,"btn_menu_hiscore_UP");
		exitButtuon=new Button(menuSkin,"btn_menu_exit_UP");
		
		bgMenu.setPosition(0, 0);
		
		playButtuon.setPosition(217,300);
		hiButtuon.setPosition(217,215);
		exitButtuon.setPosition(217,115);
		//wait add actions
		playButtuon.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new PlayScreen());
			}
		});
		hiButtuon.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new HiScreen());
			}
		});
		exitButtuon.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		Gdx.input.setInputProcessor(menuStage);
		
		menuStageAddActor();
		Gdx.gl20.glClearColor(100/255,149/255,237/255,1);
		//Gdx.gl20.glClearColor(0, 0, 0, 1);
	}
//	playButtuon
//	hiButtuon
//	exitButtuon
	
	@Override
	public void render(float delta) {
		
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		menuStage.act(delta);
		menuStage.getCamera().update();
		menuStage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		menuStage.getViewport().update(width, height,true);
		
	}
	private void menuStageAddActor() {
		menuStage.addActor(bgMenu);
		menuStage.addActor(playButtuon);
		menuStage.addActor(hiButtuon);
		menuStage.addActor(exitButtuon);
	}
	@Override
	public void pause() {
		menuStage.dispose();
		menuSkin.dispose();
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		pause();
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
