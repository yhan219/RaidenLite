package project2;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;

public class Music {
	public static AudioClip backgroundMusic;
	public static AudioClip bossdieMusic;
	public static AudioClip bossdie2Music;
	public static AudioClip bossdie3Music;
	public static AudioClip skillMusic;
	public static AudioClip enemydieMusic;
	public static AudioClip getgiftMusic;
	public static AudioClip gethurtMusic;
	public static AudioClip herodeathMusic;
	public static AudioClip herobulletMusic;
	public static AudioClip successMusic;

	static {
		try {
			successMusic = Applet.newAudioClip(((new File(
					"src/music/success.wav")).toURI()).toURL());
			herobulletMusic = Applet.newAudioClip(((new File(
					"src/music/shot.wav")).toURI()).toURL());
			herodeathMusic = Applet.newAudioClip(((new File(
					"src/music/herodeath.wav")).toURI()).toURL());
			gethurtMusic = Applet.newAudioClip(((new File(
					"src/music/gethurt.wav")).toURI()).toURL());
			getgiftMusic = Applet.newAudioClip(((new File(
					"src/music/getgift.wav")).toURI()).toURL());
			enemydieMusic = Applet.newAudioClip(((new File(
					"src/music/enemydeath.wav")).toURI()).toURL());
			skillMusic = Applet.newAudioClip(((new File("src/music/skill.wav"))
					.toURI()).toURL());
			bossdie2Music = Applet
					.newAudioClip(((new File("src/music/die.wav")).toURI())
							.toURL());
			bossdie2Music = Applet.newAudioClip(((new File(
					"src/music/death.wav")).toURI()).toURL());
			bossdieMusic = Applet.newAudioClip(((new File(
					"src/music/boss_death.wav")).toURI()).toURL());
			backgroundMusic = Applet.newAudioClip(((new File(
					"src/music/background.wav")).toURI()).toURL());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
