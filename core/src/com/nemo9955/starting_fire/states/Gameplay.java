package com.nemo9955.starting_fire.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.nemo9955.starting_fire.game.GamePlayStage;
import com.nemo9955.starting_fire.game.world.HexWorld;
import com.nemo9955.starting_fire.storage.SF;
import com.nemo9955.starting_fire.utils.OrthoCamController;

public class Gameplay extends InputAdapter implements Screen {

	private GamePlayStage		stage;

	private HexWorld			world;

	private OrthographicCamera	camera;
	private ScreenViewport		viewport;
	public OrthoCamController	cameraController;

	public InputMultiplexer		inputs	= new InputMultiplexer();

	{
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		camera.update();
		viewport = new ScreenViewport(camera);
		viewport.setUnitsPerPixel(1.9f);

		stage = new GamePlayStage();

		cameraController = new OrthoCamController(camera);
	}

	@Override
	public void show() {
		inputs.addProcessor(stage);
		Gdx.input.setInputProcessor(inputs);
		stage.restart();
		world = new HexWorld(4, 4, 127, 82);
		camera.position.setZero();
	}

	@Override
	public void render( float delta ) {
		Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		SF.spritesBatch.setProjectionMatrix(camera.combined);
		SF.shapeRend.setProjectionMatrix(camera.combined);
		SF.spritesBatch.begin();
		SF.shapeRend.begin(ShapeType.Line);
		world.manage(delta);
		SF.spritesBatch.end();
		SF.shapeRend.end();
		stage.manage(delta);
	}

	@Override
	public void resize( int width, int height ) {
		stage.resize(width, height);
		viewport.update(width, height);
	}

	@Override
	public boolean touchDown( int screenX, int screenY, int pointer, int button ) {
		if ( button == 2 ) {
			Ray ray = camera.getPickRay(screenX, screenY);
			world.hit(ray.origin.x, ray.origin.y);
		}
		return false;
	}

	@Override
	public void hide() {
		world.dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

}
