package com.nemo9955.starting_fire.game;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.nemo9955.starting_fire.storage.SF;

public class GamePlayStage extends Stage {

	Group		hudHolder	= new Group();
	ImageButton	hudMenuBut	= new ImageButton(SF.skin, "IGpause");
	ImageButton	hudCenter	= new ImageButton(SF.skin, "vCenter_small");

	Table		menuHolder	= new Table(SF.skin);
	TextButton	menuResume	= new TextButton("Resume", SF.skin);
	TextButton	menuMMenu	= new TextButton("Main Menu", SF.skin);

	public GamePlayStage() {
		super(new ScreenViewport(), SF.spritesBatch);
		((ScreenViewport) getViewport()).setUnitsPerPixel(1f);

		createHUD();

		creadeMenu();
	}

	private void creadeMenu() {
		menuHolder.defaults().pad(20);
		menuHolder.add("Paused");
		menuHolder.add(menuResume);
		menuHolder.add(menuMMenu);

		addActor(menuHolder);
	}

	private void createHUD() {
		hudHolder.addActor(hudMenuBut);
		// TextureRegion vCen = Assets.ELEMENTS_PACK.asset(TextureAtlas.class).findRegion("vCenter", 0);
		// hudCenter.setBackground(new TextureRegionDrawable(vCen));
		// hudCenter.set
		hudHolder.addActor(hudCenter);

		addActor(hudHolder);
	}

	public void restart() {
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
	}

	@Override
	public void dispose() {
		super.dispose();;
	}

}
