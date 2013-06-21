package com.facebook.android.foxeswithfriends;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseFile;
import com.parse.ParseObject;

public class AddFoxActivity extends Activity {
	
	private byte[] foxArray;
	private ByteArrayOutputStream foxPhoto = new ByteArrayOutputStream();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_fox);
		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_fox, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/** Called when the user clicks the Photo button */
	public void getFoxPhoto(View view) {
		final int SELECT_PHOTO = 100;
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, SELECT_PHOTO);	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
	    
	    final int SELECT_PHOTO = 100;
	    
	    switch(requestCode) { 
	    case SELECT_PHOTO:
	        if(resultCode == RESULT_OK){  
	            Uri selectedImage = imageReturnedIntent.getData();
	            InputStream imageStream;
				try {
					imageStream = getContentResolver().openInputStream(selectedImage);
					BitmapFactory.decodeStream(imageStream).compress(Bitmap.CompressFormat.JPEG, 70, foxPhoto);
					foxArray = foxPhoto.toByteArray();
					
				} catch (FileNotFoundException e) {
					Log.d("photoPicker", "Error: " + e.getMessage());
					foxPhoto = null;
				}
	        }
	    }
	}
	
	/** Called when the user clicks the Save button */
	public void saveFox(View view) {
		EditText editText = (EditText) findViewById(R.id.name_input);
		String name = editText.getText().toString();
		Log.d("Fox name", name);
		
		ParseObject fox = new ParseObject("Fox");
		fox.put("name", name);
		if (foxPhoto != null) {
			ParseFile imageFile = new ParseFile("fox.jpg", foxArray);
			fox.put("image", imageFile);
		}
		fox.saveInBackground();

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

}
