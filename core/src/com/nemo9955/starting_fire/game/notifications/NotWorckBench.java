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
import com.nemo9955.starting_fire.game.tiles.factories.WorkBench;
import com.nemo9955.starting_fire.storage.SF;

public class NotWorckBench extends Notification implements IEventListener {

	public static NotWorckBench inst = new NotWorckBench();

	private TextButton makeBut = new TextButton("Make WorkBench", SF.skin);

	final int call_at = 2;

	private NotWorckBench() {
		super(new ImageButton(SF.skin.getDrawable("checked")));
		setLore("Your pepole want a workbench so they can start making bildings");
		setTitle("Create a workbench");
		getHolder().add(makeBut);

		makeBut.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor act) {
				Entity entity = FirePlace.fire;
				CInfo i = CM.Info.get(entity);

				CCoordinate co = CM.Coor.get(entity);
				WorkBench.useElement(i.world.getHex(co.q - 3, co.r - 1));
				NotificationManager.deleteNotif(NotWorckBench.this);
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
