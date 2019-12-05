package umd.cmsc434.smashyourphone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.Random;

public class PlayActivity extends AppCompatActivity {
	private VideoView vid;
	private VidCharPlayer vidCharPlayer = new VidCharPlayer();
	
	private int score = 0;
	private Random rand = new Random();
	private TextView txtScoreVal;
	private View div3;
	private View div4;
	
	private SensorManager sensorManager;
	private Sensor sensor;
	private SigMovListener sigMovListener;
	
	private boolean paused = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		vid = findViewById(R.id.vid_char);
		String vidPath = "android.resource://" + getPackageName() + "/" + R.raw.play_marmot_scream;
		vid.setVideoURI(Uri.parse(vidPath));
		vid.setZOrderOnTop(true);
		vid.requestFocus();
		vid.seekTo(1);
		
		txtScoreVal = findViewById(R.id.txt_score_val);
		div3 = findViewById(R.id.vw_divider_3);
		div4 = findViewById(R.id.vw_divider_4);
		
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		sigMovListener = new SigMovListener();
		
		sensorManager.registerListener(sigMovListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void vidCharClicked(View v) {
		if (!paused) {
			scoreUp(100, 50);
			vidCharPlayer.play(225, 325);
		}
	}
	
	public void icHomeClicked(View V) {
		backToHomeActivity();
	}
	
	private void backToHomeActivity() {
		Intent intent = new Intent(this, HomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		setCustTransAnim();
	}
	
	public void icSettingsClicked(View v) {
		openSettingsActivity();
	}
	
	private void openSettingsActivity() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
		setCustTransAnim();
	}
	
	private void setCustTransAnim() {
		overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
	}
	
	public void bnRestartClicked(View v) {
		scoreReset();
		vid.pause();
		vid.seekTo(1);
		if (paused)
			bnPauseClicked(v);
	}
	
	private void scoreUp(int incrVal, int maxBonus) {
		score += incrVal + rand.nextInt(maxBonus + 1);
		txtScoreVal.setText(String.valueOf(score));
		
		int color = getResources().getColor(R.color.colorPlayScore1);
		if (score >= 1024 + rand.nextInt(maxBonus + 1))
			color = getResources().getColor(R.color.colorPlayScore2);
		if (score >= 2048 + rand.nextInt(maxBonus + 1))
			color = getResources().getColor(R.color.colorPlayScore3);
		if (score >= 4096 + rand.nextInt(maxBonus + 1))
			color = getResources().getColor(R.color.colorPlayScore4);
		if (score >= 8192 + rand.nextInt(maxBonus + 1))
			color = getResources().getColor(R.color.colorPlayScore5);
		
		txtScoreVal.setTextColor(color);
		div3.setBackgroundColor(color);
		div4.setBackgroundColor(color);
	}
	
	private void scoreReset() {
		score = 0;
		txtScoreVal.setText(String.valueOf(score));
		int color = getResources().getColor(R.color.colorPlayScore1);
		txtScoreVal.setTextColor(color);
		div3.setBackgroundColor(color);
		div4.setBackgroundColor(color);
	}
	
	public void bnPauseClicked(View v) {
		Button bn = findViewById(R.id.bn_pause);
		Drawable bgToSet;
		if (paused == false) {
			paused = true;
			bn.setText(R.string.play_resume);
			bgToSet = ResourcesCompat.getDrawable(getResources(), R.drawable.eff_bn_activity_red_rev, null);
			vid.pause();
			sensorManager.unregisterListener(sigMovListener, sensor);
		} else {
			paused = false;
			bn.setText(R.string.play_pause);
			bgToSet = ResourcesCompat.getDrawable(getResources(), R.drawable.eff_bn_activity_red, null);
			sensorManager.registerListener(sigMovListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
		}
		
		bn.setBackground(bgToSet);
	}
	
	public void bnHelpClicked(View v) {
		// TODO
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		setCustTransAnim();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		vid.seekTo(1);
	}
	
	class SigMovListener implements SensorEventListener {
		
		boolean lastMovTriggered = false;
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			// "lacc" stands for linear acceleration.
			final float LACC_ACT_LIM = 22.5F, // 2.25 G total acc to activate;
									LACC_REC_LIM = 5.0F; // 0.5G total acc to recover.
			float laccX = event.values[0],
						laccY = event.values[1],
						laccZ = event.values[2];
			float laccTotal = (float) Math.sqrt(
				Math.pow(laccX, 2) + Math.pow(laccY, 2) + Math.pow(laccZ, 2)
			);
			
			if (lastMovTriggered == true && laccTotal < LACC_REC_LIM)
				lastMovTriggered = false;
			
			if (lastMovTriggered == false && laccTotal > LACC_ACT_LIM) {
				lastMovTriggered = true;
				scoreUp((int)laccTotal * 10, 125);
				vidCharPlayer.play(225, 300);
			}
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// Do NOTHING.
		}
		
	} // END OF class SigMovListener
	
	class VidCharPlayer {
		
		final long TRIG_INTV_NS = 325_000_000; // 325MS interval.
		private long timeLastTriggered = 0;
		
		public boolean play(int sleepMsec, int vidStartMsec) {
			long timeCurr = System.nanoTime();
			if (Math.abs(timeCurr - timeLastTriggered) < TRIG_INTV_NS)
				return false;
			timeLastTriggered = timeCurr;
			playActual(sleepMsec, vidStartMsec);
			return true;
		}
		
		private void playActual(int sleepMsec, int vidStartMsec) {
			VideoView vid = findViewById(R.id.vid_char);
			try { Thread.sleep(sleepMsec); } catch (Exception e) {}
			vid.seekTo(vidStartMsec);
			vid.start();
		}
		
	} // END OF class VidCharPlayer
	
}
