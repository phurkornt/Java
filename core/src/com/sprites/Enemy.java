package com.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy {
	public Texture textureEnemy;
	public float width;
	public float height;
	public Vector2 position=new Vector2();
	public Vector2 velocity=new Vector2();
	public Rectangle boundsEnemy=new Rectangle();
	
	public abstract void update(float delta);
	public abstract void render(SpriteBatch sb);
	public abstract void setPosition(int x,int y);
	public abstract Vector2 getPosition();
	public void dispose() {
		textureEnemy.dispose();
	}
}
