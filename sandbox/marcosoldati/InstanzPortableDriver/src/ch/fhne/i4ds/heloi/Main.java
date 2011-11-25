package ch.fhne.i4ds.heloi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.*;
//import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();
		ZipHandler zipHandler = new ZipHandler();
		String os = System.getProperty("os.name").toLowerCase();
		File osTarget = new File("");	
		URL url;
		File fileName = new File("FirefoxLoader.exe");

		List<BrowserConfig> browsers = new ArrayList<BrowserConfig>();

		if (os.indexOf("win") >= 0) {
			osTarget = new File("C:\\Temp");

			File pdTarget = new File(osTarget, "Portable_Firefox_4.0");
			pdTarget.mkdirs(); // Create a new folder for the browser.
			pdTarget = new File(pdTarget, "Portable_Firefox_4.0.zip");
			try {
				url = new URL(
						"http://helio-dev.cs.technik.fhnw.ch/portable_driver/Portable_Firefox_4.0.zip");
				browsers.add(new BrowserConfig(url, "windows", 32, fileName,
						pdTarget, "firefox"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			// TODO chek it the browser is alrady instaled, else ad to browsers

		} else if (os.indexOf("mac") >= 0) {
			// osTarget = new File("");

		} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
			// osTarget = new File("");

		}

		for (BrowserConfig browserConfig : browsers) {
			main.download(browserConfig.getUrl(), browserConfig.getTargetName());
			// // Cheek if file exist before to unzip it
			// if (!(new File(browserConfig.targetName.toString())).exists()) {
			// System.out.println("unzipt: File exist "
			// + browserConfig.targetName.toString());

			if (zipHandler.unZip(browserConfig.getTargetName(), osTarget)) {
				System.out.println("Driver instaled "
						+ browserConfig.toString());
				// browsers.remove(browserConfig);
				// TODO erfolgreiche browser speichern
			} else {
				System.out.println("Driver instalation FAILD "
						+ browserConfig.toString());
			}
			// } else {
			// System.out.println(" alredy unzipt: File exist "
			// + browserConfig.targetName.toString());
			// // browsers.remove(browserConfig);
			// }
		}

	}

	/**
	 * Download the zip file of the Portable Browser
	 * 
	 * @param url
	 * @param target
	 */
	void download(URL url, File target) {
		try {
			// Do not download a zip witch already exist
			if ((new File(target.toString())).exists()) {
				System.out.println("download: File exist " + target.toString());
				return;
			}
			System.out.println("download: file:  " + target.toString());
			url.openConnection();
			InputStream reader = url.openStream();
			Thread.sleep(5500);
			System.out.println("target: " + target.toString());
			FileOutputStream writer = new FileOutputStream(target);
			byte[] buffer = new byte[153600];
			int totalBytesRead = 0;
			int bytesRead = 0;

			while ((bytesRead = reader.read(buffer)) > 0) {
				writer.write(buffer, 0, bytesRead);
				buffer = new byte[153600];
				totalBytesRead += bytesRead;
			}
			writer.close();
			reader.close();
			System.out.println("downlod sucsesful");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	
	}

}
