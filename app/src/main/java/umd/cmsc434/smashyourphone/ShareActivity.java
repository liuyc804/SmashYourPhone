package umd.cmsc434.smashyourphone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ShareActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
	}
	
}
