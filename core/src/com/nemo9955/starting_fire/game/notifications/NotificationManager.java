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

	static Notification currentNot = null;

	static Window notViewer = new Window("empty", SF.skin);
	static TextArea lore = new TextArea("empty field", SF.skin) {

		@Override
		public float getPrefWidth() {
			return 400;
		};
	};

	static TextButton later = new TextButton("Later", SF.skin);

	static ChangeListener listener = new ChangeListener() {

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			hideNotif();
		}
	};
	static {
		SF.gameplay.stage.hudHolder.addActor(notViewer);
		lore.setTouchable(Touchable.disabled);
		later.addListener(listener);
		notViewer.setVisible(false);
		// current.setModal(true);
		notViewer.setKeepWithinStage(true);
	}

	public static void registerNotif(Notification not) {
		not.reset();
		if (not instanceof IEventListener) {
			((IEventListener) not).register();
		}
	}

	public static void addNotifToBar(Notification not) {
		SF.gameplay.stage.hudNotifHolder.addActor(not.getButton());
	}

	public static void viewNotif(Notification not) {
		// addNotifToBar(not);
		currentNot = not;

		GamePlayStage stage = SF.gameplay.stage;
		notViewer.clearChildren();
		notViewer.setVisible(true);
		notViewer.defaults().pad(10);
		notViewer.pad(40);
		notViewer.getTitleLabel().setText(not.getTitle());
		lore.setText(not.getLore());
		lore.setPrefRows(/* lore.getLines() */4);
		notViewer.add(lore);
		notViewer.row();
		notViewer.add(not.getHolder());
		notViewer.row();
		notViewer.add(later);
		notViewer.pack();
		notViewer.setPosition((stage.getWidth() / 2) - notViewer.getWidth() / 2, (stage.getHeight() / 2) - notViewer.getWidth()
				/ 2);

	}

	public static void hideNotif() {
		notViewer.setVisible(false);
		currentNot = null;
	}

	public static void activateNotif(Notification not) {
		addNotifToBar(not);
		viewNotif(not);
	}

	public static void deleteNotif(Notification not) {
		hideNotif();
		SF.gameplay.stage.hudNotifHolder.removeActor(not.getButton());

	}
}
