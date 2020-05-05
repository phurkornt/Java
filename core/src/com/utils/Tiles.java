package com.utils;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Tiles {
	private OrthogonalTiledMapRenderer tmRenderer;
	private Pool<Rectangle> rectPool=new Pool<Rectangle>() {
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};
	public Tiles(OrthogonalTiledMapRenderer tmRenderer) {
		this.tmRenderer=tmRenderer;
		
	}
	public void getTiles(int startX,int startY,int stopX,int stopY,Array<Rectangle>tiles,String layerName) {
		TiledMapTileLayer layer=(TiledMapTileLayer) (tmRenderer.getMap().getLayers().get(layerName));
		rectPool.freeAll(tiles);
		tiles.clear();
		for(int y=startY;y<=stopY;y++) {
			for(int x=startX;x<=stopX;x++) {
				Cell cell =layer.getCell(x, y);
				if(cell!=null) {
					Rectangle rect=rectPool.obtain();
					rect.set(x,y,1,1);
					tiles.add(rect);
				}
			}
		}
	}
	public Rectangle obtainCellPool() {
		return rectPool.obtain();
	}
	public void freeCellPool(Rectangle bounds) {
		rectPool.free(bounds);
	}
	
}





