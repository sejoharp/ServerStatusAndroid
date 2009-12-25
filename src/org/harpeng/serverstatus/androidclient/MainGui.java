package org.harpeng.serverstatus.androidclient;

import org.harpeng.serverstatus.client.IGui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainGui extends Activity implements IGui, Callback {
	private AndroidLogic logic;
	private boolean isOnline;
	private Handler handler;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);
		((Button) findViewById(R.id.abortButton))
				.setOnClickListener(new AbortOnClickListener());
		((Button) findViewById(R.id.actionButton))
				.setOnClickListener(new ActionOnClickListener());
		this.handler = new Handler(this);
		this.logic = new AndroidLogic(this);
		this.logic.start();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "Settings");
		menu.add(0, 2, 0, "Exit");
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			startActivity(new Intent(this, Settings.class));
			return true;
		case 2:
			this.finish();
			return true;
		}
		return false;
	}

	@Override
	public void setServerState(boolean isOnline) {
		this.isOnline = isOnline;
		if (isOnline == true) {
			((TextView) findViewById(R.id.stateTextView))
					.setText("server is online.");
			((Button) findViewById(R.id.actionButton))
					.setText("shutdown server");
			((TextView) findViewById(R.id.freeSpaceView)).setText(String
					.valueOf(this.logic.getFreeSpaceFromServer()
							/ (1024 * 1024 * 1024))
					+ " GB");
		} else {
			((TextView) findViewById(R.id.stateTextView))
					.setText("server is offline.");
			((Button) findViewById(R.id.actionButton)).setText("wakeup server");
		}
	}

	public void showWakeUpState(boolean available) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if (available == true)
			builder.setMessage("server is online.");
		else
			builder.setMessage("wake up failed.");
		builder.setCancelable(false);
		builder.setPositiveButton("Ok", new MyOnClickListener());
		builder.create();
		builder.show();
	}

	private class MyOnClickListener implements OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			logic.closeProgram();
		}
	}

	private class AbortOnClickListener implements
			android.view.View.OnClickListener {
		public void onClick(View v) {
			logic.closeProgram();
		}
	}

	private class ActionOnClickListener implements
			android.view.View.OnClickListener {

		public void onClick(View v) {
			if (isOnline == true)
				logic.shutdownServer();
			else {
				ProgressDialog dialog = ProgressDialog.show(MainGui.this, "",
						"Waking up. Please wait...", true);
				logic.wakeUp(handler);
			}
		}
	}

	@Override
	public boolean handleMessage(Message msg) {
		if (msg.what == 0)
			showWakeUpState(false);
		else if (msg.what == 1)
			showWakeUpState(true);
		// logic.closeProgram();
		return false;
	}
}