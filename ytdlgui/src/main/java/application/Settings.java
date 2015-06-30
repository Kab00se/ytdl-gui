package application;

import java.nio.file.Path;

public class Settings {
	// VARS
	private Path youtubeDLPath;		// If null --> assume it's on the system path?
	private Path downloadDirectory; // Default =>  System.getProperty("user.dir")

	private String fileTemplate; // TODO: Possibly provide some file templates
									// out of the box?

}
