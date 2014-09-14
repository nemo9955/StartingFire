package com.nemo9955.starting_fire.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.nemo9955.starting_fire.game.stage.GamePlayStage;
import com.nemo9955.starting_fire.game.world.World;
import com.nemo9955.starting_fire.storage.SF;
import com.nemo9955.starting_fire.utils.OrthoCamController;

public class Gameplay extends InputAdapter implements Screen {

	public GamePlayStage		stage;

	private World				world;

	public OrthographicCamera	camera;
	private ScreenViewport		viewport;
	private OrthoCamController	cameraController;

	public InputMultiplexer		inputs	= new InputMultiplexer();

	{
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		camera.update();
		viewport = new ScreenViewport(camera);
		viewport.setUnitsPerPixel(1.8f);

		stage = new GamePlayStage();

		cameraController = new OrthoCamController(camera);
		inputs.addProcessor(stage);
		inputs.addProcessor(cameraController);
		inputs.addProcessor(this);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(inputs);
		stage.restart();
		world = new World(15, 13, 127, 82);
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
		// SF.shapeRend.begin(ShapeType.Line);
		world.manage(delta);
		SF.spritesBatch.end();
		// SF.shapeRend.end();
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
	public boolean keyTyped( char character ) {
		switch ( character ) {
			case 'r' :
				world.generateNewWorldType();
				break;
			default :
				break;
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

	public World getWorld() {
		return world;
	}

	public void activateAllInputes( boolean allInputs ) {
		if ( allInputs )
			Gdx.input.setInputProcessor(SF.gameplay.inputs);
		else
			Gdx.input.setInputProcessor(SF.gameplay.stage);

	}
}
