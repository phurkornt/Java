package com.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.utils.Tiles;

public class Bugger extends Enemy {
	private static final float VELOCITY=5f;
	private Animation walk;
	private TextureRegion buggerFrame;
	private float stateTime;
	
	private Vector2 velocity;
	
	private boolean facedRight;
	
	private Tiles tile;
	private Array<Rectangle> tiles=new Array();
	
	public enum Direction{LEFT,RIGHT};
	public Direction direction;
	
	private int startX,startY,endX,endY;
	private boolean hitHplatform;
	
	public Bugger(float x,float y,OrthogonalTiledMapRenderer tmRenderer) {
		textureEnemy=new Texture("sprites/bugger2.png");
		TextureRegion[] regions=TextureRegion.split(textureEnemy,32,36)[0];
		walk=new Animation(0.15f,regions[0],regions[1],regions[2]);
		walk.setPlayMode(PlayMode.LOOP_PINGPONG);
		
		width=regions[0].getRegionWidth()*(1/20f);
		height=regions[0].getRegionHeight()*(1/20f);
		
		position.set(x,y);
		
		boundsEnemy.set(x, y, width, height);
		
		tile=new Tiles(tmRenderer);
		
		stateTime=0;
		
		direction=Direction.RIGHT;
		if(direction==Direction.LEFT) {
			velocity=new Vector2(-VELOCITY,0);
			facedRight=false;
		}else {
			velocity=new Vector2(VELOCITY,0);
			facedRight=true;
		}
		buggerFrame=(TextureRegion) walk.getKeyFrame(stateTime);
		
	}
	@Override
	public void update(float delta) {
		if(delta==0)return; 
		if(delta>0.1f)delta=0.1f;
		stateTime+=delta;
		
		boundsEnemy.x=position.x;
		boundsEnemy.y=position.y;
			
		velocity.scl(delta);
		if(checkCollidesHplatform()) {
			hitHplatform=!hitHplatform;
			facedRight=!facedRight;
		}
		position.add(velocity);
		velocity.scl(1/delta);
		
		buggerFrame=(TextureRegion) walk.getKeyFrame(stateTime,true);
		if(!hitHplatform) {
			velocity.x=-VELOCITY;
		}else {
			velocity.x=VELOCITY;
		}
		
	}
	

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		if(!hitHplatform) {
			sb.draw(buggerFrame, position.x+width, position.y,-width,height);
		}else {
			sb.draw(buggerFrame, position.x, position.y,width,height);
		}
		sb.end();
		
	}
	private boolean checkCollidesHplatform() {
		if(velocity.x>0) {
			startX=endX=(int)(position.x+width+velocity.x);
		}else {
			startX=endX=(int)(position.x+velocity.x);
		}
		startY=(int)(position.y);
		endY=(int)(position.y+height);
		tile.getTiles(startX, startY, endX, endY, tiles, "platform");
		boundsEnemy.x+=velocity.x;
		for(Rectangle tile:tiles) {
			if(boundsEnemy.overlaps(tile)) {
				velocity.x=0;
				return true;
			}
		}
		boundsEnemy.x=position.x;
		return false;
	}
	@Override
	public void setPosition(int x, int y) {
		position.x=x;
		position.y=y;
		
	}
	
	public void setBounds(float x,float y,float width,float height) {
		boundsEnemy.set(x, y, width, height);
	}
	public Rectangle getBounds(){
		return boundsEnemy;
	}
	public boolean getFacedRight() {
		return facedRight;
	}
	public void setFacedRight(boolean facedRight) {
		this.facedRight = facedRight;
	}
	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return position;
	}
	

}
