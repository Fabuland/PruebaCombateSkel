package paqCombate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.Icon;

public class Combate extends JFrame {

	JPanel menuPrincipal;
	JLabel fondo, esqueleto, enemigo, barraVidaEsq, barraVidaEnmg, vidaActualEsqTxt, vidaActualEnmgTxt, bolaFuego;
	int vidaTotalEsq, vidaActualEsq, vidaTotalEnmg, vidaActualEnmg;
	JButton btnAtaque;
	int dañoEsq, dañoEnmg;

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

		ajustarVidas();
		menuPrincipal = new JPanel();
		menuPrincipal.setBounds(0, 0, 944, 502);
		getContentPane().add(menuPrincipal);
		menuPrincipal.setVisible(true);
		menuPrincipal.setLayout(null);

		fondo = new JLabel("");
		fondo.setIcon(new ImageIcon(Combate.class.getResource("background.gif")));
		fondo.setBounds(0, 0, 944, 502);
		menuPrincipal.add(fondo);

		esqueleto = new JLabel("");
		esqueleto.setIcon(new ImageIcon(Combate.class.getResource("skeleton.gif")));
		esqueleto.setBounds(10, 220, 520, 280);
		fondo.add(esqueleto);

		btnAtaque = new JButton("");
		btnAtaque.setIcon(new ImageIcon(Combate.class.getResource("btnataque.png")));
		btnAtaque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atacar();
			}
		});
		btnAtaque.setBounds(430, 215, 80, 40);
		btnAtaque.setOpaque(false);
		btnAtaque.setContentAreaFilled(false);
		btnAtaque.setBorderPainted(false);
		fondo.add(btnAtaque);

		barraVidaEsq = new JLabel("");
		barraVidaEsq.setIcon(new ImageIcon(Combate.class.getResource("barraVida.png")));
		barraVidaEsq.setBounds(90, 220, 260, 29);
		fondo.add(barraVidaEsq);

		vidaActualEsqTxt = new JLabel(vidaActualEsq + "/" + vidaTotalEsq);
		vidaActualEsqTxt.setBounds(360, 220, 80, 29);
		vidaActualEsqTxt.setFont(new Font("Tahoma", Font.BOLD, 16));
		vidaActualEsqTxt.setForeground(Color.BLACK);
		fondo.add(vidaActualEsqTxt);

		enemigo = new JLabel("");
		enemigo.setIcon(new ImageIcon(Combate.class.getResource("slime.gif")));
		enemigo.setBounds(630, 300, 200, 200);
		fondo.add(enemigo);

		barraVidaEnmg = new JLabel("");
		barraVidaEnmg.setIcon(new ImageIcon(Combate.class.getResource("barraVida.png")));
		barraVidaEnmg.setBounds(600, 220, 260, 29);
		fondo.add(barraVidaEnmg);

		vidaActualEnmgTxt = new JLabel(vidaActualEnmg + "/" + vidaTotalEnmg);
		vidaActualEnmgTxt.setBounds(540, 220, 80, 29);
		vidaActualEnmgTxt.setFont(new Font("Tahoma", Font.BOLD, 16));
		vidaActualEnmgTxt.setForeground(Color.BLACK);
		fondo.add(vidaActualEnmgTxt);

		bolaFuego = new JLabel("");
		bolaFuego.setIcon(new ImageIcon(Combate.class.getResource("fireball.gif")));
		bolaFuego.setVisible(false);
		bolaFuego.setBounds(470, 300, 200, 200);
		fondo.add(bolaFuego);

	}

	public void atacar() {

		Timer time = new Timer(250, null);
		ActionListener listener = new ActionListener() {
			int contTiempo = 0;
			int posBolaFuego = 470;

			public void actionPerformed(ActionEvent e) {
				esqueleto.setIcon(new ImageIcon(Combate.class.getResource("skeletonattack.gif")));
				contTiempo++;
				if (contTiempo > 7 && contTiempo <= 15) {
					esqueleto.setIcon(new ImageIcon(Combate.class.getResource("skeleton.gif")));
					bolaFuego.setVisible(true);
					posBolaFuego -= 15;
					bolaFuego.setBounds(posBolaFuego, 300, 200, 200);
				} else if (contTiempo > 15 && contTiempo <= 20) {
					bolaFuego.setVisible(false);
					esqueleto.setIcon(new ImageIcon(Combate.class.getResource("skeleton.gif")));
					if (vidaActualEsq == 0) {
						esqueleto.setIcon(new ImageIcon(Combate.class.getResource("skeletondying.gif")));
					} else if (vidaActualEsq > 0) {
						time.stop();
					}
				}else if(contTiempo > 20) {
					esqueleto.setIcon(new ImageIcon(Combate.class.getResource("skeletondead.png")));
					esqueleto.setBounds(10, 420, 390, 60);
					btnAtaque.setEnabled(false);
					time.stop();
				}
				if (contTiempo == 6) {
					vidaActualEnmg = vidaActualEnmg - dañarEnmg();
					if (vidaActualEnmg < 1) {
						vidaActualEnmg = 0;
					}
					vidaActualEnmgTxt.setText(vidaActualEnmg + "/" + vidaTotalEnmg);
				} else if (contTiempo == 13) {
					vidaActualEsq = vidaActualEsq - dañarEsq();
					if (vidaActualEsq < 1) {
						vidaActualEsq = 0;
					}
					vidaActualEsqTxt.setText(vidaActualEsq + "/" + vidaTotalEsq);
					
				}
				System.out.println(vidaActualEsq);
				if (vidaActualEsq < ((vidaTotalEsq / 8) * 7) && vidaActualEsq >= (vidaTotalEsq / 8) * 6) {
					barraVidaEsq.setIcon(new ImageIcon(Combate.class.getResource("barraVida1.png")));
				} else if (vidaActualEsq < ((vidaTotalEsq / 8) * 6) && vidaActualEsq >= (vidaTotalEsq / 8) * 5) {
					barraVidaEsq.setIcon(new ImageIcon(Combate.class.getResource("barraVida2.png")));
				} else if (vidaActualEsq < ((vidaTotalEsq / 8) * 5) && vidaActualEsq >= (vidaTotalEsq / 8) * 4) {
					barraVidaEsq.setIcon(new ImageIcon(Combate.class.getResource("barraVida3.png")));
				} else if (vidaActualEsq < ((vidaTotalEsq / 8) * 4) && vidaActualEsq >= (vidaTotalEsq / 8) * 3) {
					barraVidaEsq.setIcon(new ImageIcon(Combate.class.getResource("barraVida4.png")));
				} else if (vidaActualEsq < ((vidaTotalEsq / 8) * 3) && vidaActualEsq >= (vidaTotalEsq / 8) * 2) {
					barraVidaEsq.setIcon(new ImageIcon(Combate.class.getResource("barraVida5.png")));
				} else if (vidaActualEsq < ((vidaTotalEsq / 8) * 2) && vidaActualEsq >= (vidaTotalEsq / 8) * 1) {
					barraVidaEsq.setIcon(new ImageIcon(Combate.class.getResource("barraVida6.png")));
				} else if (vidaActualEsq < ((vidaTotalEsq / 8) * 1) && vidaActualEsq > 0) {
					barraVidaEsq.setIcon(new ImageIcon(Combate.class.getResource("barraVida7.png")));
				} else if (vidaActualEsq == 0) {
					barraVidaEsq.setIcon(new ImageIcon(Combate.class.getResource("barraVida8.png")));
				}
			}
		};
		time.addActionListener(listener);
		time.start();

	}

	public int dañarEnmg() {

		int randomNum = ThreadLocalRandom.current().nextInt(dañoEsq, dañoEsq + 5);
		return randomNum;

	}

	public int dañarEsq() {

		int randomNum = ThreadLocalRandom.current().nextInt(dañoEnmg, dañoEnmg + 3);
		return randomNum;

	}

	public void ajustarVidas() {

		vidaActualEsq = 40;
		vidaTotalEsq = 40;
		vidaActualEnmg = 15;
		vidaTotalEnmg = 15;
		dañoEsq = 7;
		dañoEnmg = 7;

	}

}
