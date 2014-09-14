package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.ComponentMapper;

public class CM {

	public static ComponentMapper<CPosition>	Pos		= ComponentMapper.getFor(CPosition.class);
	public static ComponentMapper<CTexture>		Tex		= ComponentMapper.getFor(CTexture.class);
	public static ComponentMapper<CAnimation>	Anim	= ComponentMapper.getFor(CAnimation.class);
	public static ComponentMapper<CWorld>		Wor		= ComponentMapper.getFor(CWorld.class);
	public static ComponentMapper<CCollision>	Col		= ComponentMapper.getFor(CCollision.class);
	public static ComponentMapper<CCoordinate>	Coor	= ComponentMapper.getFor(CCoordinate.class);
	public static ComponentMapper<CIHit>		Inter	= ComponentMapper.getFor(CIHit.class);
	public static ComponentMapper<CIUpdate>		Upd		= ComponentMapper.getFor(CIUpdate.class);
	public static ComponentMapper<CIActor>		Act		= ComponentMapper.getFor(CIActor.class);
	public static ComponentMapper<CTimer>		Time	= ComponentMapper.getFor(CTimer.class);
	public static ComponentMapper<CTelegraph>	Tel		= ComponentMapper.getFor(CTelegraph.class);
}
