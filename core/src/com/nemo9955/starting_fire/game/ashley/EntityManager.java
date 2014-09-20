package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nemo9955.starting_fire.game.ashley.components.CActor;
import com.nemo9955.starting_fire.game.ashley.components.CActor.IActable;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CCollision;
import com.nemo9955.starting_fire.game.ashley.components.CCoordinate;
import com.nemo9955.starting_fire.game.ashley.components.CHit;
import com.nemo9955.starting_fire.game.ashley.components.CHit.IHitable;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CTelegraph;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CTimer;
import com.nemo9955.starting_fire.game.ashley.components.CUpdate;
import com.nemo9955.starting_fire.game.ashley.components.CUpdate.IUpdatable;
import com.nemo9955.starting_fire.game.world.World;

public class EntityManager {

	private World	world;

	public EntityManager(World world) {
		this.world = world;
	}

	public EntityManager addTimer( Entity entity, float time ) {
		CTimer tm = world.createComponent(CTimer.class);
		tm.time = time;
		entity.add(tm);
		return this;
	}

	public EntityManager addTelegraph( Entity entity, Telegraph teleg ) {
		CTelegraph tel = world.createComponent(CTelegraph.class);
		tel.tel = teleg;
		entity.add(tel);
		return this;
	}

	public EntityManager addHit( Entity entity, IHitable interact ) {
		CHit inter = world.createComponent(CHit.class);
		inter.hitter = interact;
		entity.add(inter);
		return this;
	}

	public EntityManager addUpdate( Entity entity, IUpdatable upd ) {
		CUpdate update = world.createComponent(CUpdate.class);
		update.update = upd;
		entity.add(update);
		return this;
	}

	public EntityManager addActor( Entity entity, IActable actor ) {
		CActor act = world.createComponent(CActor.class);
		act.actor = actor;
		entity.add(act);
		return this;
	}

	public EntityManager addAnimation( Entity entity, Animation animation ) {
		CAnimation anim = world.createComponent(CAnimation.class);
		anim.anim = animation;
		entity.add(anim);
		return this;
	}

	public EntityManager addCollision( Entity entity, float x, float y, float width, float height ) {
		CCollision col = world.createComponent(CCollision.class);
		col.setColide(x, y, width, height);
		entity.add(col);
		return this;
	}

	public EntityManager addTexture( Entity entity, TextureRegion region ) {
		CTexture tex = entity.getComponent(CTexture.class);
		if ( tex == null )
			tex = world.createComponent(CTexture.class);
		tex.tex.add(region);
		entity.add(tex);
		return this;
	}

	public EntityManager addPosition( Entity entity, float x, float y ) {
		CPosition poz = world.createComponent(CPosition.class);
		poz.x = x;
		poz.y = y;
		entity.add(poz);
		return this;
	}

	public EntityManager addCoordinates( Entity entity, int q, int r ) {
		CCoordinate coo = world.createComponent(CCoordinate.class);
		coo.r = r;
		coo.q = q;
		entity.add(coo);
		return this;
	}

}
