package com.nemo9955.starting_fire.game.notifications;

import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.nemo9955.starting_fire.storage.SF;

public class NotificationManager {

	public static void registerNotif( Notification not ) {
		SF.gameplay.stage.hudNotifHolder.addActor(not.getButton());
	}

	public static void viewNotif( Notification not ) {
		Window w = new Window("aer", SF.skin);
		w.
	}
}
