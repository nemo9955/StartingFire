package com.nemo9955.starting_fire.storage;

import com.badlogic.ashley.core.ComponentMapper;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CCollision;
import com.nemo9955.starting_fire.game.ashley.components.CCoordinate;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;

public class CM {

	public static ComponentMapper<CPosition>	Pos		= ComponentMapper.getFor(CPosition.class);
	public static ComponentMapper<CTexture>		Tex		= ComponentMapper.getFor(CTexture.class);
	public static ComponentMapper<CAnimation>	Anim	= ComponentMapper.getFor(CAnimation.class);
	public static ComponentMapper<CWorld>		Wor		= ComponentMapper.getFor(CWorld.class);
	public static ComponentMapper<CCollision>	Col		= ComponentMapper.getFor(CCollision.class);
	public static ComponentMapper<CCoordinate>	Coor	= ComponentMapper.getFor(CCoordinate.class);
}
