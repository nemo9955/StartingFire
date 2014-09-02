package com.nemo9955.starting_fire.states;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nemo9955.starting_fire.storage.SF;

public class MainMenu implements State<MenuController> {

	private Table	table	= new Table(SF.skin);

	{
		table.setFillParent(true);
		final TextButton play = new TextButton("Play", SF.skin);

		table.addListener(new ChangeListener() {

			@Override
			public void changed( ChangeEvent event, Actor actor ) {
				if ( play.isPressed() )
					SF.game.setScreen(SF.gameplay);
			}
		});
		table.add(play);
	}

	@Override
	public void enter( MenuController entity ) {
		entity.stage.addActor(table);
	}

	@Override
	public void update( MenuController entity ) {}

	@Override
	public void exit( MenuController entity ) {
		entity.stage.getActors().removeValue(table, false);
	}

	@Override
	public boolean onMessage( MenuController entity, Telegram telegram ) {
		return false;
	}

}
