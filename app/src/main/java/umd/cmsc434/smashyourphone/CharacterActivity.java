package umd.cmsc434.smashyourphone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CharacterActivity extends AppCompatActivity {
	private ArrayList<CharacterProfile> chars;
	private Button bnPrev, bnNext, bnConfirm;
	private int charIdx;
	private ImageView vwChar;
	private TextView txtName, txtIntro;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character);
		
		chars = new ArrayList<>();
		chars.add(new CharacterProfile(R.drawable.ic_char_giraffe, R.string.char_giraffe_name, R.string.char_giraffe_intro, false));
		chars.add(new CharacterProfile(R.drawable.ic_char_marmot, R.string.char_marmot_name, R.string.char_marmot_intro, true));
		chars.add(new CharacterProfile(R.drawable.ic_char_emu, R.string.char_emu_name, R.string.char_emu_intro, false));
		bnPrev = findViewById(R.id.bn_prev);
		bnNext = findViewById(R.id.bn_next);
		bnConfirm = findViewById(R.id.bn_confirm);
		charIdx = 1;
		vwChar = findViewById(R.id.vw_char);
		txtName = findViewById(R.id.txt_char_name);
		txtIntro = findViewById(R.id.txt_char_intro);
	}
	
	public void bnConfirmClicked(View v) {
		openPlayActivity();
	}
	
	private void openPlayActivity() {
		Intent intent = new Intent(this, PlayActivity.class);
		startActivity(intent);
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
	
	public void bnPrevClicked(View v) {
		boolean prevEnabled = true,
						nextEnabled = true;
		charIdx -= 1;
		if (charIdx <= 0) {
			prevEnabled = false;
			charIdx = 0;
		}
		bnPrev.setEnabled(prevEnabled);
		bnNext.setEnabled(nextEnabled);
		setCharacter(chars.get(charIdx));
	}
	
	public void bnNextClicked(View v) {
		boolean prevEnabled = true,
						nextEnabled = true;
		charIdx += 1;
		if (charIdx >= chars.size() - 1) {
			nextEnabled = false;
			charIdx = chars.size() - 1;
		}
		bnPrev.setEnabled(prevEnabled);
		bnNext.setEnabled(nextEnabled);
		setCharacter(chars.get(charIdx));
	}
	
	private void setCharacter(CharacterProfile profile) {
		txtName.setText(profile.nameResID);
		txtIntro.setText(profile.introResID);
		bnConfirm.setEnabled(profile.supported);
		
		// slower operation goes later
		vwChar.setImageDrawable(ResourcesCompat.getDrawable(getResources(), profile.icResId, null));
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		setCustTransAnim();
	}
	
	private class CharacterProfile {
		
		public final int icResId;
		public final int nameResID;
		public final int introResID;
		public final boolean supported;
		
		public CharacterProfile(int icResId, int nameResID, int introResID, boolean supported) {
			this.icResId = icResId;
			this.nameResID = nameResID;
			this.introResID = introResID;
			this.supported = supported;
		}
		
	}
	
}
