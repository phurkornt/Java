package com.screens;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.boting;
public class SplashScreen implements Screen {
	private OrthographicCamera cam;
	
	private Image splashImage;
	private Stage introStage;
	
	public SplashScreen() {
		cam=new OrthographicCamera();
		cam.setToOrtho(false, boting.WIDTH,	boting.HEIGHT);
		cam.update();
		
		splashImage=new Image(new Texture("screens/back.png"));
		introStage=new Stage(new StretchViewport (boting.WIDTH,	boting.HEIGHT,cam));
		
	}
	@Override
	public void show() {
		introStage.addActor(splashImage);
		splashImage.addAction(Actions.sequence(
				Actions.alpha(0f),
				Actions.fadeIn(1f),
				Actions.delay(0f),
				Actions.run(new Runnable() {
					@Override
					public void run() {
					((Game)Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
					}
				})
		));
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		introStage.act(delta);
		introStage.getCamera().update();
		introStage.draw();
		
	}

	@Override
	public void resize(int width, int height) {
		introStage.getViewport().update(width, height,true);
		
	}

	@Override
	public void pause() {
		introStage.dispose();
		
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
