package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CCollision;
import com.nemo9955.starting_fire.game.ashley.components.CCoordinate;
import com.nemo9955.starting_fire.game.ashley.components.CIActor;
import com.nemo9955.starting_fire.game.ashley.components.CIActor.IActable;
import com.nemo9955.starting_fire.game.ashley.components.CIHit;
import com.nemo9955.starting_fire.game.ashley.components.CIHit.IHitable;
import com.nemo9955.starting_fire.game.ashley.components.CIUpdate;
import com.nemo9955.starting_fire.game.ashley.components.CIUpdate.IUpdatable;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CTelegraph;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CTimer;
import com.nemo9955.starting_fire.game.tiles.FireFactory;
import com.nemo9955.starting_fire.game.tiles.HexBase;
import com.nemo9955.starting_fire.game.world.World;

public class EntityManager {

	// private PooledEngine engine;
	private World	world;
	private Entity	entity;

	public EntityManager(World world) {
		this.world = world;
	}

	public Entity makeHex( HexBase hex, int q, int r ) {
		entity = world.getHex(q, r);
		addCoordinates(q, r);

		addTexture(hex.getTex());

		float x = world.hexWidht * 0.75f * q;
		float y;
		if ( world.offsetPlace )
			y = ((q & 1) == 1 ? world.hexHeight * 0.5f : 0) - (world.hexHeight * r);
		else
			y = -(world.hexHeight * 0.5f * (r * 2 + 1 + q));

		addPosition(x, y);
		addCollision(x, y, world.hexWidht, world.hexHeight);

		if ( q == world.width / 2 && r == world.height / 2 )
			FireFactory.useElement(this);

		return entity;
	}

	public EntityManager addTimer( float time ) {
		CTimer tm = world.createComponent(CTimer.class);
		tm.time = time;
		entity.add(tm);
		return this;
	}

	public EntityManager addTelegraph( Telegraph teleg ) {
		CTelegraph tel = world.createComponent(CTelegraph.class);
		tel.tel = teleg;
		entity.add(tel);
		return this;
	}

	public EntityManager addHit( IHitable interact ) {
		CIHit inter = world.createComponent(CIHit.class);
		inter.interact = interact;
		entity.add(inter);
		return this;
	}

	public EntityManager addUpdate( IUpdatable upd ) {
		CIUpdate update = world.createComponent(CIUpdate.class);
		update.update = upd;
		entity.add(update);
		return this;
	}

	public EntityManager addActor( IActable actor ) {
		CIActor act = world.createComponent(CIActor.class);
		act.actor = actor;
		entity.add(act);
		return this;
	}

	public EntityManager addAnimation( Animation animation ) {
		CAnimation anim = world.createComponent(CAnimation.class);
		anim.anim = animation;
		entity.add(anim);
		return this;
	}

	public EntityManager addCollision( float x, float y, float width, float height ) {
		CCollision col = world.createComponent(CCollision.class);
		col.setColide(x, y, width, height);
		entity.add(col);
		return this;
	}

	public EntityManager addTexture( TextureRegion region ) {
		CTexture tex = entity.getComponent(CTexture.class);
		if ( tex == null )
			tex = world.createComponent(CTexture.class);
		tex.tex.add(region);
		entity.add(tex);
		return this;
	}

	public EntityManager addPosition( float x, float y ) {
		CPosition poz = world.createComponent(CPosition.class);
		poz.x = x;
		poz.y = y;
		entity.add(poz);
		return this;
	}

	public EntityManager addCoordinates( int q, int r ) {
		CCoordinate coo = world.createComponent(CCoordinate.class);
		coo.r = r;
		coo.q = q;
		entity.add(coo);
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
