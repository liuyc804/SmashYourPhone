package umd.cmsc434.smashyourphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
	}
	
	public void bnStartClicked(View v) {
		openCharacterActivity();
	}
	
	public void openCharacterActivity() {
		Intent intent = new Intent(this, CharacterActivity.class);
		startActivity(intent);
		setCustTransAnim();
	}
	
	private void setCustTransAnim() {
		overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
	}
	
	public void bnSettingsClicked(View v) {
		openSettingsActivity();
	}
	
	private void openSettingsActivity() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
		setCustTransAnim();
	}
	
	public void bnHelpClicked(View v) {
		openSettingsHelpActivity();
	}
	
	private void openSettingsHelpActivity() {
		Intent intent = new Intent(this, SettingsHelpActivity.class);
		startActivity(intent);
		setCustTransAnim();
	}
	
	public void bnExitClicked(View v) {
		finishAffinity();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		setCustTransAnim();
	}
	
}
