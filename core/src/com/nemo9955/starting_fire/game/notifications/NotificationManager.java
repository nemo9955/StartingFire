package com.nemo9955.starting_fire.game.notifications;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nemo9955.starting_fire.game.events.IEventListener;
import com.nemo9955.starting_fire.game.stage.GamePlayStage;
import com.nemo9955.starting_fire.storage.SF;

public class NotificationManager {

	static Window			current		= new Window("empty", SF.skin);
	static TextArea			lore		= new TextArea("empty field", SF.skin) {

											@Override
											public float getPrefWidth() {
												return 400;
											};
										};

	static TextButton		later		= new TextButton("Later", SF.skin);

	static ChangeListener	listener	= new ChangeListener() {

											@Override
											public void changed( ChangeEvent event, Actor actor ) {
												hideNotif();
											}
										};
	static {
		SF.gameplay.stage.hudHolder.addActor(current);
		lore.setTouchable(Touchable.disabled);
		later.addListener(listener);
		current.setVisible(false);
		// current.setModal(true);
		current.setKeepWithinStage(true);
	}

	public static void registerNotif( Notification not ) {
		not.reset();
		if ( not instanceof IEventListener ) {
			((IEventListener) not).register();
		}
	}

	public static void addNotifToBar( Notification not ) {
		SF.gameplay.stage.hudNotifHolder.addActor(not.getButton());
	}

	public static void viewNotif( Notification not ) {
		addNotifToBar(not);

		GamePlayStage stage = SF.gameplay.stage;
		current.clearChildren();
		current.setVisible(true);
		current.defaults().pad(10);
		current.pad(40);
		current.setTitle(not.getTitle());
		lore.setText(not.getLore());
		lore.setPrefRows(/* lore.getLines() */4);
		current.add(lore);
		current.row();
		current.add(not.getHolder());
		current.row();
		current.add(later);
		current.pack();
		current.setPosition((stage.getWidth() / 2)-current.getWidth()/2, (stage.getHeight() / 2)-current.getWidth()/2 );

	}

	public static void hideNotif() {
		current.setVisible(false);
	}

	public static void addNviewNotif( Notification not ) {
		addNotifToBar(not);
		viewNotif(not);
	}

	public static void deleteNotif( Notification not ) {
		hideNotif();
		SF.gameplay.stage.hudNotifHolder.removeActor(not.getButton());

	}
}
