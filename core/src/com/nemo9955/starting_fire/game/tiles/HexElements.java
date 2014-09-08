package com.nemo9955.starting_fire.game.tiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.nemo9955.starting_fire.game.ashley.EntityManager;
import com.nemo9955.starting_fire.storage.SF;

public enum HexElements {
	FIRE {

		private Animation	fireAnim	= new Animation(0.1f, SF.atlas.findRegions("small_fire"), PlayMode.LOOP_PINGPONG);

		@Override
		public void useElement( EntityManager manager ) {
			manager.addAnimation(fireAnim);
			manager.addActor(SF.gameplay.stage.entFire);
		}
	};

	abstract public void useElement( EntityManager manager );
}
