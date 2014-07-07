package hu.mep.utils.others;

import hu.mep.datamodells.Session;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

public class AlertDialogFactory {
	
	public static AlertDialog prepareAlertDialogForNoConnection(final Activity act) {
		AlertDialog.Builder adb = new AlertDialog.Builder(act);
		adb.setCancelable(true)
				.setTitle("Hiba a bejelentkezés során!")
				.setMessage("Nincs internet kapcsolata!\nMit kíván tenni?")
				.setPositiveButton("Wi-Fi beállítások",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								if (which == DialogInterface.BUTTON_POSITIVE) {
									act.startActivity(new Intent(
											Settings.ACTION_WIFI_SETTINGS));
								}
							};

						})
				.setNegativeButton("Vissza",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (which == DialogInterface.BUTTON_NEGATIVE) {
									Session.dismissAndMakeNullAlertDialog();
								}
							}
						});
		return adb.create();
	}
 
	
	public static AlertDialog prepareAlertDialogForBadCredentials(final Activity act) {
		AlertDialog.Builder adb = new AlertDialog.Builder(act);
		adb.setCancelable(true)
				.setTitle("Hiba a bejelentkezés során!")
				.setMessage(
						"Sikertelen bejelentkezés!\nHelytelen felhasználónév vagy jelszó!")
				.setNegativeButton("Vissza",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (which == DialogInterface.BUTTON_NEGATIVE) {
									Session.dismissAndMakeNullAlertDialog();
								}
							}
						});
		return adb.create();
	}
	
}
