package com.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bomber {
	private static final float SPEED=0.1f;
	private Texture bomberTex;
	private float width;
	private float height;
	
	private Sprite bomberSprite;
	private Vector2 velocity;
	
	private float path[];
	private float angle;
	
	private Vector2 currenPosition,nextPosition;
	private  int currentIndex;
	private  int pathSize;
	
	private Rectangle bomberBounds;
	public Bomber(float path[]) {
		this.path=path;
		
		bomberTex=new Texture("sprites/bomber.png");
		width=bomberTex.getWidth()*(1/18f);
		height=bomberTex.getHeight()*(1/18f);
		
		bomberSprite=new Sprite(bomberTex);
		bomberSprite.setSize(bomberSprite.getWidth()*(1/16f),bomberSprite.getHeight()*(1/16f));
		
		velocity=new Vector2();
		for(int i=0;i<path.length;i++) {
			path[i]=path[i]*(1/16f);
		}
		currenPosition=new Vector2(path[0],path[1]);
		nextPosition=new Vector2(path[2],path[3]);
		
		bomberSprite.setPosition(currenPosition.x, currenPosition.y);
		
		currentIndex=2;
		pathSize=path.length;
		bomberBounds=new Rectangle();
		
	}
	public void update() {
		bomberBounds.set(bomberSprite.getX(),bomberSprite.getY(),
						 bomberSprite.getWidth(),bomberSprite.getHeight());
		angle=MathUtils.atan2(nextPosition.y-currenPosition.y,nextPosition.x-currenPosition.x);
		velocity.set(SPEED*MathUtils.cos(angle),SPEED*MathUtils.sin(angle));
		bomberSprite.setPosition(bomberSprite.getX()+velocity.x,bomberSprite.getY()+velocity.y);
		float xPositionDiff=nextPosition.x-bomberSprite.getX();
		float yPositionDiff=nextPosition.y-bomberSprite.getY();
		
		if(Math.abs(xPositionDiff)<=SPEED&&Math.abs(yPositionDiff)<=SPEED) {
			currentIndex+=2;
			currenPosition.set(nextPosition.x,nextPosition.y);
			nextPosition.set(path[currentIndex%pathSize],path[(currentIndex+1)%pathSize]);
			bomberSprite.setPosition(currenPosition.x, currenPosition.y);
		}
	}
	public void render(SpriteBatch sb) {
		sb.begin();
		bomberSprite.draw(sb);
		sb.end();
	}
	public Rectangle getBounds() {
		return bomberBounds.set(bomberSprite.getX(),bomberSprite.getY(),width,height);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
