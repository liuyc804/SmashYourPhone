package umd.cmsc434.smashyourphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CharacterActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character);
	}
	
	public void bnConfirmClicked(View v) {
		openPlayActivity();
	}
	
	public void openPlayActivity() {
		Intent intent = new Intent(this, PlayActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
	}
	
}
