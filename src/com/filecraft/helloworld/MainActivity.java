/**
The MIT License (MIT)

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
associated documentation files (the "Software"), to deal in the Software without restriction, 
including without limitation the rights to use, copy, modify, merge, publish, distribute, 
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is 
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or 
substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT 
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT 
OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.filecraft.helloworld;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getClass().getSimpleName();
	private static final String FILECRAFT_PACKAGE = "com.filecraft";

	private Button _launchFileCraftButton = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set an onClickListener that will either go to the play market or launch FileCraft
		// depending on whether or not FileCraft is installed.
		_launchFileCraftButton = (Button) findViewById(R.id.launch_app_button);
		_launchFileCraftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Intent intent = getPackageManager().getLaunchIntentForPackage(FILECRAFT_PACKAGE);
					if (intent == null) {
						intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse("market://details?id=" + FILECRAFT_PACKAGE));
						startActivity(intent);
					} else {
						startActivity(intent);
					}
				} catch (ActivityNotFoundException e) {
					Log.e(TAG, "Failed to start activity", e);
				}
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Update the button string each time the app resumes.
		Intent intent = getPackageManager().getLaunchIntentForPackage(FILECRAFT_PACKAGE);
		_launchFileCraftButton.setText(intent == null ?
				R.string.download_filecraft : R.string.open_filecraft);
	}
}
