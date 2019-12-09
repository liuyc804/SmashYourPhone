package umd.cmsc434.smashyourphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SettingsLanguageActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_language);
	}
	
	public void icBackArrowClicked(View v) {
		onBackPressed();
	}
	
	public void langEnClicked(View v) {
		Toast.makeText(getApplicationContext(), "Language set to English.", Toast.LENGTH_SHORT).show();
		finish();
		setCustTransAnim();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		setCustTransAnim();
	}
	
	private void setCustTransAnim() {
		overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
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
	
}
