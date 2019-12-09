package umd.cmsc434.smashyourphone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
	}
	
	public void settingsLangClicked(View v) {
		openSettingsLanguageActivity();
	}
	
	private void openSettingsLanguageActivity() {
		Intent intent = new Intent(this, SettingsLanguageActivity.class);
		startActivity(intent);
		setCustTransAnim();
	}
	
	public void settingsAboutClicked(View v) {
		openSettingsAboutActivity();
	}
	
	private void openSettingsAboutActivity() {
		Intent intent = new Intent(this, SettingsAboutActivity.class);
		startActivity(intent);
		setCustTransAnim();
	}
	
	public void settingsHelpClicked(View v) {
		openSettingsHelpActivity();
	}
	
	private void openSettingsHelpActivity() {
		Intent intent = new Intent(this, SettingsHelpActivity.class);
		startActivity(intent);
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
