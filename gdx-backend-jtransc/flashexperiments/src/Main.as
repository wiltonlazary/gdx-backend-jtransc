package {
import com.adobe.utils.AGALMiniAssembler;

import flash.display.Sprite;
import flash.events.KeyboardEvent;
import flash.events.TextEvent;
import flash.text.TextField;
import flash.text.TextFieldType;
import flash.utils.ByteArray;
import flash.utils.setTimeout;

public class Main extends Sprite {
    function createTf():TextField {
        var tf:TextField = new TextField();
        tf.type = TextFieldType.INPUT;
        tf.multiline = true;
        tf.width = 200;
        tf.height = 200;
        tf.border = true;
        tf.wordWrap = true;
        tf.borderColor = 0xFF333333;
        tf.backgroundColor = 0xFFFF00;
        return tf;
    }

    static private function byteToHex(b:uint):String {
        return ('00' + b.toString(16)).substr(-2);
    }

    private function baToString(ba:ByteArray):String {
        var out:String = "";
        for (var n:int = 0; n < ba.length; n++) out += byteToHex(ba[n]);
        return out;
    }

    public function Main() {
        setTimeout(function () {
            var tin = createTf();
            var tout = createTf();
            tout.x = 200;

            function assemble():void {
                var asm:AGALMiniAssembler = new AGALMiniAssembler();
                //trace(tin.text);
                tout.text = agalTokenize(baToString(asm.assemble("vertex", tin.text)));
                //trace('event:' + e);
            }

            tin.addEventListener(TextEvent.TEXT_INPUT, function (e:TextEvent):void {
                assemble();
            });

            tin.addEventListener(KeyboardEvent.KEY_UP, function (e:KeyboardEvent):void {
                assemble();
            });
            tin.addEventListener(KeyboardEvent.KEY_DOWN, function (e:KeyboardEvent):void {
                assemble();
            });
            addChild(tin);
            addChild(tout);
        }, 0);
    }

    static private function agalTokenize(str:String):String {
        var out:Array = [];
        var offset:int = 0;
        while (offset < str.length) {
            var size:int = (offset == 0) ? 14 : 48;
            out.push(str.substring(offset, offset + size));
            offset += size;
        }
        return out.join(":");
    }
}
}
