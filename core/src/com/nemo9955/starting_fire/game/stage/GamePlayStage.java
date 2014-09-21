package com.nemo9955.starting_fire.game.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.nemo9955.starting_fire.game.ashley.components.CActor.IActable;
import com.nemo9955.starting_fire.game.tiles.FireFactory;
import com.nemo9955.starting_fire.storage.SF;

public class GamePlayStage extends Stage {

	private Group			currentGroup;

	ImageButton				hudNotifBut		= new ImageButton(SF.skin, "notification");
	public HorizontalGroup	hudNotifHolder	= new HorizontalGroup();
	ScrollPane				hudNotifScroll	= new ScrollPane(hudNotifHolder);
	ImageButton				hudMenuBut		= new ImageButton(SF.skin, "IGpause");
	ImageButton				hudCenter		= new ImageButton(SF.skin, "vCenter_small");
	public Group			hudHolder		= new Group() {

												@Override
												public void setVisible( boolean visible ) {
													super.setVisible(visible);
													SF.gameplay.activateAllInputes(visible);
												}
											};

	TextButton				menuResume		= new TextButton("Resume", SF.skin);
	TextButton				menuMMenu		= new TextButton("Main Menu", SF.skin);
	public Table			menuHolder		= new Table(SF.skin);

	private Array<IActable>	entActors		= new Array<IActable>();

	public GamePlayStage() {
		super(new ScreenViewport(), SF.spritesBatch);
		((ScreenViewport) getViewport()).setUnitsPerPixel(1f);

		createHUD();

		creadeMenu();
	}

	private void createHUD() {
		hudNotifBut.setSize(40, 55);

		hudHolder.addActor(hudMenuBut);
		hudHolder.addActor(hudCenter);
		hudHolder.addActor(hudNotifBut);

		hudNotifScroll.setWidth(getWidth() - hudNotifBut.getWidth() * 1.08f);
		hudNotifScroll.setHeight(50);
		hudNotifScroll.setVisible(false);
		hudHolder.addActor(hudNotifScroll);

		hudHolder.addListener(hudListener);
		addActor(hudHolder);
	}

	ChangeListener	hudListener	= new ChangeListener() {

									@Override
									public void changed( ChangeEvent event, Actor actor ) {
										if ( hudMenuBut.isPressed() ) {
											changeGroup(menuHolder);
										} else if ( hudCenter.isPressed() ) {
											FireFactory.centerCamera();
										}
										else if ( hudNotifBut.isPressed() ) {
											hudNotifScroll.setVisible(!hudNotifScroll.isVisible());
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

	ChangeListener	menuListener	= new ChangeListener() {

										@Override
										public void changed( ChangeEvent event, Actor actor ) {
											if ( menuResume.isPressed() ) {
												changeGroup(hudHolder);
											} else if ( menuMMenu.isPressed() ) {
												SF.game.setScreen(SF.mc);
											}

										}
									};

	public void restart() {
		entActors.clear();
		hudNotifHolder.clearChildren();
		currentGroup = hudHolder;
		hudHolder.setVisible(true);
		menuHolder.setVisible(false);
	}

	public void resize( int width, int height ) {
		getViewport().setScreenSize(width, height);
		getViewport().update(width, height, true);
		hudNotifBut.setPosition(0, 0);
		hudMenuBut.setPosition(getWidth() - hudMenuBut.getWidth(), getHeight() - hudMenuBut.getHeight());
		hudNotifScroll.setPosition(hudNotifBut.getWidth() * 1.08f, 0);
		hudCenter.setPosition(getWidth() * 0.1f, getHeight() - hudCenter.getHeight());

		for (IActable act : entActors)
			act.resize(width, height);
	}

	public void changeGroup( Group newGroup ) {
		currentGroup.setVisible(false);
		currentGroup = newGroup;
		currentGroup.setVisible(true);
	}

	public void manage( float delta ) {
		act();
		draw();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	public void addEntActor( IActable act ) {
		entActors.add(act);
		addActor(act.getGroup());
	}

}
