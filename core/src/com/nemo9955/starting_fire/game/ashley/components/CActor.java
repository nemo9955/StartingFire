package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Pool.Poolable;

//TODO implement so it extends Actor
public class CActor extends VerticalGroup implements Poolable, Component {

	// public IActable actor;
	// private int DEGEABA;

	@Override
	public void reset() {
		// actor = null;
		clear();
		remove();
		setVisible(false);
		setUserObject(null);
	}

	// public static interface IActable {
	//
	// public void resize(int maxWidth, int maxHeight);
	//
	// public Group getGroup();
	//
	// }

}
