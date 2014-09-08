package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.nemo9955.starting_fire.game.ashley.components.CActor;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CCollision;
import com.nemo9955.starting_fire.game.ashley.components.CCoordinate;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.game.tiles.HexBase;
import com.nemo9955.starting_fire.game.tiles.HexElements;
import com.nemo9955.starting_fire.game.world.World;
import com.nemo9955.starting_fire.storage.SF;

public class EntityManager {

	private PooledEngine	engine;
	private World			world;

	private Entity			entity;

	public EntityManager(PooledEngine eng, World world) {
		this.engine = eng;
		this.world = world;
	}

	public Entity makeTile( HexBase hex, int q, int r, boolean offset ) {
		designEntity();
		addCoordinates(q, r);

		addTexture(hex.getTex());

		float x = world.hexWidht * 0.75f * q;
		float y;
		if ( offset )
			y = ((q & 1) == 1 ? world.hexHeight * 0.5f : 0) - (world.hexHeight * r);
		else
			y = -(world.hexHeight * 0.5f * (r * 2 + 1 + q));

		addPosition(x, y);
		addCollision(x, y, world.hexWidht, world.hexHeight);

		if ( q == 0 && r == 0 )
			HexElements.FIRE.useElement(this);

		return entity;
	}

	public EntityManager designEntity() {
		entity = engine.createEntity();
		CWorld cw = engine.createComponent(CWorld.class);
		cw.world = world;
		entity.add(cw);
		return this;
	}

	public EntityManager designEntity( HexBase base ) {
		designEntity();
		addTexture(SF.atlas.findRegion(base.getName()));
		return this;
	}

	public EntityManager addActor( Actor actor ) {
		CActor act = engine.createComponent(CActor.class);
		act.actor = actor;
		entity.add(act);
		return this;
	}

	public EntityManager addAnimation( Animation animation ) {
		CAnimation anim = engine.createComponent(CAnimation.class);
		anim.anim = animation;
		entity.add(anim);
		return this;
	}

	public EntityManager addCollision( float x, float y, float width, float height ) {
		CCollision col = engine.createComponent(CCollision.class);
		col.setColide(x, y, width, height);
		entity.add(col);
		return this;
	}

	public EntityManager addTexture( TextureRegion region ) {
		CTexture tex = entity.getComponent(CTexture.class);
		if ( tex == null )
			tex = engine.createComponent(CTexture.class);
		tex.tex.add(region);
		entity.add(tex);
		return this;
	}

	public EntityManager addPosition( float x, float y ) {
		CPosition poz = engine.createComponent(CPosition.class);
		poz.x = x;
		poz.y = y;
		entity.add(poz);
		return this;
	}

	public EntityManager addCoordinates( int q, int r ) {
		CCoordinate coo = engine.createComponent(CCoordinate.class);
		coo.r = r;
		coo.q = q;
		entity.add(coo);
		return this;
	}

	public Entity createEntity() {
		return entity;
	}
}
