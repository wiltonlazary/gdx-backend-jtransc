import com.badlogic.cubocy.Cubocy;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.jtransc.media.limelibgdx.LimeApplication;

public class CubocJTransc {
	public static void main(String[] argv) {
		System.out.println("CubocJTransc.main()");
		new LimeApplication(new Cubocy(), "Cubocy", 480, 320);
		System.out.println("[2]");

		// After creating the Application instance we can set the log level to
		// show the output of calls to Gdx.app.debug
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		System.out.println("[3]");
	}
}
