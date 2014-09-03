package com.nemo9955.starting_fire.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.nemo9955.starting_fire.hexagons.HexaList;
import com.nemo9955.starting_fire.storage.Assets;
import com.nemo9955.starting_fire.storage.SF;
import com.nemo9955.starting_fire.utils.OrthoCamController;

public class Gameplay extends ScreenAdapter {

	private TiledMap					map;
	private OrthographicCamera			camera;
	private OrthoCamController			cameraController;
	private HexagonalTiledMapRenderer	renderer;

	{
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		camera.update();

		cameraController = new OrthoCamController(camera);

		TextureAtlas hexas = Assets.HEXAS.asset(TextureAtlas.class);

		TiledMapTile[] tiles = new TiledMapTile[HexaList.values().length];

		for (int i = 0; i < HexaList.values().length; i++) {
			tiles[i] = new StaticTiledMapTile(hexas.findRegion(HexaList.values()[i].toString()));
			// System.out.println(hexas.getRegions().get(i).name);
		}
		// tiles[0] = new StaticTiledMapTile(hexas.findRegion("water-hex"));
		// tiles[1] = new StaticTiledMapTile(hexas.findRegion("sand-hex"));
		// tiles[2] = new StaticTiledMapTile(hexas.findRegion("gras-hex"));

		map = new TiledMap();
		MapLayers layers = map.getLayers();
		TiledMapTileLayer floorL = new TiledMapTileLayer(10, 10, 127, 82);
		for (int y = 0; y < floorL.getHeight(); y++) {
			for (int x = 0; x < floorL.getWidth(); x++) {
				int id = (int) (Math.random() * tiles.length);
				Cell cell = new Cell();
				cell.setTile(tiles[id]);
				floorL.setCell(x, y, cell);
			}
		}
		// floorL.getWidth(), floorL.getHeight(), (int) floorL.getTileWidth(), (int) floorL.getTileHeight()
		TiledMapTileLayer onflL = new TiledMapTileLayer(10, 10, 127, 82);
		Cell cell = new Cell();
		Array<StaticTiledMapTile> foc = new Array<StaticTiledMapTile>(3);
		foc.add(new StaticTiledMapTile(hexas.findRegion("water")));
		foc.add(new StaticTiledMapTile(hexas.findRegion("sand")));
		foc.add(new StaticTiledMapTile(hexas.findRegion("small_fire", 3)));
		cell.setTile(new AnimatedTiledMapTile(0.3f, foc));
		onflL.setCell(0, 0, cell);

		layers.add(onflL);
		layers.add(floorL);

		renderer = new HexagonalTiledMapRenderer(map, 1f, SF.spritesBatch);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(cameraController);
	}

	@Override
	public void render( float delta ) {
		Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		renderer.setView(camera);
		renderer.render();
	}

	@Override
	public void resize( int width, int height ) {
		camera.setToOrtho(false, width, height);
		camera.update();
	}

	@Override
	public void dispose() {}

}
