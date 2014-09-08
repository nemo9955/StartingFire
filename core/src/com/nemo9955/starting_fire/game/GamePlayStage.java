package com.nemo9955.starting_fire.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.nemo9955.starting_fire.storage.SF;
import com.nemo9955.starting_fire.utils.CircularGroup;

public class GamePlayStage extends Stage {

	private Group			currentGroup;

	ImageButton				hudMenuBut	= new ImageButton(SF.skin, "IGpause");
	ImageButton				hudCenter	= new ImageButton(SF.skin, "vCenter_small");
	public Group			hudHolder	= new Group() {

											@Override
											public void setVisible( boolean visible ) {
												super.setVisible(visible);
												if ( visible )
													Gdx.input.setInputProcessor(SF.gameplay.inputs);
												else
													Gdx.input.setInputProcessor(SF.gameplay.stage);
											}
										};

	TextButton				menuResume	= new TextButton("Resume", SF.skin);
	TextButton				menuMMenu	= new TextButton("Main Menu", SF.skin);
	public Table			menuHolder	= new Table(SF.skin);

	public Group			entHolder	= new Group();
	public CircularGroup	entFire		= new CircularGroup(SF.shapeRend);

	public GamePlayStage() {
		super(new ScreenViewport(), SF.spritesBatch);
		((ScreenViewport) getViewport()).setUnitsPerPixel(1f);

		createHUD();

		creadeMenu();

		createEnt();
	}

	private void createHUD() {
		hudHolder.addActor(hudMenuBut);
		// TextureRegion vCen = Assets.ELEMENTS_PACK.asset(TextureAtlas.class).findRegion("vCenter", 0);
		// hudCenter.setBackground(new TextureRegionDrawable(vCen));
		// hudCenter.set
		hudHolder.addActor(hudCenter);

		hudHolder.addListener(hudListener);
		addActor(hudHolder);
	}

	ChangeListener	hudListener	= new ChangeListener() {

									@Override
									public void changed( ChangeEvent event, Actor actor ) {
										if ( hudMenuBut.isPressed() ) {
											changeGroup(menuHolder);
										} else if ( hudCenter.isPressed() ) {

										}

									}
								};

	private void creadeMenu() {
		menuHolder.setFillParent(true);
		menuHolder.setBackground("pix30");
		menuHolder.defaults().pad(20);
		menuHolder.add("Paused").row();
		menuHolder.add(menuResume).row();
		menuHolder.add(menuMMenu).row();

		menuHolder.addListener(menuListener);
		addActor(menuHolder);
	}

	private void createEnt() {
		entFire.setDraggable(false);
		entFire.setVisible(false);
		entFire.addActor(new TextButton("Fire1", SF.skin));
		entFire.addActor(new TextButton("Fire2", SF.skin));
		entFire.addActor(new TextButton("Fire3", SF.skin));
		entHolder.addActor(entFire);

		addActor(entHolder);
	}

	ChangeListener	menuListener	= new ChangeListener() {

										@Override
										public void changed( ChangeEvent event, Actor actor ) {
											if ( menuResume.isPressed() ) {
												changeGroup(hudHolder);
											} else if ( menuMMenu.isPressed() ) {

											}

										}
									};

	public void restart() {
		currentGroup = hudHolder;
		hudHolder.setVisible(true);
		menuHolder.setVisible(false);
	}

	public void manage( float delta ) {
		act();
		draw();
	}

	public void resize( int width, int height ) {
		getViewport().setScreenSize(width, height);
		getViewport().update(width, height, true);
		hudMenuBut.setPosition(getWidth() - hudMenuBut.getWidth(), getHeight() - hudMenuBut.getHeight());
		hudCenter.setPosition(getWidth() * 0.1f, getHeight() - hudCenter.getHeight());

		entFire.setAsCircle(100, 30);
		entFire.setActivInterval(1, 0, false, 60, false);
		entFire.setModifyAlpha(false);
		// entFire.setPosition(200, 200);// TODO

	}

	public void changeGroup( Group newGroup ) {
		currentGroup.setVisible(false);
		currentGroup = newGroup;
		currentGroup.setVisible(true);
	}

	@Override
	public void dispose() {
		super.dispose();;
	}

}
