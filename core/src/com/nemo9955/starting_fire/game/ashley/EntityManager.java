package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nemo9955.starting_fire.game.ashley.components.CActor;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CCollision;
import com.nemo9955.starting_fire.game.ashley.components.CCoordinate;
import com.nemo9955.starting_fire.game.ashley.components.CIHit;
import com.nemo9955.starting_fire.game.ashley.components.CIHit.IHitable;
import com.nemo9955.starting_fire.game.ashley.components.CIUpdate;
import com.nemo9955.starting_fire.game.ashley.components.CIUpdate.IUpdatable;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.game.stage.IActable;
import com.nemo9955.starting_fire.game.tiles.FireFactory;
import com.nemo9955.starting_fire.game.tiles.HexBase;
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
			FireFactory.instance.useElement(this);

		return getEntity();
	}

	public EntityManager designEntity() {
		setEntity(engine.createEntity());
		CWorld cw = engine.createComponent(CWorld.class);
		cw.world = world;
		getEntity().add(cw);
		return this;
	}

	public EntityManager designEntity( HexBase base ) {
		designEntity();
		addTexture(SF.atlas.findRegion(base.getName()));
		return this;
	}

	public EntityManager addInteract( IHitable interact ) {
		CIHit inter = engine.createComponent(CIHit.class);
		inter.interact = interact;
		getEntity().add(inter);
		return this;
	}

	public EntityManager addUpdate( IUpdatable upd ) {
		CIUpdate update = engine.createComponent(CIUpdate.class);
		update.update = upd;
		getEntity().add(update);
		return this;
	}

	public EntityManager addActor( IActable actor ) {
		CActor act = engine.createComponent(CActor.class);
		act.actor = actor;
		getEntity().add(act);
		return this;
	}

	public EntityManager addAnimation( Animation animation ) {
		CAnimation anim = engine.createComponent(CAnimation.class);
		anim.anim = animation;
		getEntity().add(anim);
		return this;
	}

	public EntityManager addCollision( float x, float y, float width, float height ) {
		CCollision col = engine.createComponent(CCollision.class);
		col.setColide(x, y, width, height);
		getEntity().add(col);
		return this;
	}

	public EntityManager addTexture( TextureRegion region ) {
		CTexture tex = getEntity().getComponent(CTexture.class);
		if ( tex == null )
			tex = engine.createComponent(CTexture.class);
		tex.tex.add(region);
		getEntity().add(tex);
		return this;
	}

	public EntityManager addPosition( float x, float y ) {
		CPosition poz = engine.createComponent(CPosition.class);
		poz.x = x;
		poz.y = y;
		getEntity().add(poz);
		return this;
	}

	public EntityManager addCoordinates( int q, int r ) {
		CCoordinate coo = engine.createComponent(CCoordinate.class);
		coo.r = r;
		coo.q = q;
		getEntity().add(coo);
		return this;
	}

	public Entity getEntity() {
		return entity;
	}

	public EntityManager setEntity( Entity entity ) {
		this.entity = entity;
		return this;
	}
}
