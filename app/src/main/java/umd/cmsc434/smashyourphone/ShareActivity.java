package umd.cmsc434.smashyourphone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class ShareActivity extends AppCompatActivity {
	
	private final String SHARE_IMG_NAME = "smashyourphone_share.jpg";
	private Uri uri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		
		Intent intent = getIntent();
		uri = Uri.parse(intent.getStringExtra("ImgUri"));
		ImageView vwShare = findViewById(R.id.vw_share);
		vwShare.setImageURI(uri);
		TextView txtFileLoc = findViewById(R.id.txt_file_loc);
		txtFileLoc.setText(getResources().getString(R.string.share_file_location) + " " + uri.toString());
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		try { takeSnapshotAgain(); } catch (Exception e) {}
	}
	
	private void takeSnapshotAgain() throws Exception {
		ConstraintLayout layoutPlay = findViewById(R.id.layout_share);
		Bitmap bitmap = Bitmap.createBitmap(layoutPlay.getWidth(), layoutPlay.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(getResources().getColor(R.color.colorShareBG));
		layoutPlay.draw(canvas);
		
		File file = new File(uri.toString());
		file.createNewFile();
		
		FileOutputStream stream = new FileOutputStream(file);
		bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream);
		stream.flush();
		stream.close();
	}
	
	public void bnShareClicked(View v) {
		grantStorePerm();
		File file = null;
		try { file = takeSnapshotToStorage(); } catch (Exception e) {}
		String url = null;
		try {
			url = MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), SHARE_IMG_NAME, "");
		} catch (Exception e) {}
		Uri uriContent = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_SEND);
		grantUriPerms(intent, uri);
		intent.setType("image/*");
		intent.putExtra(Intent.EXTRA_STREAM, uriContent);
		startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_title3)));
	}
	
	private File takeSnapshotToStorage() throws Exception {
		ConstraintLayout layoutPlay = findViewById(R.id.layout_share);
		Bitmap bitmap = Bitmap.createBitmap(layoutPlay.getWidth(), layoutPlay.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(getResources().getColor(R.color.colorShareBG));
		layoutPlay.draw(canvas);
		File file = new File(getExternalCacheDir(), SHARE_IMG_NAME);
		file.createNewFile();
		FileOutputStream stream = new FileOutputStream(file);
		bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream);
		return file;
	}
	
	private void grantUriPerms(Intent intent, Uri uri) {
		List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_ALL);
		for (ResolveInfo resolveInfo : resInfoList) {
			String packageName = resolveInfo.activityInfo.packageName;
			grantUriPermission(packageName, uri,  Intent.FLAG_GRANT_READ_URI_PERMISSION);
			grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
			grantUriPermission(packageName, uri, Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
			grantUriPermission(packageName, uri, Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
		}
	}
	
	private void grantStorePerm() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
									== PackageManager.PERMISSION_GRANTED) {
				return;
			} else {
				ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
				return;
			}
		}
		else { // perm granted upon installation before Android M (5.0)
			return;
		}
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
