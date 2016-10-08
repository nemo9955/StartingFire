package com.nemo9955.starting_fire.game.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.nemo9955.starting_fire.game.ashley.components.CMap;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.storage.SF;

public class PlayerBuilder extends InputAdapter {

	private static AtlasRegion playerImage = SF.atlas.findRegion("player");

	Entity entity;
	World world;
	CPosition pozition;

	private float speed = 10;

	public Entity useElement(Entity entity) {
		this.entity = entity;
		world = CMap.world.get(entity).world;

		pozition = world.addPosition(entity, 0, 0);
		world.addTexture(entity, playerImage);

		return entity;
	}

	@Override
	public boolean keyTyped(char character) {
		switch (character) {
		case 'w':
			pozition.y += speed;
			break;
		case 's':
			pozition.y -= speed;
			break;
		case 'd':
			pozition.x += speed;
			break;
		case 'a':
			pozition.x -= speed;
			break;
		default:
			break;
		}
		return false;
	}

}
