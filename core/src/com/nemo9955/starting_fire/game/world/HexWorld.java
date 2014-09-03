package com.nemo9955.starting_fire.game.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.nemo9955.starting_fire.game.Hex;
import com.nemo9955.starting_fire.game.ashley.CAlignment;
import com.nemo9955.starting_fire.game.ashley.CPosition;
import com.nemo9955.starting_fire.game.ashley.CTexture;
import com.nemo9955.starting_fire.game.ashley.CWorld;
import com.nemo9955.starting_fire.game.ashley.RenderSystem;
import com.nemo9955.starting_fire.storage.Assets;

public class HexWorld implements Disposable {

	private PooledEngine	eng	= new PooledEngine();
	public int				width, height, hexWidht, hexHeight;

	public HexWorld(int width, int height, int hexWidht, int hexHeight) {
		super();
		this.width = width;
		this.height = height;
		this.hexWidht = hexWidht;
		this.hexHeight = hexHeight;

		eng.addSystem(new RenderSystem());
		generateNewWorld();

	}

	private void generateNewWorld() {
		TextureAtlas ta = Assets.HEXAS.asset(TextureAtlas.class);

		for (int row = -width; row <= width; row++)
			for (int col = -height; col <= height; col++) {
				Entity ent = eng.createEntity();

				CAlignment al = eng.createComponent(CAlignment.class);
				al.col = col;
				al.row = row;
				ent.add(al);

				CPosition poz = eng.createComponent(CPosition.class);
				poz.x = (int) (hexWidht * 0.75f * col);
				poz.y = (col % 2 == 1 ? 0 : hexHeight) + (hexHeight * row);
				ent.add(poz);

				CTexture tex = eng.createComponent(CTexture.class);
				tex.tex = new TextureRegion(ta.findRegion(Hex.getRandomValue().getName()));
				ent.add(tex);

				CWorld cw = eng.createComponent(CWorld.class);
				cw.world = this;
				ent.add(cw);
			}
	}

	/**
	 * updates but also renders the world .
	 * 
	 * use setRender(boolean render) to enable/disable rendering
	 * 
	 * @param delta
	 */
	public void update( float delta ) {
		eng.update(delta);
	}

	public void setRender( boolean render ) {
		eng.getSystem(RenderSystem.class).render = render;
	}

	@Override
	public void dispose() {
		eng.clearPools();
	}
}
