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
import com.nemo9955.starting_fire.game.tiles.factories.FirePlace;
import com.nemo9955.starting_fire.game.tiles.factories.LumberJack;
import com.nemo9955.starting_fire.storage.SF;

public class NotLumberJack extends Notification implements IEventListener {

	public static NotLumberJack	inst	= new NotLumberJack();

	private TextButton			makeBut	= new TextButton("Make Lumber Jack", SF.skin);

	final int					call_at	= 1;
	int							current	= 0;

	private NotLumberJack() {
		super(new ImageButton(SF.skin.getDrawable("checked")));
		setTitle("Make a LumberJack post !");
		setLore("You are running out of wood. Why not make a place to process it ?");
		getHolder().add(makeBut);
		makeBut.addListener(new ChangeListener() {

			@Override
			public void changed( ChangeEvent event, Actor actor ) {
				Entity entity = FirePlace.fire;
				CInfo i = CM.Info.get(entity);

				CCoordinate co = CM.Coor.get(entity);
				LumberJack.useElement(i.world.getHex(co.q + 3, co.r - 1));
				NotificationManager.deleteNotif(NotLumberJack.this);
			}
		});
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
