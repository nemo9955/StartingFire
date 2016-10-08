package com.nemo9955.starting_fire.game.notifications;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nemo9955.starting_fire.game.ashley.components.CCoordinate;
import com.nemo9955.starting_fire.game.ashley.components.CMap;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.game.events.Events;
import com.nemo9955.starting_fire.game.events.IEventListener;
import com.nemo9955.starting_fire.game.tiles.factories.LumberJack;
import com.nemo9955.starting_fire.storage.SF;

public class NotLumberJack extends Notification implements IEventListener {

	public static NotLumberJack inst = new NotLumberJack();

	public TextButton makeBut = new TextButton("Make Lumber Jack", SF.skin);

	final int call_at = 3;

	private NotLumberJack() {
		super(new ImageButton(SF.skin.getDrawable("checked")));
		setLore("You are running out of wood. Why not make a place to process it ?");
		setTitle("Make a LumberJack post !");
		getHolder().add(makeBut);

		makeBut.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Entity entity = (Entity) actor.getUserObject();
//				System.out.println(entity);
				CWorld i = CMap.world.get(entity);
				CCoordinate co = CMap.coordinate.get(entity);

				LumberJack.useElement(i.world.getHex(co.q + 3, co.r - 1));
				NotificationManager.deleteNotif(NotLumberJack.this);
			}
		});
	}

	@Override
	public void called(int quantity) {
		if (quantity >= call_at) {
			NotificationManager.activateNotif(this);
			unregister();
		}
	}

	@Override
	public void reset() {
		super.reset();
	}

	@Override
	public void register() {
		Events.Fire_Lit.addListener(this);
	}

	@Override
	public void unregister() {
		Events.Fire_Lit.getListeners().removeValue(this, false);
	}
}
