package com.utils;

import java.util.Iterator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader.Setters.Bones;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.boting;
import com.screens.LevelOne;
import com.screens.LevelOneEndScreen;
import com.screens.LevelTwo;
import com.screens.MenuScreen;
import com.screens.PlayScreen;
import com.sprites.Bomber;
import com.sprites.Bugger;
import com.sprites.Xbot;
import com.sprites.Xbot.State;

public class WorldLevelOneRenderer implements Disposable {
	private WorldController worldController;
	private TiledMap tiledMap;
	
	private int mapWidth;
	private int mapHeight;
	private float minX,minY,maxX,maxY;
	
	private OrthogonalTiledMapRenderer tmRenderer;
	private Xbot xbot;
	private SpriteBatch sb;
	
	private OrthographicCamera cam;
	private Texture test;
	//Check tiles
	private int startX,startY,endX,endY;
	private Tiles tile;
	private Rectangle boundsXbot;
	private Array<Rectangle>tiles=new Array<Rectangle>();
	
	private Rectangle boundsExitTile;
	private Array<Bugger>buggers;
	private Array<Bomber>bombers;
	private Array<Rectangle>water;
	
	//Show date while play game exemple show point or life and time 
	private OrthographicCamera camHud;
	private SpriteBatch sbHud;
	private BitmapFont font;
	private Texture xbotHud,crystalBlueHud,coinYellowHud;
	private Image xbotActor,crystalBulActor,coinYellowHudActor;

	private Stage stageHub;
	private Label labelXbotQTY,labelCrystalBlueQTY,labelConinYellowQTY;
	private Label labelTimeLevel01;
	
	private int lifes;
	private int timeLevel01;
	private float timeCount;
	private int CrystalBlueQTY,ConinYellowQTY;
	
	private int num;

	
	public WorldLevelOneRenderer(WorldController worldController,String map) {
		this.worldController=worldController;
		
		cam=new OrthographicCamera();
		cam.setToOrtho(false,30,20);
		cam.update();
		
		tiledMap=new TmxMapLoader().load(map);
		
		TiledMapTileLayer mainLayer=(TiledMapTileLayer) (tiledMap.getLayers().get("platform"));
		mapWidth=mainLayer.getWidth();
		mapHeight=mainLayer.getHeight();
		
		tmRenderer=new OrthogonalTiledMapRenderer(tiledMap,1/16f);
		sb=(SpriteBatch) tmRenderer.getBatch();
		
		tile=new Tiles(tmRenderer);
		boundsXbot=new Rectangle();
		
		boundsExitTile=layExitTile(mapWidth,mapHeight);
		
		buggers=new Array<Bugger>();
		layBuggers(buggers);
		bombers=new Array<Bomber>();
		layBombers(bombers);
		water=new Array<Rectangle>();
		layWater(water);
		//Show date while play game exemple show point or life and time 
		sbHud=new SpriteBatch();
		camHud=new OrthographicCamera();
		camHud.setToOrtho(false,30,20);
		camHud.update();
		font=new BitmapFont(Gdx.files.internal("fonts/carterone.fnt"),
							Gdx.files.internal("fonts/carterone.png"),false);
		font.getData().setScale(0.75f,0.75f);
		lifes=boting.LIFES;//lifes = 3
		CrystalBlueQTY=0;
		ConinYellowQTY=0;
		timeLevel01=boting.TIME_LEVEL01;//timeLevel01 = 100s
		timeCount=0;//Count the number
		
		xbotHud=new Texture("sprites/xbot.png");
		TextureRegion xbotRegion=new TextureRegion(xbotHud,0,0,28,35);
		xbotActor=new Image(xbotRegion);
		
		crystalBlueHud=new Texture("sprites/crystalblue.png");
		crystalBulActor=new Image(crystalBlueHud);
		
		coinYellowHud=new Texture("sprites/coinyellow.png");
		coinYellowHudActor=new Image(coinYellowHud);
		
		//this is stage for support actor
		stageHub=new Stage(new StretchViewport(boting.WIDTH,boting.HEIGHT,camHud));
		//this for show point have 4
		labelXbotQTY=new Label("x"+lifes,new Label.LabelStyle(font,Color.WHITE));
		labelConinYellowQTY=new Label("x"+ConinYellowQTY,new Label.LabelStyle(font,Color.WHITE));
		labelCrystalBlueQTY=new Label("x"+CrystalBlueQTY,new Label.LabelStyle(font,Color.WHITE));
		
		labelTimeLevel01=new Label("x"+timeLevel01,new Label.LabelStyle(font,Color.WHITE));
		labelTimeLevel01.setPosition(boting.WIDTH-150f,boting.HEIGHT-50f);
		
		
		//Gdx.gl.glClearColor(155/255, 222/255, 255/255, 1);
		xbot=new Xbot();
		xbot.setPosition(boting.Start_X,boting.Start_Y);
		xbot.setFacedRight(false);
		
		test=new Texture("screens/bg_intro.png");
		
	}
	
	private void update(float delta) {
		xbot.update(delta);
		handInput();
		updateXbot(delta);
		cam.position.x=xbot.getPosition().x;//+11
		keepCameraBounds();
		keepXbotBounds();
		
		for(Bugger bug:buggers) {
			bug.update(delta);
		}
		for(Bomber bug:bombers) {
			bug.update();
		}
		checkXbotCollidesExitTile();
		checkBomber();
		checkBugge();
		checkWater();
		
		displayXbotHUD();
		displayCrytalHUD();
		displayCoinHUD();
		displayTimeLevelHUD(delta);
		checkGame();
		cam.update();
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			((Game)Gdx.app.getApplicationListener()).setScreen(new LevelOneEndScreen("Win",
					lifes,CrystalBlueQTY,ConinYellowQTY,timeLevel01));
		}
	}
	private void checkGame() {
		if(lifes<1||timeLevel01==0) {
			((Game)Gdx.app.getApplicationListener()).setScreen(new LevelOneEndScreen("GameOver",
					lifes,CrystalBlueQTY,ConinYellowQTY,timeLevel01));
		}
	}
	private void stageAddActorXbotHub() {
		stageHub.addActor(labelTimeLevel01);
	}
	private void displayXbotHUD() {
		xbotActor.setPosition(20, boting.HEIGHT-45f);
		labelXbotQTY.setPosition(xbotActor.getX()+xbotActor.getWidth(),boting.HEIGHT-55f);
		stageHub.addActor(xbotActor);
		stageHub.addActor(labelXbotQTY);
	}
	private void displayCrytalHUD() {
		crystalBulActor.setPosition(20, boting.HEIGHT-80f);
		labelCrystalBlueQTY.setPosition(crystalBulActor.getX()+crystalBulActor.getWidth()+10f,boting.HEIGHT-85f);
		stageHub.addActor(crystalBulActor);
		stageHub.addActor(labelCrystalBlueQTY);
	}
	private void displayCoinHUD() {
		coinYellowHudActor.setPosition(20, boting.HEIGHT-110f);
		labelConinYellowQTY.setPosition(crystalBulActor.getX()+coinYellowHudActor.getWidth()+10f,boting.HEIGHT-115f);
		stageHub.addActor(coinYellowHudActor);
		stageHub.addActor(labelConinYellowQTY);
	}
	private void displayTimeLevelHUD(float delta) {
		timeCount+=delta;
		if(timeCount>=1) {
			if(timeLevel01>0) {
				timeLevel01--;
			}
			labelTimeLevel01.setText("X "+timeLevel01);
			timeCount=0;
		}
	}

	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.setProjectionMatrix(cam.combined);
		cam.update();
		update(worldController.getDeltaTime());
		draw();
		
		//for show point
		sbHud.setProjectionMatrix(camHud.combined);
		camHud.update();
		
		sbHud.begin();
		//add something
		stageAddActorXbotHub();
		stageHub.act(worldController.getDeltaTime());
		stageHub.draw();
		sbHud.end();
		
		
		
	}
	private void draw() {
		sb.begin();
		sb.draw(test, 0, 0);
		
		sb.end();
		tmRenderer.setView(cam);
		tmRenderer.render();
		xbot.render(sb);
		
		for(Bugger bug:buggers) {
			bug.render(sb);
		}
		for(Bomber bug:bombers) {
			bug.render(sb);
		}
	}
	
	private Rectangle layExitTile(int a,int b) {
		TiledMapTileLayer layer=(TiledMapTileLayer) (tiledMap.getLayers().get("keyitems"));
		if(layer==null)return null;
		for(int y=0;y<=mapHeight;y++) {
			for(int x=0;x<=mapWidth;x++) {
				Cell cell =layer.getCell(x, y);
				if(cell!=null) {
					if(cell.getTile().getProperties().get("exit","true",String.class)!=null) {
						Rectangle rect=new Rectangle(x,y,1,1);
						return rect;
					}
				}
			}
		}
		return null;
	}
	
	private void checkXbotCollidesExitTile() {
		boundsXbot.set(xbot.getPosition().x, xbot.getPosition().y, xbot.getWidth(), xbot.getHeight());
		if(boundsExitTile!=null) {
			if(boundsXbot.overlaps(boundsExitTile)) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new LevelOneEndScreen("Win",
						lifes,CrystalBlueQTY,ConinYellowQTY,timeLevel01));
			}
		}
	}
	
	private void checkBomber() {
		xbot.setBounds(xbot.getPosition().x, xbot.getPosition().y, xbot.getWidth(), xbot.getHeight());
		for (Bomber bom:bombers) {
			
			if(bom.getBounds().overlaps(xbot.getBounds())) {
				//bombers.removeValue(bom,false);
				xbot.setPosition(boting.Start_X,boting.Start_Y);
				xbot.setFacedRight(true);
				lifes--;
				labelXbotQTY.setText("x "+lifes);
				
			}
		}
	}
	private void checkBugge() {
		xbot.setBounds(xbot.getPosition().x, xbot.getPosition().y, xbot.getWidth(), xbot.getHeight());
			int novel=0;
		for (Bugger bu:buggers) {
			
			System.out.println(novel);
			novel++;
			if(bu.getBounds().overlaps(xbot.getBounds())) {
				
				if(xbot.getPosition().y>bu.getPosition().y) {
					
					
					System.out.println("hi");
					System.out.println(xbot.getVelocity());
					xbot.state=Xbot.State.Jumping;
					xbot.setVelocity(xbot.Jump_Velocity);
					num++;
					if(num==2) {
						buggers.removeValue(bu, true);
						num=0;
					}
					
					
				}else {
					if(xbot.getFacedRight()) {
						xbot.setPositionX(xbot.getPosition().x-2);
						xbot.setVelocity(xbot.Jump_Velocity-10);
						lifes--;
						labelXbotQTY.setText("x "+lifes);
					}else {
						xbot.setPositionX(xbot.getPosition().x+2);
						xbot.setVelocity(xbot.Jump_Velocity-10);
						lifes--;
						labelXbotQTY.setText("x "+lifes);
					}
				}
				

			}
		}
		
		
	}
	private void checkWater() {
		xbot.setBounds(xbot.getPosition().x, xbot.getPosition().y, xbot.getWidth(), xbot.getHeight());
		for (Rectangle wat:water) {
			if(wat.overlaps(xbot.getBounds())) {
				xbot.setPosition(boting.Start_X,boting.Start_Y);
				xbot.setFacedRight(true);
				lifes--;
				labelXbotQTY.setText("x "+lifes);
			}
		}
	}
	
	private void layBombers(Array<Bomber>bombers ) {
		Iterator<MapObject>mapObjectIterator=tiledMap.getLayers().get("enemy").getObjects().iterator();
		while(mapObjectIterator.hasNext()) {
			MapObject mapObject=mapObjectIterator.next();
			if(mapObject.getName().equals("bomber")) {
				Polygon polygon=((PolygonMapObject)mapObject).getPolygon();
				Bomber bomber=new Bomber(polygon.getTransformedVertices());
				bombers.add(bomber);
				
			}
		}
		
	}
	private void layWater(Array<Rectangle>water){
		Iterator<MapObject>mapObjectIterator=tiledMap.getLayers().get("enemy").getObjects().iterator();
		while(mapObjectIterator.hasNext()) {
			MapObject mapObject=mapObjectIterator.next();
			if(mapObject.getName().equals("water")) {
				Rectangle rectangle=((RectangleMapObject)mapObject).getRectangle();
				float x=rectangle.x*(1/16f);
				float y=rectangle.y*(1/16f);
				float width= rectangle.getWidth()*(1/16f);
				float height= rectangle.getHeight()*(1/16f);
				rectangle.set(x,y,width,height);
				water.add(rectangle);
			}
		}
	}
	private void layBuggers(Array<Bugger>buggers) {
		Iterator<MapObject>mapObjectIterator=tiledMap.getLayers().get("enemy").getObjects().iterator();
		while(mapObjectIterator.hasNext()) {
			MapObject mapObject=mapObjectIterator.next();
			if(mapObject.getName().equals("bugger")) {
				Rectangle rectangle=((RectangleMapObject)mapObject).getRectangle();
				float x=rectangle.x*(1/16f);
				float y=rectangle.y*(1/16f);
				Bugger bt=new Bugger(x,y,tmRenderer);
				buggers.add(bt);
			}
		}
	}

	
	private void keepXbotBounds() {
		minX=(int)(cam.position.x-(cam.viewportWidth/2f));
		maxX=mapWidth-xbot.getWidth();
		xbot.setPositionX(MathUtils.clamp(xbot.getPosition().x, minX, maxX));
		
	}
	private void keepCameraBounds() {
		minX=(int)(cam.viewportWidth/2f);
		maxX=mapWidth-(int)(cam.viewportWidth/2f);
		
		minY=(int)(cam.viewportHeight/2f);
		maxY=mapHeight-(int)(cam.viewportHeight/2f);
		
		cam.position.x=MathUtils.clamp(cam.position.x, minX, maxX);
		cam.position.y=MathUtils.clamp(cam.position.y, minY, maxY);
	}
	
	private void handInput() {
		if(Gdx.input.isKeyPressed(Keys.LEFT)||Gdx.input.isKeyPressed(Keys.A)) {
			xbot.setVelocityX(-xbot.Max_Velocity);
			if(xbot.getGround()) {
				xbot.state=xbot.state.Walking;
			}
			xbot.setFacedRight(false);
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)||Gdx.input.isKeyPressed(Keys.D)) {
			xbot.setVelocityX(xbot.Max_Velocity);
			if(xbot.getGround()) {
				xbot.state=xbot.state.Walking;
			}
			xbot.setFacedRight(true);
		}

		if(Gdx.input.isKeyPressed(Keys.UP)&&xbot.getGround()) {
			xbot.setVelocity(xbot.getVelocity().y+Xbot.Jump_Velocity);
			xbot.state=Xbot.State.Jumping;
			xbot.setGround(false);
		}
	}
	private void updateXbot(float delta){
		xbot.getVelocity().add(0,Xbot.Gravity);
		xbot.setVelocityX(MathUtils.clamp(xbot.getVelocity().x,-Xbot.Max_Velocity,Xbot.Max_Velocity));
		if(Math.abs(xbot.getVelocity().x)<1) {
			xbot.setVelocityX(0);
			if(xbot.getGround()) {
				xbot.state= Xbot.State.Standing;
			}
		}
		xbot.getVelocity().scl(delta);
		
//		if(xbot.getPosition().y<boting.Ground_Y) {
//
//			xbot.setVelocity(0);
//			xbot.setPositionY(2);
//			xbot.setGround(true);
//		}
		
		boundsXbot=tile.obtainCellPool();
		boundsXbot.set(xbot.getPosition().x,xbot.getPosition().y,xbot.getWidth(),xbot.getHeight());
		collideThePlatformOnXAxis();
		collideThePlatformOnYAxis();
		collectTheItem("crystalblue");
		collectTheItem("coinyellow");
		
		tile.freeCellPool(boundsXbot);
		
		
		xbot.getPosition().add(xbot.getVelocity());
		xbot.getVelocity().scl(1/delta);
		xbot.setVelocityX(xbot.getVelocity().x*Xbot.Damping);
	}
	
	private void collectTheItem(String layerName) {
		if(xbot.getVelocity().x>0) {
			startX=endX=(int)(xbot.getPosition().x+xbot.getWidth()+xbot.getVelocity().x);
		}else {
			startX=endX=(int)(xbot.getPosition().x+xbot.getVelocity().x);
		}
		startY=(int)(xbot.getPosition().y);
		endY=(int)(xbot.getPosition().y+xbot.getHeight());
		tile.getTiles(startX, startY, endX, endY, tiles,layerName);
		TiledMapTileLayer layer =(TiledMapTileLayer) (tmRenderer.getMap().getLayers().get(layerName));
		boundsXbot.x+=xbot.getVelocity().x;
		for(Rectangle tile :tiles) {
			if(boundsXbot.overlaps(tile)) {
				
				layer.setCell((int)tile.x,(int)tile.y, null);
				if(layerName=="crystalblue") {
					CrystalBlueQTY++;
					labelCrystalBlueQTY.setText("x "+CrystalBlueQTY);
					
				}
				if(layerName=="coinyellow") {
					ConinYellowQTY++;
					labelConinYellowQTY.setText("x "+ConinYellowQTY);
				}
				//System.out.println(CrystalBlueQTY+"   "+ConinYellowQTY+"   "+lifes);
				break;
			}
		}
		boundsXbot.x=xbot.getPosition().x;
		
		
		if(xbot.getVelocity().y>0) {
			startY=endY=(int)(xbot.getPosition().y+xbot.getWidth()+xbot.getVelocity().y);
		}else {
			startY=endY=(int)(xbot.getPosition().y+xbot.getVelocity().y);
		}
		startX=(int)(xbot.getPosition().x);
		endX=(int)(xbot.getPosition().x+xbot.getHeight());
		tile.getTiles(startX, startY, endX, endY, tiles,layerName);

		boundsXbot.x+=xbot.getVelocity().y;
		for(Rectangle tile :tiles) {
			
			if(boundsXbot.overlaps(tile)) {
				layer.setCell((int)tile.x,(int)tile.y, null);
				
				break;
			}
		}
		boundsXbot.x=xbot.getPosition().x;
	}
	private void collideThePlatformOnXAxis() {
		if(xbot.getVelocity().x>0) {
			startX=endX=(int)(xbot.getPosition().x+xbot.getWidth()+xbot.getVelocity().x);
		}else {
			startX=endX=(int)(xbot.getPosition().x+xbot.getVelocity().x);
		}
		startY=(int)(xbot.getPosition().y);
		endY=(int)(xbot.getPosition().y+xbot.getHeight());
		tile.getTiles(startX, startY, endX, endY, tiles, "platform");
		boundsXbot.x+=xbot.getVelocity().x;
		
		for(Rectangle tile :tiles) {
			
			if(boundsXbot.overlaps(tile)) {
				xbot.setVelocityX(0);
				break;
			}
		}
		boundsXbot.x=xbot.getPosition().x;
	}
	private void collideThePlatformOnYAxis() {
		if(xbot.getVelocity().y>0) {
			startY=endY=(int)(xbot.getPosition().y+xbot.getHeight()+xbot.getVelocity().y);
		}else {
			startY=endY=(int)(xbot.getPosition().y+xbot.getVelocity().y);
		}
		startX=(int)(xbot.getPosition().x);
		endX=(int)(xbot.getPosition().x+xbot.getWidth());
		tile.getTiles(startX, startY, endX, endY, tiles, "platform");
		boundsXbot.y+=xbot.getVelocity().y;
		
		for(Rectangle tile :tiles) {
			if(boundsXbot.overlaps(tile)) {
				if(xbot.getVelocity().y>0) {
					xbot.setPositionY(tile.y-xbot.getHeight());
				}else if(xbot.getVelocity().y<0) {
					xbot.setPositionY(tile.y+tile.height);
					xbot.setGround(true);
				}
				if(xbot.getPosition().y<boting.Ground_Y) {
					xbot.setPositionY(boting.Ground_Y);
				}
				xbot.setVelocity(0);
				break;
			}
		}
	}


	@Override
	public void dispose() {
		tiledMap.dispose();
		xbot.dispose();
	}
	
}
