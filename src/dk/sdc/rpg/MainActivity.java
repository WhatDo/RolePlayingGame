package dk.sdc.rpg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import dk.sdc.helloworld.R;

public class MainActivity extends Activity {

	private DragonCanvas canvas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		canvas = (DragonCanvas) findViewById(R.id.dragonCanvas1);

		Button att = (Button) findViewById(R.id.attack);
		Button attS = (Button) findViewById(R.id.strong);

		att.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				canvas.attackClicked();
				canvas.invalidate();
			}
		});

		attS.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				canvas.strongAttackClicked();
				canvas.invalidate();
			}
		});
	}
}
