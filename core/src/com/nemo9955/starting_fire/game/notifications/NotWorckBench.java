package com.nemo9955.starting_fire.game.notifications;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nemo9955.starting_fire.game.ashley.EntityManager;
import com.nemo9955.starting_fire.game.ashley.components.CCoordinate;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.game.events.Events;
import com.nemo9955.starting_fire.game.events.IEventListener;
import com.nemo9955.starting_fire.game.tiles.FireFactory;
import com.nemo9955.starting_fire.game.tiles.WorckBFactory;
import com.nemo9955.starting_fire.storage.SF;

public class NotWorckBench extends Notification implements IEventListener {

	private static TextButton	entMWorkB	= new TextButton("Make WorkBench", SF.skin);

	final int					call_at		= 3;
	int							current		= 0;

	public NotWorckBench() {
		super(new ImageButton(SF.skin.getDrawable("checked")));
		setLore("Your pepole want a workbench so they can start making bildings");
		setTitle("Create a workbench");
		getButton().addListener(new ChangeListener() {

			@Override
			public void changed( ChangeEvent event, Actor actor ) {}
		});

		entMWorkB.addListener(new ChangeListener() {

			@Override
			public void changed( ChangeEvent event, Actor act ) {
				Entity entity = FireFactory.fire;
				CWorld wo = CM.Wor.get(entity);

				CCoordinate co = CM.Coor.get(entity);
				EntityManager manager = wo.world.manager;
				manager.setEntity(wo.world.getHex(co.q + 2, co.r - 1));

				WorckBFactory.useElement(manager);
				NotificationManager.deleteNotif(NotWorckBench.this);
			}
		});

		getHolder().add(entMWorkB);
	}

	@Override
	public void called() {
		current++;
		if ( current == call_at ) {
			NotificationManager.addNviewNotif(this);
		}
	}

	@Override
	public void register() {
		Events.Fire_Lit.addListener(this);
	}

}
