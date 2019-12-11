package umd.cmsc434.smashyourphone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ShareActivity extends AppCompatActivity {
	
	private Uri uri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		
		Intent intent = getIntent();
		uri = Uri.parse(intent.getStringExtra("ImgUri"));
		ImageView vwShare = findViewById(R.id.vw_share);
		vwShare.setImageURI(uri);
		try { takeSnapshotAgain(); } catch (Exception e) {}
		TextView txtFileLoc = findViewById(R.id.txt_file_loc);
		txtFileLoc.setText(getResources().getString(R.string.share_file_location) + " " + uri.toString());
	}
	
	private void takeSnapshotAgain() throws Exception {
		ConstraintLayout layoutPlay = findViewById(R.id.layout_share);
		Bitmap bitmap = Bitmap.createBitmap(layoutPlay.getWidth(), layoutPlay.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		layoutPlay.draw(canvas);
		
		ContentResolver resolver = getContentResolver();
		
		OutputStream stream = resolver.openOutputStream(uri);
		bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream);
		stream.flush();
		stream.close();
	}
	
	public void bnShareClicked(View v) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.putExtra(Intent.EXTRA_STREAM, uri);
		startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_title3)));
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
	
	public void icSettingsClicked(View v) {
		openSettingsActivity();
	}
	
	private void openSettingsActivity() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
		setCustTransAnim();
	}
	
}
