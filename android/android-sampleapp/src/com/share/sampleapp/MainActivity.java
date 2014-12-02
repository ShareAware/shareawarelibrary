package com.share.sampleapp;

import java.io.File;
import java.io.FileOutputStream;

import com.share.wom.sharewarelibrary.ShareWareArtifact;
import com.share.wom.sharewarelibrary.ShareWareLibrary;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {
	private final static String TAG = MainActivity.class.getSimpleName();
	boolean isFree = true;
	ShareWareArtifact[] artifactsArray;
	Button[] featureArray;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupDemoButtons();
        getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	void setupDemoButtons() {		
		Button f1Button = (Button) findViewById(R.id.feature1Button);
		Button f2Button = (Button) findViewById(R.id.feature2Button);
		Button a1Button = (Button) findViewById(R.id.asset1Button);
		Button d1Button = (Button) findViewById(R.id.document1Button);
		Button v1Button = (Button) findViewById(R.id.video1Button);
		Button m1Button = (Button) findViewById(R.id.music1Button);
		Button scButton = (Button) findViewById(R.id.screenCaptureButton);
		scButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	MainActivity.this.captureScreen();
            }
        });				

		Button[] bArray = {f1Button, f2Button, a1Button, d1Button, v1Button, m1Button};
		featureArray = bArray;
		artifactsArray = new ShareWareArtifact[featureArray.length];
		for (int ii = 0; ii < featureArray.length; ii++) {
			Button button = featureArray[ii];
			artifactsArray[ii] = new ShareWareArtifact(button.getText().toString(),
														(isFree) ? "true" : "false",
														"");
			button.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	            	Button but = (Button) v;
	                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
	                dialog.setTitle(but.getText());
	                dialog.setMessage("This is demonstrating " + but.getText() + " feature/function");
	                dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                  	  public void onClick(DialogInterface dialog, int whichButton) {
                  	  }
                    });
	                dialog.create().show();	 
	            }
			});
		}
        RadioButton freeButton = (RadioButton) findViewById(R.id.freeButton);
        freeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	isFree = true;
            	setupArtifactsPerms(isFree);
            }
        });
        setupArtifactsPerms(isFree);
        RadioButton paidButton = (RadioButton) findViewById(R.id.paidButton);
        paidButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	isFree = false;
            	setupArtifactsPerms(isFree);
            	Log.i(TAG, "isFree: " + isFree);
            }
        });
	}

	void setupArtifactsPerms(boolean is) {
		for (ShareWareArtifact swa : artifactsArray) {
			swa.permission = (!is) ? "true" : "false";
		}
		for (Button button : featureArray) {
			button.setEnabled(!is);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	int itemId = item.getItemId();
    	if (itemId == R.id.action_settings) {
    		launchLibrary();
    		return true;    		
    	} else if (itemId == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Method to launch the Share library
     */
    void launchLibrary() {
        Handler uh = new Handler(MainActivity.this.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
            	if (msg != null) {
            		String name 		= msg.getData().getString("name");
            		String permission 	= msg.getData().getString("permission");
	            	String sharedWith 	= msg.getData().getString("sharedWith");
	            	updatePermission(name, permission, sharedWith);
	            	Log.i(TAG, "Message from Library: " + name + ", " + permission + ", " + sharedWith);
            	}
            }
        };

		ShareWareLibrary swl = new ShareWareLibrary(uh);
		swl.setIsFree(MainActivity.this.isFree);
		swl.setShareWareArtifact(MainActivity.this.artifactsArray);
		swl.launchUI(MainActivity.this);	
    }
    
    /**
     * UpdatePermission - callback from the library handler
     * @param name - Name of artifact
     * @param p    - Permission
     * @param s    - Shared with
     */
    void updatePermission(String n, String p, String s) {
    	Log.i(TAG, "updatePermission: " + n + ", " + p + ", " + s);
    	int ii = 0;
    	ShareWareArtifact tSWA = null;
    	for (ShareWareArtifact swa : this.artifactsArray) {
    		if (swa.name.equals(n)) {
    			swa.permission = p;
    			swa.sharedWith = s;
    			tSWA = swa;
    			break;
    		}
			ii++;
    	}
    	if (tSWA != null) {
    		artifactsArray[ii] = tSWA;
    	}
    	ii = 0; 
		for (Button button : featureArray) {
			button.setEnabled(artifactsArray[ii].permission.equals("true"));
			ii++;
		}
    }
    
    private void captureScreen() {
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.activity_main);
        File root = Environment.getExternalStorageDirectory();
        File shareFolder = new File(root, "Share");
        if (!shareFolder.exists()) {
        	shareFolder.mkdir();
        }
        File file = new File(shareFolder,"shareScreen.jpg");
        Bitmap b = Bitmap.createBitmap(mainLayout.getWidth(), mainLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        mainLayout.draw(c);
        FileOutputStream fos = null;
        try {
           fos = new FileOutputStream(file);
           if (fos != null) {
              b.compress(Bitmap.CompressFormat.JPEG, 90, fos);
              fos.close();
           }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}

