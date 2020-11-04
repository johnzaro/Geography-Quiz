package com.johnzaro.geographyquiz.core;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioStuff
{
	private DoubleProperty masterSliderVolume;
	private DoubleProperty musicSliderVolume;
	private DoubleProperty soundEffectsSliderVolume;
	
	private DoubleBinding musicCalculatedVolume;
	private DoubleBinding soundEffectsCalculatedVolume;
	
	private AudioClip HOVER_SOUND, BUTTON_CLICK_SOUND, SWITCH_BUTTON_ON_SOUND, SWITCH_BUTTON_OFF_SOUND, CHECKBOX_SELECTED_SOUND, 
			CHECKBOX_DESELECTED_SOUND, RADIOBUTTON_SELECTED_SOUND, POPUP_SOUND, SLIDE_SOUND, MINIMIZE_SOUND, MAXIMIZE_SOUND,
			REWIND_SOUND, CORRECT_ANSWER_SIMPLE_SOUND, WRONG_ANSWER_SIMPLE_SOUND, GAME_WON_SOUND, GAME_LOST_SOUND, HEART_BREAKING_SOUND, TIME_OVER_SOUND;
	
	private Media CLOCK_TICKING_30S_SOUND, introductionSound, welcomeLoopSound;
	private MediaPlayer CLOCK_TICKING_30S_PLAYER, introductionMediaPlayer, welcomeLoopMediaPlayer;
	
	public AudioStuff()
	{
		masterSliderVolume = new SimpleDoubleProperty();
		musicSliderVolume = new SimpleDoubleProperty();
		soundEffectsSliderVolume = new SimpleDoubleProperty();
		
		musicCalculatedVolume = (masterSliderVolume.divide(100.0)).multiply(musicSliderVolume.divide(100.0));
		soundEffectsCalculatedVolume = (masterSliderVolume.divide(100.0)).multiply(soundEffectsSliderVolume.divide(100.0));
	}
	
	void loadAudio()
	{
		HOVER_SOUND = new AudioClip(PowerOn.class.getResource("/audio/hover.wav").toExternalForm());
		BUTTON_CLICK_SOUND = new AudioClip(PowerOn.class.getResource("/audio/buttonClick.wav").toExternalForm());
		SWITCH_BUTTON_ON_SOUND = new AudioClip(PowerOn.class.getResource("/audio/switchButtonOn.wav").toExternalForm());
		SWITCH_BUTTON_OFF_SOUND = new AudioClip(PowerOn.class.getResource("/audio/switchButtonOff.wav").toExternalForm());
		CHECKBOX_SELECTED_SOUND = new AudioClip(PowerOn.class.getResource("/audio/checkboxSelected.wav").toExternalForm());
		CHECKBOX_DESELECTED_SOUND = new AudioClip(PowerOn.class.getResource("/audio/checkboxDeselected.wav").toExternalForm());
		RADIOBUTTON_SELECTED_SOUND = new AudioClip(PowerOn.class.getResource("/audio/radioButtonSelected.wav").toExternalForm());
		
		POPUP_SOUND = new AudioClip(PowerOn.class.getResource("/audio/popUp.wav").toExternalForm());
		SLIDE_SOUND = new AudioClip(PowerOn.class.getResource("/audio/slide.wav").toExternalForm());
		
		MINIMIZE_SOUND = new AudioClip(PowerOn.class.getResource("/audio/minimize.wav").toExternalForm());
		MAXIMIZE_SOUND = new AudioClip(PowerOn.class.getResource("/audio/maximize.wav").toExternalForm());
		
		REWIND_SOUND = new AudioClip(PowerOn.class.getResource("/audio/rewind.wav").toExternalForm());
		CORRECT_ANSWER_SIMPLE_SOUND = new AudioClip(PowerOn.class.getResource("/audio/correctAnswerSimple.wav").toExternalForm());
		WRONG_ANSWER_SIMPLE_SOUND = new AudioClip(PowerOn.class.getResource("/audio/wrongAnswerSimple.wav").toExternalForm());
		GAME_WON_SOUND = new AudioClip(PowerOn.class.getResource("/audio/gameWon.wav").toExternalForm());
		GAME_LOST_SOUND = new AudioClip(PowerOn.class.getResource("/audio/gameLost.wav").toExternalForm());
		HEART_BREAKING_SOUND = new AudioClip(PowerOn.class.getResource("/audio/heartBreaking.wav").toExternalForm());
		TIME_OVER_SOUND = new AudioClip(PowerOn.class.getResource("/audio/timeOver.wav").toExternalForm());
		
		CLOCK_TICKING_30S_SOUND = new Media(PowerOn.class.getResource("/audio/clockTicking30S.mp3").toExternalForm());
		CLOCK_TICKING_30S_PLAYER = new MediaPlayer(CLOCK_TICKING_30S_SOUND);
		CLOCK_TICKING_30S_PLAYER.setCycleCount(MediaPlayer.INDEFINITE);
		
		introductionSound = new Media(PowerOn.class.getResource("/audio/introductionSound.mp3").toExternalForm());
		welcomeLoopSound = new Media(PowerOn.class.getResource("/audio/welcomeLoopSound.mp3").toExternalForm());
		introductionMediaPlayer = new MediaPlayer(introductionSound);
		welcomeLoopMediaPlayer = new MediaPlayer(welcomeLoopSound);
		
		welcomeLoopMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		
		introductionMediaPlayer.setOnEndOfMedia(() ->
		{
			playWelcomeLoopSoundSound();
			introductionMediaPlayer.dispose();
		});
		
		HOVER_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		BUTTON_CLICK_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		SWITCH_BUTTON_ON_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		SWITCH_BUTTON_OFF_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		CHECKBOX_SELECTED_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		CHECKBOX_DESELECTED_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		RADIOBUTTON_SELECTED_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		POPUP_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		SLIDE_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		MINIMIZE_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		MAXIMIZE_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		REWIND_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		CORRECT_ANSWER_SIMPLE_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		WRONG_ANSWER_SIMPLE_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		GAME_WON_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		GAME_LOST_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		HEART_BREAKING_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		TIME_OVER_SOUND.volumeProperty().bind(soundEffectsCalculatedVolume);
		CLOCK_TICKING_30S_PLAYER.volumeProperty().bind(soundEffectsCalculatedVolume);
		
		introductionMediaPlayer.volumeProperty().bind(musicCalculatedVolume);
		welcomeLoopMediaPlayer.volumeProperty().bind(musicCalculatedVolume);
	}
	
	public void setMasterSliderVolume(double masterSliderVolume)
	{
		this.masterSliderVolume.set(masterSliderVolume);
	}
	
	public void setMusicSliderVolume(double musicSliderVolume)
	{
		this.musicSliderVolume.set(musicSliderVolume);
	}
	
	public void setSoundEffectsSliderVolume(double soundEffectsSliderVolume)
	{
		this.soundEffectsSliderVolume.set(soundEffectsSliderVolume);
	}
	
	public double getMasterSliderVolume()
	{
		return masterSliderVolume.get();
	}
	
	public DoubleProperty masterSliderVolumeProperty()
	{
		return masterSliderVolume;
	}
	
	public double getMusicSliderVolume()
	{
		return musicSliderVolume.get();
	}
	
	public DoubleProperty musicSliderVolumeProperty()
	{
		return musicSliderVolume;
	}
	
	public double getSoundEffectsSliderVolume()
	{
		return soundEffectsSliderVolume.get();
	}
	
	public DoubleProperty soundEffectsSliderVolumeProperty()
	{
		return soundEffectsSliderVolume;
	}
	
	public void playIntroductionSound()
	{
		introductionMediaPlayer.play();
	}
	
	public void pauseIntroductionSound()
	{
		introductionMediaPlayer.pause();
	}
	
	public boolean isIntroductionSoundPlaying()
	{
		return introductionMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
	}
	
	public boolean isIntroductionSoundPaused()
	{
		return introductionMediaPlayer.getStatus() == MediaPlayer.Status.PAUSED;
	}
	
	public boolean isIntroductionSoundReady()
	{
		return introductionMediaPlayer.getStatus() == MediaPlayer.Status.READY;
	}
	
	public void playWelcomeLoopSoundSound()
	{
		welcomeLoopMediaPlayer.play();
	}
	
	public void pauseWelcomeLoopSound()
	{
		welcomeLoopMediaPlayer.pause();
	}
	
	public boolean isWelcomeLoopSoundPlaying()
	{
		return welcomeLoopMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
	}
	
	public boolean isWelcomeLoopSoundPaused()
	{
		return welcomeLoopMediaPlayer.getStatus() == MediaPlayer.Status.PAUSED;
	}
	
	public void playHoverSound()
	{
//		HOVER_SOUND.play();
	}
	
	public void playButtonClickSound()
	{
		BUTTON_CLICK_SOUND.play();
	}
	
	public void playSwitchButtonOnSound()
	{
		SWITCH_BUTTON_ON_SOUND.play();
	}
	
	public void playSwitchButtonOffSound()
	{
		SWITCH_BUTTON_OFF_SOUND.play();
	}
	
	public void playCheckBoxSelectedSound()
	{
		CHECKBOX_SELECTED_SOUND.play();
	}
	
	public void playCheckBoxDeselectedSound()
	{
		CHECKBOX_DESELECTED_SOUND.play();
	}
	
	public void playRadioButtonSelectedSound()
	{
		RADIOBUTTON_SELECTED_SOUND.play();
	}
	
	public void playPopUpSound()
	{
		POPUP_SOUND.play();
	}
	
	public void playSlideSound()
	{
		SLIDE_SOUND.play();
	}
	
	public void playMinimizeSound()
	{
		MINIMIZE_SOUND.play();
	}
	
	public void playMaximizeSound()
	{
		MAXIMIZE_SOUND.play();
	}
	
	public void playRewindSound()
	{
		REWIND_SOUND.play();
	}
	
	public void playCorrectAnswerSimpleSound()
	{
		CORRECT_ANSWER_SIMPLE_SOUND.play();
	}
	
	public void playWrongAnswerSimpleSound()
	{
		WRONG_ANSWER_SIMPLE_SOUND.play();
	}
	
	public void playGameWonSound()
	{
		GAME_WON_SOUND.play();
	}
	
	public void playGameLostSound()
	{
		GAME_LOST_SOUND.play();
	}
	
	public void playHeartBreakingSound()
	{
		HEART_BREAKING_SOUND.play();
	}
	
	public void playTimeOverSound()
	{
		TIME_OVER_SOUND.play();
	}
	
	public void playClockTickingSound()
	{
		CLOCK_TICKING_30S_PLAYER.play();
	}
	
	public void stopClockTickingSound()
	{
		CLOCK_TICKING_30S_PLAYER.stop();
	}
}
