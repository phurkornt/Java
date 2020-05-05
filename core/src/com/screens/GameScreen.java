package com.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.boting;

public class GameScreen implements Screen {
	protected OrthographicCamera cam;
	protected Skin skin;
	protected Stage stage;
	protected Button backButton;
	
	public GameScreen() {
		// TODO Auto-generated constructor stub
	}
	protected Skin getSkin() {
		if(skin==null) {
			skin=new Skin(Gdx.files.internal("screens/xbot_button.json"),
					 new TextureAtlas("screens/xbot_spritesheet_screen.pack"));
		}
		return skin;
	}
	@Override
	public void show() {
		cam=new OrthographicCamera();
		cam.setToOrtho(false, boting.WIDTH,	boting.HEIGHT);
		cam.update();
		
		stage=new Stage(new StretchViewport (boting.WIDTH,	boting.HEIGHT,cam));
		backButton=new Button(getSkin(),"btn_back_UP");
		backButton.setPosition(10, 10);
		
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
			}
			
		});
		Gdx.input.setInputProcessor(stage);
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.getCamera().update();
		stage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height,true);
	}

	@Override
	public void hide() {
		dispose();
		
	}

	@Override
	public void dispose() {
		if(skin!=null)skin.dispose();
		if(stage!=null)stage.dispose();
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
