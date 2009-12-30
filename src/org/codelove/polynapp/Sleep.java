package org.codelove.polynapp;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Sleep extends Activity {

	private NoisePlayer noise = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sleep);

		final Button button = (Button) findViewById(R.id.sleep_awake_button);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				noise.stop();
				unsilencePhone();
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		Toast.makeText(this, "Generating noise...", Toast.LENGTH_SHORT).show();
		noise = new NoisePlayer();
		noise.start();
		silencePhone();
	}

	@Override
	public void onDestroy() {
		noise.stop();
		unsilencePhone();
		super.onDestroy();
	}
	
	private void silencePhone() {
		final AudioManager am = (AudioManager) getSystemService(Activity.AUDIO_SERVICE);
		am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		am.setRingerMode(AudioManager.VIBRATE_SETTING_OFF);
	}

	private void unsilencePhone() {
		final AudioManager am = (AudioManager) getSystemService(Activity.AUDIO_SERVICE);
		am.setRingerMode(AudioManager.VIBRATE_SETTING_ON);
		am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	}
}
