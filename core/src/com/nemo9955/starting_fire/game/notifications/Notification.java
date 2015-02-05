package com.nemo9955.starting_fire.game.notifications;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nemo9955.starting_fire.storage.SF;

public abstract class Notification {

	private String title = "empty";
	private String lore = "empty";
	private ImageButton barButt;
	private Table holder = new Table(SF.skin);

	public Notification(ImageButton button) {
		this.barButt = button;
		button.getStyle().pressedOffsetY = 2;
		barButt.addListener(listener);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLore() {
		return lore;
	}

	public void setLore(String lore) {
		this.lore = lore;
	}

	public ImageButton getButton() {
		return barButt;
	}

	public Table getHolder() {
		return holder;
	}

	public void reset() {

	}

	private ChangeListener listener = new ChangeListener() {

		@Override
		public void changed(ChangeEvent event, Actor actor) {
			if (NotificationManager.currentNot != Notification.this)
				NotificationManager.viewNotif(Notification.this);
			else
				NotificationManager.hideNotif();
		}
	};

}
