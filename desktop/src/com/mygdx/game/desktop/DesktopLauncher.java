package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.boting;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Super>Bot";
		config.width=boting.WIDTH;
		config.height=boting.HEIGHT;
		//config.fullscreen=true;
		new LwjglApplication(new boting(), config);
	}
}
