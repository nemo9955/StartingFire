package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.ComponentType;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.nemo9955.starting_fire.game.ashley.components.CAnimation;
import com.nemo9955.starting_fire.game.ashley.components.CCollision;
import com.nemo9955.starting_fire.game.ashley.components.CPosition;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.storage.CM;
import com.nemo9955.starting_fire.storage.SF;

public class RenderSystem extends IteratingSystem {

	public boolean	render	= true;

	@SuppressWarnings("unchecked")
	public RenderSystem() {
		super(Family.getFor(ComponentType.getBitsFor(CPosition.class, CWorld.class),
					ComponentType.getBitsFor(CTexture.class, CAnimation.class, CCollision.class),
					ComponentType.getBitsFor()));
	}

	@Override
	public void processEntity( Entity entity, float deltaTime ) {
		CPosition po = CM.Pos.get(entity);
		CWorld wo = CM.Wor.get(entity);
		CTexture tex = CM.Tex.get(entity);
		CAnimation anim = CM.Anim.get(entity);
		// CCollision col = CM.Col.get(entity);

		if ( tex != null )
			for (TextureRegion texture : tex.tex)
				SF.spritesBatch.draw(texture, po.x, po.y, texture.getRegionWidth(), texture.getRegionHeight());

		if ( anim != null )
			SF.spritesBatch.draw(anim.anim.getKeyFrame(wo.world.stateTime, true), po.x, po.y);

		// if ( col != null ) {
		// SF.spritesBatch.draw(SF.atlas.findRegion("tile_highlight"), col.bound.x, col.bound.y);
		// SF.shapeRend.setColor(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
		// SF.shapeRend.ellipse(col.colide.x - col.colide.width / 2, col.colide.y - col.colide.height / 2,
		// col.colide.width, col.colide.height, 6);
		// SF.shapeRend.rect(col.colide.x, col.colide.y, col.colide.width, col.colide.height);

	}

	@Override
	public boolean checkProcessing() {
		return render;
	}
}
