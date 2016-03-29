class HaxeLimeGdxApplication extends lime.app.Application {
    static public var instance:HaxeLimeGdxApplication;
    static public var listener:com.badlogic.gdx.ApplicationListener_;


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