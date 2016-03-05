package com.jtransc.media.limelibgdx.flash;

import com.badlogic.gdx.graphics.GL20;
import com.jtransc.media.limelibgdx.StateGL20;
import flash.display3D.Context3DClearMask;
import flash.display3D.Context3DTriangleFace;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class FlashGL20 {
	static public GL20 create() {
		return new StateGL20(new StateGL20.Impl() {
			@Override
			public void clear(StateGL20.State state, boolean color, boolean depth, boolean stencil) {
				//context.clear(
				//	state.clearRed,
				//	state.clearGreen,
				//	state.clearBlue,
				//	state.clearAlpha,
				//	state.clearDepth,
				//	state.clearStencil,
				//	color ? Stage3d
				//);
			}

			public void render(StateGL20.State state) {
				context.setCulling(
					state.cullFaceEnabled ? (
						state.cullFaceClockWise ? Context3DTriangleFace.FRONT : Context3DTriangleFace.BACK
					) : Context3DTriangleFace.FRONT_AND_BACK
				);
			}
		});
	}
}
