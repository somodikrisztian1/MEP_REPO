package hu.mep.utils.others;

import hu.mep.datamodells.Session;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.provider.Settings;

public class AlertDialogFactory {
	
	public static AlertDialog prepareAlertDialogForNoConnection(final Activity act) {
		AlertDialog.Builder adb = new AlertDialog.Builder(act);
		adb.setCancelable(true)
				.setTitle("Hiba történt!")
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
	
	public static AlertDialog prepareAlertDialogForBadUsername(final Activity act) {
		AlertDialog.Builder adb = new AlertDialog.Builder(act);
		adb.setCancelable(true)
				.setTitle("Hiba a regisztráció során!")
				.setMessage(
						"Adjon meg egy új, érvényes felhasználónevet!")
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
	
	public static AlertDialog prepareAlertDialogForBadPasswords(final Activity act) {
		AlertDialog.Builder adb = new AlertDialog.Builder(act);
		adb.setCancelable(true)
				.setTitle("Hiba a regisztráció során!")
				.setMessage(
						"Adjon meg egy új, érvényes jelszót!\n"
						+ "A jelszónak legalább egy szóköztől különböző karakterből kell állnia.")						
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
	
	public static AlertDialog prepareAlertDialogForTrimmedPasswords(final Activity act, final String username, final String password, final String email, final String fullname) {
		AlertDialog.Builder adb = new AlertDialog.Builder(act);
		adb.setCancelable(true)
				.setTitle("Figyelem!")
				.setMessage(
						"A jelszó elejéről és végéről levágásra kerültek a szóköz karakterek.\n"
						+ "Az így kapott érvényes jelszó:\n"
						+ password)		
				.setNegativeButton("Új jelszó",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (which == DialogInterface.BUTTON_NEGATIVE) {
									Session.dismissAndMakeNullAlertDialog();
								}
							}
						}).setPositiveButton("Elfogadom", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if(which == DialogInterface.BUTTON_POSITIVE) {
									Session.getActualCommunicationInterface().registrateUser(fullname, email, username, password);
									Session.dismissAndMakeNullAlertDialog();
								}
							}
						});
		return adb.create();
	}
	
	public static AlertDialog prepareAlertDialogForNoFullyLoadedCells(final Activity act) {
		AlertDialog.Builder adb = new AlertDialog.Builder(act);
		adb.setCancelable(true)
				.setTitle("Hiba a regisztráció során!")
				.setMessage(
						"Minden mezőt kötelező kitölteni az érvényes regisztrációhoz.")					
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
	
	public static AlertDialog prepareAlertDialogForNoMatchingPasswords(final Activity act) {
		AlertDialog.Builder adb = new AlertDialog.Builder(act);
		adb.setCancelable(true)
				.setTitle("Hiba a regisztráció során!")
				.setMessage(
						"A beírt jelszavak nem egyeznek meg.\nKérem adja meg újból a jelszavakat!")				
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
	
	public static AlertDialog prepareAlertDialogWithText(final Activity act, String text) {
		AlertDialog.Builder adb = new AlertDialog.Builder(act);
		adb.setCancelable(true)
				.setTitle("Hiba a regisztráció során!")
				.setMessage(text)				
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
