package ui;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Class with functions that return Swing components with default attributes,
 * e.g., JButtons without a painted focus-rectangle.
 * 
 * @author glasergl
 */
public final class CustomizedSwing {
	static {
		System.setProperty("awt.useSystemAAFontSettings", "on"); // enables anti-aliasing for text in Swing gui
	}

	private static final int DEFAULT_TEXT_SIZE = 20;
	private static final Font DEFAULT_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, DEFAULT_TEXT_SIZE);

	public static JButton getJButton(final String buttonText, final int buttonTextSize) {
		final JButton jButton = new JButton(buttonText);
		jButton.setFocusPainted(false);
		jButton.setFont(DEFAULT_FONT);
		return jButton;
	}

	public static JButton getJButton(final String buttonText) {
		return getJButton(buttonText, DEFAULT_TEXT_SIZE);
	}

	public static JButton getJButton() {
		return getJButton("");
	}

	public static JLabel getJLabel(final String labelText, final int textSize) {
		final JLabel jLabel = new JLabel(labelText);
		jLabel.setFont(CustomizedSwing.DEFAULT_FONT);
		jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		return jLabel;
	}

	public static JLabel getJLabel(final String labelText) {
		return getJLabel(labelText, DEFAULT_TEXT_SIZE);
	}

	public static JLabel getJLabel() {
		return getJLabel("");
	}

	public static JLabel getJLabel(final Icon icon, final int width, final int height) {
		final JLabel jLabel = new JLabel(icon);
		jLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel.setPreferredSize(new Dimension(width, height));
		return jLabel;
	}

	public static JLabel getJLabel(final Icon icon) {
		return getJLabel(icon, icon.getIconWidth(), icon.getIconHeight());
	}

	public static JScrollPane wrapInJScrollPane(final JComponent jComponent) {
		final JScrollPane jScrollPane = new JScrollPane(jComponent, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		return jScrollPane;
	}
}
