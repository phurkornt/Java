package com.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Xbot {
	public static float Max_Velocity=10f;
	public static float Jump_Velocity=41f;
	public static float Damping=0.2f;
	public static final float Gravity=-2.5f;
	
	public enum State{Standing,Walking,Jumping}
	
	private Texture xbotTexture;
	private float width;
	private float height;
	private TextureRegion xbotFrame;
	public Animation stand;
	public Animation walk;
	public Animation jump;
	
	private Vector2 position=new Vector2();
	private Vector2 velocity=new Vector2();
	
	public State state;
	private float stateTime;
	private boolean facedRight;
	
	private boolean ground;
	
	private Rectangle xbotBounds;
	Texture katana;
	boolean checkWeapon=false;

	
	public Xbot() {
		xbotTexture=new Texture("sprites/mon.png");
		
		TextureRegion[] regions=TextureRegion.split(xbotTexture,19,34)[0];
		TextureRegion[] regions1=TextureRegion.split(new Texture("sprites/run.png") ,21,33)[0];
		TextureRegion[] regions2=TextureRegion.split(new Texture("sprites/jump.png") ,17,34)[0];
		stand=new Animation(0.05f, regions[0],regions[1],regions[2],regions[3],regions[4],regions[5], regions[6],regions[7],regions[8],regions[9],regions[10],regions[11]);
		jump=new Animation(0, regions2[0]);
		walk=new Animation(0.15f, regions1[0],regions1[1],regions1[2],regions1[3],regions1[4],regions1[5],regions1[6],regions1[7]);
		
		stand.setPlayMode(PlayMode.LOOP_PINGPONG);
		walk.setPlayMode(PlayMode.LOOP_PINGPONG);
		
		width=regions[0].getRegionWidth()*(1/14f);
		height=regions[0].getRegionHeight()*(1/14f);
		
		state=State.Walking;
		stateTime=0;
		facedRight=true;
		ground=true;
		
		xbotFrame=(TextureRegion) stand.getKeyFrame(stateTime);
		xbotBounds=new Rectangle();
		
	}
	public void update(float delta) {
		if(delta==0)return;
		if(delta>0.1f)delta=0f;
		stateTime+=delta;
		updateXbotFrame();
	}
	public void render(SpriteBatch sb) {
		sb.begin();
		if(facedRight) {
			sb.draw(xbotFrame, position.x,position.y,width,height);
		}else {
			sb.draw(xbotFrame, position.x+width,position.y,-width,height);
		}
		
		sb.end();
	}
	private void updateXbotFrame() {
		switch (state) {
		case Standing:
			xbotFrame=(TextureRegion) stand.getKeyFrame(stateTime);
			break;
		case Walking:
			xbotFrame=(TextureRegion) walk.getKeyFrame(stateTime);
			break;
		case Jumping:
			xbotFrame=(TextureRegion) jump.getKeyFrame(stateTime);
			break;
		}
	}
	public void setBounds(float x,float y,float width,float height) {
		xbotBounds.set(x, y, width, height);
	}
	public Rectangle getBounds() {
		return xbotBounds;
	}
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(float a,float b) {
		this.position.x = a;
		this.position.y = b;
	}
	public void setPositionX(float x) {
		this.position.x=x;
	}
	public void setPositionY(float y) {
		this.position.y=y;
	}
	public void setVelocityX(float x) {
		this.velocity.x=x;
	}
	public void setVelocity(float y) {
		this.velocity.y=y;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public boolean getFacedRight() {
		return facedRight;
	}
	public void setFacedRight(boolean facedRight) {
		this.facedRight = facedRight;
	}
	public boolean getGround() {
		return ground;
	}
	public void setGround(boolean ground) {
		this.ground = ground;
	}
	public float getWidth() {
		return width;
	}
	public float getHeight() {
		return height;
	}
	public void dispose() {
		xbotTexture.dispose();
	}
	public void setCheckWeapon(boolean checkWeapon) {
		this.checkWeapon = checkWeapon;
	}
	
	
}
