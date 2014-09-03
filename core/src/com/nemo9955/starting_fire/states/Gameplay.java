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
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
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

		map = new TiledMap();
		TiledMapTile[] tiles = new TiledMapTile[3];
		tiles[0] = new StaticTiledMapTile(hexas.findRegion("water-hex"));
		tiles[1] = new StaticTiledMapTile(hexas.findRegion("sand-hex"));
		tiles[2] = new StaticTiledMapTile(hexas.findRegion("gras-hex"));

		MapLayers layers = map.getLayers();
		for (int l = 0; l < 1; l++) {
			TiledMapTileLayer layer = new TiledMapTileLayer(10, 10, 128, 83);
			for (int y = 0; y < layer.getHeight(); y++) {
				for (int x = 0; x < layer.getWidth(); x++) {
					int id = (int) (Math.random() * 3);
					Cell cell = new Cell();
					cell.setTile(tiles[id]);

					layer.setCell(x, y, cell);
				}
			}
			layers.add(layer);
		}

		renderer = new HexagonalTiledMapRenderer(map, SF.spritesBatch);
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
