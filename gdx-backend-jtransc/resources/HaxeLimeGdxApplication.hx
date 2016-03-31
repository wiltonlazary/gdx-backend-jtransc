class HaxeLimeGdxApplication extends lime.app.Application {
    static public var instance:HaxeLimeGdxApplication;
    static public var listener:com.badlogic.gdx.ApplicationListener_;
    static public var initializedListener:Bool = false;

    static public function convertFloatBuffer(buf, size = -1) {
        var len = buf.limit__I();
        var out = new lime.utils.Float32Array(len);
        for (n in 0 ... len) out[n] = buf.get_I_F(n);
        return out;
    }

    static public function convertShortBuffer(buf, size = -1) {
        var len = buf.limit__I();
        var out = new lime.utils.Int16Array(len);
        for (n in 0 ... len) out[n] = buf.get_I_S(n);
        return out;
    }

    static public function convertIntBuffer(buf, size) {
        var len = buf.limit__I();
        var out = new lime.utils.Int32Array(len);
        for (n in 0 ... len) out[n] = buf.get_I_I(n);
        return out;
    }

    static public function convertBuffer(buf:java_.nio.Buffer_, size:Int = -1):lime.utils.ArrayBufferView {
        if (Std.is(buf, java_.nio.FloatBuffer_)) return convertFloatBuffer(cast(buf, java_.nio.FloatBuffer_));
        if (Std.is(buf, java_.nio.ShortBuffer_)) return convertShortBuffer(cast(buf, java_.nio.ShortBuffer_));
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
        if (listener != null) {
            if (!initializedListener) {
                initializedListener = true;
                listener.create__V();
            }
            listener.render__V();
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
    override public function onMouseUp (window:lime.ui.Window, x:Float, y:Float, button:Int):Void {
    }
    override public function onMouseDown (window:lime.ui.Window, x:Float, y:Float, button:Int):Void {
    }
    override public function onMouseMove (window:lime.ui.Window, x:Float, y:Float):Void {
    }
}