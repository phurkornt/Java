package com.utils;

import com.badlogic.gdx.Gdx;

public class WorldController {
	
public float deltaTime;
public float animationTime;

public WorldController() {
	deltaTime=0.0f;
	animationTime=0.0f;
}
public void update() {
	deltaTime=Gdx.graphics.getDeltaTime();
	animationTime+=deltaTime;
}
public float getDeltaTime() {
	return deltaTime;
}
public void setDeltaTime(float deltaTime) {
	this.deltaTime = deltaTime;
}
public float getAnimationTime() {
	return animationTime;
}
public void setAnimationTime(float animationTime) {
	this.animationTime = animationTime;
}


}
