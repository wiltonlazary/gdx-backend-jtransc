class HaxeLimeGdxApplication extends lime.app.Application {
    static public var instance:HaxeLimeGdxApplication;
    static public var listener:com.badlogic.gdx.ApplicationListener_;
    static public var initializedListener:Boolean = false;

    static public function convertFloatBuffer(buf:java_.nio.FloatBuffer_, size:Int = -1):lime.utils.Float32Array {
        var len:Int = buf.limit__I();
        var out:lime.utils.Float32Array = new lime.utils.Float32Array(len);
        for (n in 0 ... len) out.set(n, buf.get_I_F(n));
        return out;
    }


    static public function convertIntBuffer(buf:java_.nio.IntBuffer_, size:Int = -1):lime.utils.Int32Array {
        var len = buf.limit__I();
        var out = new lime.utils.Int32Array(len);
        for (n in 0 ... len) out.set(n, buf.get_I_I(n));
        return out;
    }

    static public function convertBuffer(buf:java_.nio.Buffer_, size:Int = -1):lime.utils.Int8Array {
        var len:Int = buf.limit__I();
        var out:lime.utils.Int8Array = new lime.utils.Int8Array(len);
        //for (n in 0 ... len) out.set(n, buf.get_I_F(n));
        throw 'Not implemented convertBuffer!';
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