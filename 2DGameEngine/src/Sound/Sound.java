package Sound;
import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	
	public static final AudioClip level = Applet.newAudioClip(Sound.class.getResource("gameloop.wav"));
	public static final AudioClip gun = Applet.newAudioClip(Sound.class.getResource("Gun.wav"));
	public static final AudioClip death = Applet.newAudioClip(Sound.class.getResource("Death.wav"));

}