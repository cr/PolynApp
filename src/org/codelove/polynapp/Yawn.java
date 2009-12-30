package org.codelove.polynapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;

public class Yawn extends Activity {

	private Bundle bundle = null;
	private static final String UTMIN = "yawnTimerMinutes";
	private static final String UTHRS = "yawnTimerHours";
	private static final String UTNOI = "yawnToggleNoise";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yawn);

		/* Bundle take saved state info */
		bundle = new Bundle();

		final TimePicker picker = (TimePicker) findViewById(R.id.yawn_timer);
		picker.setIs24HourView(true);

		picker.setCurrentHour(bundle.getInt(UTHRS, 0));
		picker.setCurrentMinute(bundle.getInt(UTMIN, 26));

		final Button button = (Button) findViewById(R.id.yawn_start_button);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/* Save the timer state */
				bundle.putInt(UTHRS, picker.getCurrentHour());
				bundle.putInt(UTMIN, picker.getCurrentMinute());
				/* Switch over to sleep activity */
				Intent i = new Intent(v.getContext(), Sleep.class);
				startActivityForResult(i, 0);
			}

		});
	}

	/*
	 * public void onClick(View v) { switch (v.getId()) { case
	 * R.id.undress_start_button: Intent i = new Intent(this, Yawn.class);
	 * startActivity(i); break; // More buttons go here (if any) ... } } }
	 */

}