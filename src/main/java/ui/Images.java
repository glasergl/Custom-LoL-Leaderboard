package ui;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Class which retrieves and provides all necessary images.
 * 
 * @author glasergl
 */
public final class Images {
	public static final ImageIcon CHALLENGER_ICON = retrieveImageIcon("Challenger_Icon.png", 175, 175);
	public static final ImageIcon GRANDMASTER_ICON = retrieveImageIcon("Grandmaster_Icon.png", 140, 140);
	public static final ImageIcon MASTER_ICON = retrieveImageIcon("Master_Icon.png", 110, 110);
	public static final Image FRAME_ICON = retrieveImage("Frame_Icon.png", 40, 40);
	public static final ImageIcon RANK_UP_ICON = retrieveImageIcon("Up_Icon.png", 15, 15);
	public static final ImageIcon RANK_DOWN_ICON = retrieveImageIcon("Down_Icon.png", 15, 15);

	private static Image retrieveImage(final String fileName) {
		try {
			final InputStream inputStream = Images.class.getClassLoader().getResourceAsStream(fileName);
			System.out.println(Images.class.getClassLoader());
			return ImageIO.read(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Couldn't load file \"" + fileName + "\"." + Images.class.getClassLoader());
		}
	}

	private static Image retrieveImage(final String fileName, final int scaledXSize, final int scaledYSize) {
		final Image image = retrieveImage(fileName);
		return image.getScaledInstance(scaledXSize, scaledYSize, Image.SCALE_SMOOTH);
	}

	private static ImageIcon retrieveImageIcon(final String fileName, final int scaledXSize, final int scaledYSize) {
		final Image image = retrieveImage(fileName, scaledXSize, scaledYSize);
		return new ImageIcon(image);
	}
}
