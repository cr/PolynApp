package org.codelove.polynapp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Environment;
import android.util.Log;

public class NoisePlayer {

	private int samplingFrequency = 22050;
	private int bufferSeconds = 5;
	private int bufferFrames = (int) samplingFrequency * bufferSeconds;
	private int channels = 2;
	private int bufferSize = bufferFrames * channels;
	private short[] audioBuffer = new short[bufferSize];
	private double[] randomBuffer = new double[bufferFrames];
	private double noise_color = 0;
	private int filter_poles = 3;
	private AudioTrack audioTrack = null;

	public NoisePlayer() {
			/* fill the noise buffer */
			fillBuffer();
	}

	public void start() {
		try {
			audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 22050,
					AudioFormat.CHANNEL_CONFIGURATION_STEREO,
					AudioFormat.ENCODING_PCM_16BIT, bufferSize * 2, // in bytes
					AudioTrack.MODE_STATIC);
			audioTrack.flush();
			// Write the music buffer to the AudioTrack object
			audioTrack.write(audioBuffer, 0, bufferSize); // size in shorts
			// Start playback
			audioTrack.setLoopPoints(0, bufferFrames, 50);
			audioTrack.play();
		} catch (Throwable t) {
			Log.e("AudioTrack", "Playback failed");
		}
	}

	public void stop() {
		try {
			audioTrack.stop();
			audioTrack.flush();
		} catch (Throwable t) {
			Log.e("AudioTrack", "flushing failed");
		}
	}

	protected void fillBuffer() {
		short frame;
		PinkNoise source = new PinkNoise(noise_color, filter_poles);
		for (int i = 0; i < bufferFrames; i++) {
			frame = (short) (source.nextValue() * 32767.0);
			audioBuffer[i * 2] = frame;
			audioBuffer[(bufferFrames - 1 - i) * 2 + 1] = frame;
		}
	}

}
