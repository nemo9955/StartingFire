package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.nemo9955.starting_fire.game.ashley.IInteractable;

public class CInteract extends Component implements Poolable {

	public IInteractable	interact;

	@Override
	public void reset() {
		interact = null;
	}

}
