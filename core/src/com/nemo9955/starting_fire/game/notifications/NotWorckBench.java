package com.nemo9955.starting_fire.game.notifications;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nemo9955.starting_fire.game.ashley.components.CCoordinate;
import com.nemo9955.starting_fire.game.ashley.components.CInfo;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.events.Events;
import com.nemo9955.starting_fire.game.events.IEventListener;
import com.nemo9955.starting_fire.game.tiles.FireFactory;
import com.nemo9955.starting_fire.game.tiles.WorkBFactory;
import com.nemo9955.starting_fire.storage.SF;

public class NotWorckBench extends Notification implements IEventListener {

	public static NotWorckBench	inst		= new NotWorckBench();

	private TextButton			entMWorkB	= new TextButton("Make WorkBench", SF.skin);

	final int					call_at		= 1;
	int							current		= 0;

	private NotWorckBench() {
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
				CInfo i = CM.Info.get(entity);

				CCoordinate co = CM.Coor.get(entity);
				WorkBFactory.useElement(i.world.getHex(co.q + 2, co.r - 1));
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
	public void reset() {
		super.reset();
		current = 0;
	}

	@Override
	public void register() {
		Events.Fire_Lit.addListener(this);
	}

}
