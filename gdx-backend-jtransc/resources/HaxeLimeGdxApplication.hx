import lime.graphics.opengl.GL;
import lime.ui.Window;
import lime.ui.KeyCode;
import lime.graphics.Renderer;
import com.jtransc.media.limelibgdx.LimeInput_;

class HaxeLimeGdxApplication extends lime.app.Application {
    static public var instance:HaxeLimeGdxApplication;
    static public var app:com.jtransc.media.limelibgdx.LimeApplication_;
    static public var initializedListener:Bool = false;

    static public function convertByteBuffer(buf, size = -1) {
        var len = buf.limit__I();
        var out = new lime.utils.UInt8Array(len);
        for (n in 0 ... len) out[n] = buf.get_I_B(n);
        return out;
    }

    static public function convertShortBuffer(buf, size = -1) {
        var len = buf.limit__I();
        var out = new lime.utils.Int16Array(len);
        for (n in 0 ... len) out[n] = buf.get_I_S(n);
        return out;
    }

    static public function convertIntBuffer(buf, size = -1) {
        var len = buf.limit__I();
        var out = new lime.utils.Int32Array(len);
        for (n in 0 ... len) out[n] = buf.get_I_I(n);
        return out;
    }

    static public function convertFloatBuffer(buf, size = -1) {
        var len = buf.limit__I();
        var out = new lime.utils.Float32Array(len);
        for (n in 0 ... len) out[n] = buf.get_I_F(n);
        return out;
    }

    static public function convertBuffer(buf:java_.nio.Buffer_, size:Int = -1):lime.utils.ArrayBufferView {
        if (Std.is(buf, java_.nio.ByteBuffer_)) return convertByteBuffer(cast(buf, java_.nio.ByteBuffer_));
        if (Std.is(buf, java_.nio.ShortBuffer_)) return convertShortBuffer(cast(buf, java_.nio.ShortBuffer_));
        if (Std.is(buf, java_.nio.IntBuffer_)) return convertIntBuffer(cast(buf, java_.nio.IntBuffer_));
        if (Std.is(buf, java_.nio.FloatBuffer_)) return convertFloatBuffer(cast(buf, java_.nio.FloatBuffer_));
		throw 'Not implemented convertBuffer!';
    }

    static public function convertIntArray(buf:HaxeIntArray, offset:Int, size:Int):lime.utils.Int32Array {
        var len = buf.length;
        var out = new lime.utils.Int32Array(len);
        for (n in 0 ... len) out[n] = buf.get(n);
        return out;
    }

    static public function convertFloatArray(buf:HaxeFloatArray, offset:Int, size:Int):lime.utils.Float32Array {
        var len = buf.length;
        var out = new lime.utils.Float32Array(len);
        for (n in 0 ... len) out[n] = buf.get(n);
        return out;
    }

    static public function loopInit(init: Void -> Void) {
    }

    static public function loopLoop(update: Void -> Void, render: Void -> Void) {
    }

    public override function onPreloadComplete():Void {
        //switch (renderer.context) {
        //	case FLASH(sprite): #if flash initializeFlash(sprite); #end
        //	case OPENGL (gl):
        //	default:
        //	throw "Unsupported render context";
        //}
    }

    public override function render(renderer:lime.graphics.Renderer) {
        super.render(renderer);
        if (app != null) {
            if (!initializedListener) {
                initializedListener = true;
                app.create__V();
            }
            app.render__V();
        }
    }

    public override function update(deltaTime:Int) {
        super.update(deltaTime);
    }

    public function new() {
        super();
        HaxeLimeGdxApplication.instance = this;
        addModule(new JTranscModule());
    }
}

class JTranscModule extends lime.app.Module {
    override public function onMouseUp (window:Window, x:Float, y:Float, button:Int):Void {
    	LimeInput_.lime_onMouseUp_DDI_V(x, y, button);
    }
    override public function onMouseDown (window:Window, x:Float, y:Float, button:Int):Void {
    	LimeInput_.lime_onMouseDown_DDI_V(x, y, button);
    }
    override public function onMouseMove (window:Window, x:Float, y:Float):Void {
    	LimeInput_.lime_onMouseMove_DD_V(x, y);
    }
	override public function onKeyDown(window:Window, keyCode:KeyCode, modifier:lime.ui.KeyModifier):Void {
		LimeInput_.lime_onKeyDown_II_V(keyCode, modifier);
	}
	override public function onKeyUp(window:Window, keyCode:KeyCode, modifier:lime.ui.KeyModifier):Void {
		LimeInput_.lime_onKeyUp_II_V(keyCode, modifier);
	}
}