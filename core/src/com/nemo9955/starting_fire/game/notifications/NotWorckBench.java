package com.nemo9955.starting_fire.game.notifications;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nemo9955.starting_fire.game.events.Event;
import com.nemo9955.starting_fire.game.events.IEventListener;
import com.nemo9955.starting_fire.storage.SF;

public class NotWorckBench extends Notification implements IEventListener {

	public static NotWorckBench	intance	= new NotWorckBench();

	final int					call_at	= 3;
	int							current	= 0;

	public NotWorckBench() {
		super(new ImageButton(SF.skin.getDrawable("checked")));
		setLore("Your pepole want a workbench so they can start making bildings");
		setTitle("Create a workbench");
		getButton().addListener(new ChangeListener() {

			@Override
			public void changed( ChangeEvent event, Actor actor ) {}
		});
		Event.Fire_Lit.addListener(this);
	}

	@Override
	public void called() {
		current++;
		if ( current == call_at ) {
			NotificationManager.registerNotif(this);
		}
	}

}
