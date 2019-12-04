package umd.cmsc434.smashyourphone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
		openActivityCharacter();
	}
	
	public void bnExitClicked(View v) {
		finishAffinity();
	}
	
	public void openActivityCharacter() {
		Intent intent = new Intent(this, CharacterActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
	}
	
}
