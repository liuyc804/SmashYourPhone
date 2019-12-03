package umd.cmsc434.smashyourphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

public class PlayActivity extends AppCompatActivity {
	private VidCharPlayer vidCharPlayer = new VidCharPlayer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		VideoView vid = findViewById(R.id.vid_char);
		String vidPath = "android.resource://" + getPackageName() + "/" + R.raw.play_marmot_scream;
		vid.setVideoURI(Uri.parse(vidPath));
		vid.setZOrderOnTop(true);
		vid.seekTo(1);
		
		SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		sensorManager.registerListener(new SigMovListener(), sensor, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void vidCharClicked(View v) {
		vidCharPlayer.play(225, 325);
	}
	
	class SigMovListener implements SensorEventListener {
		
		boolean lastMovTriggered = false;
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			// "lacc" stands for linear acceleration.
			final float LACC_ACT_LIM = 22.5F; // 2.25 G total acc to activate;
			final float LACC_REC_LIM = 5.0F; // 0.5G total acc to recover.
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
				vidCharPlayer.play(225, 325);
			}
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// Do nothing.
		}
		
	} // END OF class SigMovListener
	
	class VidCharPlayer {
		
		final long TRIG_INTV_NS = 300_000_000; // 300MS interval.
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
