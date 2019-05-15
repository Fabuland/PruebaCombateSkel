package paqCombate;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Icon;

public class Combate extends JFrame {

	JPanel menuPrincipal;

	public Combate() throws IOException {

		Toolkit miPantalla = Toolkit.getDefaultToolkit();
		Dimension tamanoPantalla = miPantalla.getScreenSize();
		int alturaPantalla = tamanoPantalla.height;
		int anchoPantalla = tamanoPantalla.width;
		setSize(anchoPantalla / 2, alturaPantalla / 2);
		setLocation(anchoPantalla / 4, alturaPantalla / 4);
		setTitle("JavaFighter");
		// Image icono = miPantalla.getImage("src\\pic\\Madera1.png");
		// setIconImage(icono);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		menuPrincipal = new JPanel() {
			/*protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				BufferedImage img = null;
				try {
					img = ImageIO.read(new File("src\\pic\\volcanobackground.gif"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				g.drawImage(img, 0, 0, null);
			}*/
		};
		menuPrincipal.setBounds(0, 0, 944, 502);
		getContentPane().add(menuPrincipal);
		menuPrincipal.setVisible(true);
		menuPrincipal.setLayout(null);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Combate.class.getResource("src//paqCombate//ok2.gif")));
		label_1.setBounds(10, 86, 358, 336);
		menuPrincipal.add(label_1);

	}
}

