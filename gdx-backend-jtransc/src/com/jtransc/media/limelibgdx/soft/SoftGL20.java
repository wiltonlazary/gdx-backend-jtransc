package com.jtransc.media.limelibgdx.soft;

import com.jtransc.media.limelibgdx.StateGL20;

public class SoftGL20 {
	static public StateGL20<SoftGL20.Impl> create(BitmapData32 backbuffer) {
		return new StateGL20<>(new Impl(backbuffer));
	}

	static public class Impl extends StateGL20.Impl {
		BitmapData32 backbuffer;

		public Impl(BitmapData32 backbuffer) {
			this.backbuffer = backbuffer;
		}

		@Override
		public void clear(StateGL20.State state, boolean color, boolean depth, boolean stencil) {
			int color32 = state.clearColor.toColor32();

			backbuffer.fill(color32);
		}
	}
}
