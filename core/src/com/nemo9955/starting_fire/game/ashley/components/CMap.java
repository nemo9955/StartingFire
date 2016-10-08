package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.ComponentMapper;

public class CMap {

	public static ComponentMapper<CPosition> position = ComponentMapper.getFor(CPosition.class);
	public static ComponentMapper<CTexture> texture = ComponentMapper.getFor(CTexture.class);
	public static ComponentMapper<CAnimation> animation = ComponentMapper.getFor(CAnimation.class);
	public static ComponentMapper<CWorld> world = ComponentMapper.getFor(CWorld.class);
	public static ComponentMapper<CCollision> collision = ComponentMapper.getFor(CCollision.class);
	public static ComponentMapper<CCoordinate> coordinate = ComponentMapper.getFor(CCoordinate.class);
	public static ComponentMapper<CHit> hit = ComponentMapper.getFor(CHit.class);
	public static ComponentMapper<CActor> actor = ComponentMapper.getFor(CActor.class);
	public static ComponentMapper<CTimer> timer = ComponentMapper.getFor(CTimer.class);
	public static ComponentMapper<CTelegraph> telegraph = ComponentMapper.getFor(CTelegraph.class);
	public static ComponentMapper<CInputListener> input = ComponentMapper.getFor(CInputListener.class);
}
