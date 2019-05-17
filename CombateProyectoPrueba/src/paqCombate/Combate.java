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
	JLabel fondo, esqueleto, enemigo, barraVidaEsq, barraVidaEnmg, vidaActualEsqTxt, vidaActualEnmgTxt, bolaFuego,
			nivelDisplayed, expNeeded;
	double vidaTotalEsq, vidaActualEsq, vidaTotalEnmg, vidaActualEnmg;
	int vidaTotalEsqDisplayed, vidaActualEsqDisplayed, vidaTotalEnmgDisplayed, vidaActualEnmgDisplayed;
	JButton btnAtaque;
	int dañoEsq, dañoEnmg, nivelActual, expNeed, expActual;
	ImageIcon slime, owlboy, mago, encapuchado;

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
		esqueleto.setIcon(new ImageIcon(Combate.class.getResource("skeletonwalking.gif")));
		esqueleto.setBounds(10, 220, 520, 280);
		fondo.add(esqueleto);

		nivelActual = 1;
		nivelDisplayed = new JLabel("Nivel " + nivelActual);
		nivelDisplayed.setFont(new Font("System", Font.BOLD, 28));
		nivelDisplayed.setForeground(Color.white);
		nivelDisplayed.setBounds(90, 185, 150, 30);
		fondo.add(nivelDisplayed);

		expNeed = 150;
		expNeeded = new JLabel("Exp necesaria: " + expNeed);
		expNeeded.setFont(new Font("System", Font.BOLD, 16));
		expNeeded.setForeground(Color.black);
		expNeeded.setBounds(50, 475, 260, 30);
		fondo.add(expNeeded);

		btnAtaque = new JButton("");
		btnAtaque.setIcon(new ImageIcon(Combate.class.getResource("btnataque.png")));
		btnAtaque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAtaque.setEnabled(false);
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

		vidaActualEsqTxt = new JLabel(vidaActualEsqDisplayed + "/" + vidaTotalEsqDisplayed);
		vidaActualEsqTxt.setBounds(360, 220, 80, 29);
		vidaActualEsqTxt.setFont(new Font("Impact", Font.PLAIN, 16));
		vidaActualEsqTxt.setForeground(Color.BLACK);
		fondo.add(vidaActualEsqTxt);

		enemigo = new JLabel("");
		slime = new ImageIcon(Combate.class.getResource("slime.gif"));
		owlboy = new ImageIcon(Combate.class.getResource("owlboy.gif"));
		mago = new ImageIcon(Combate.class.getResource("mago.gif"));
		encapuchado = new ImageIcon(Combate.class.getResource("encapuchado.gif"));
		enemigo.setIcon(mago);
		enemigo.setBounds(630, 300, 200, 200);
		fondo.add(enemigo);

		barraVidaEnmg = new JLabel("");
		barraVidaEnmg.setIcon(new ImageIcon(Combate.class.getResource("barraVida.png")));
		barraVidaEnmg.setBounds(600, 220, 260, 29);
		fondo.add(barraVidaEnmg);

		vidaActualEnmgTxt = new JLabel(vidaActualEnmgDisplayed + "/" + vidaTotalEnmgDisplayed);
		vidaActualEnmgTxt.setBounds(540, 220, 80, 29);
		vidaActualEnmgTxt.setFont(new Font("Impact", Font.PLAIN, 16));
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
				if(vidaActualEnmg > 0) {
					esqueleto.setIcon(new ImageIcon(Combate.class.getResource("skeletonattack.gif")));
				}else if(vidaActualEnmg == 0) {
					esqueleto.setIcon(new ImageIcon(Combate.class.getResource("skeleton.gif")));
				}
				contTiempo++;
				if ((contTiempo > 7 && contTiempo <= 12) && vidaActualEnmg > 0) {
					esqueleto.setIcon(new ImageIcon(Combate.class.getResource("skeleton.gif")));
					bolaFuego.setVisible(true);
					posBolaFuego -= 20;
					bolaFuego.setBounds(posBolaFuego, 300, 200, 200);
				} else if ((contTiempo > 12 && contTiempo <= 17) && vidaActualEnmg > 0) {
					bolaFuego.setVisible(false);
					esqueleto.setIcon(new ImageIcon(Combate.class.getResource("skeleton.gif")));
					if (vidaActualEsq == 0) {
						esqueleto.setIcon(new ImageIcon(Combate.class.getResource("skeletondying.gif")));
					} else if (vidaActualEsq > 0 && vidaActualEnmg > 0) {
						time.stop();
						btnAtaque.setEnabled(true);
					}
				} else if ((contTiempo > 17 && contTiempo <= 23) && vidaActualEsq == 0) {
					esqueleto.setIcon(new ImageIcon(Combate.class.getResource("skeletondead.png")));
					esqueleto.setBounds(10, 420, 390, 60);
					btnAtaque.setEnabled(false);
					time.stop();
				} else if (vidaActualEnmg == 0 && contTiempo == 12) { //muere el enemigo
					expActual += 150;
					vidaActualEnmg = vidaTotalEnmg;
					vidaActualEnmgDisplayed = (int) vidaActualEnmg;
					expNeeded.setText("Exp necesaria: " + expNeed);
					barraVidaEnmg.setIcon(new ImageIcon(Combate.class.getResource("barraVida.png")));
					cambiarEnemigo();
					vidaActualEnmgTxt.setText(vidaActualEnmgDisplayed + "/" + vidaTotalEnmgDisplayed);
					esqueleto.setIcon(new ImageIcon(Combate.class.getResource("skeleton.gif")));
					time.stop();
					btnAtaque.setEnabled(true);
					comprobarExpNecesaria();
				}
				
				if (contTiempo == 6) {
					vidaActualEnmg = vidaActualEnmg - dañarEnmg();
					vidaActualEnmgDisplayed = (int) vidaActualEnmg;
					if (vidaActualEnmgDisplayed < 1) {
						vidaActualEnmgDisplayed = 0;
						vidaActualEnmg = 0;
					}
					vidaActualEnmgTxt.setText(vidaActualEnmgDisplayed + "/" + vidaTotalEnmgDisplayed);
				} else if (contTiempo == 11 && vidaActualEnmg > 0) {
					vidaActualEsq = vidaActualEsq - dañarEsq();
					vidaActualEsqDisplayed = (int) vidaActualEsq;
					if (vidaActualEsqDisplayed < 1) {
						vidaActualEsqDisplayed = 0;
						vidaActualEsq = 0;
					}
					vidaActualEsqTxt.setText(vidaActualEsqDisplayed + "/" + vidaTotalEsqDisplayed);

				}
				cambiarBarraVidaEsq();
				cambiarBarraVidaEnmg();
				
				System.out.println(dañoEsq);
			}
		};
		time.addActionListener(listener);
		time.start();

	}

	public void cambiarBarraVidaEsq() {
		if (vidaActualEsq < vidaTotalEsq && vidaActualEsq >= (vidaTotalEsq / 8) * 6) {
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

	public void cambiarBarraVidaEnmg() {
		if (vidaActualEnmg < ((vidaTotalEnmg / 8) * 7) && vidaActualEnmg >= (vidaTotalEnmg / 8) * 6) {
			barraVidaEnmg.setIcon(new ImageIcon(Combate.class.getResource("barraVida1.png")));
		} else if (vidaActualEnmg < ((vidaTotalEnmg / 8) * 6) && vidaActualEnmg >= (vidaTotalEnmg / 8) * 5) {
			barraVidaEnmg.setIcon(new ImageIcon(Combate.class.getResource("barraVida2.png")));
		} else if (vidaActualEnmg < ((vidaTotalEnmg / 8) * 5) && vidaActualEnmg >= (vidaTotalEnmg / 8) * 4) {
			barraVidaEnmg.setIcon(new ImageIcon(Combate.class.getResource("barraVida3.png")));
		} else if (vidaActualEnmg < ((vidaTotalEnmg / 8) * 4) && vidaActualEnmg >= (vidaTotalEnmg / 8) * 3) {
			barraVidaEnmg.setIcon(new ImageIcon(Combate.class.getResource("barraVida4.png")));
		} else if (vidaActualEnmg < ((vidaTotalEnmg / 8) * 3) && vidaActualEnmg >= (vidaTotalEnmg / 8) * 2) {
			barraVidaEnmg.setIcon(new ImageIcon(Combate.class.getResource("barraVida5.png")));
		} else if (vidaActualEnmg < ((vidaTotalEnmg / 8) * 2) && vidaActualEnmg >= (vidaTotalEnmg / 8) * 1) {
			barraVidaEnmg.setIcon(new ImageIcon(Combate.class.getResource("barraVida6.png")));
		} else if (vidaActualEnmg < ((vidaTotalEnmg / 8) * 1) && vidaActualEnmg > 0) {
			barraVidaEnmg.setIcon(new ImageIcon(Combate.class.getResource("barraVida7.png")));
		} else if (vidaActualEnmg == 0) {
			barraVidaEnmg.setIcon(new ImageIcon(Combate.class.getResource("barraVida8.png")));
		}
	}

	public int dañarEnmg() {

		int randomNum = ThreadLocalRandom.current().nextInt(dañoEsq, (dañoEsq + 8));
		return randomNum;

	}

	public int dañarEsq() {

		int randomNum = ThreadLocalRandom.current().nextInt(dañoEnmg, (dañoEnmg + 5));
		return randomNum;

	}

	public void ajustarVidas() {

		vidaActualEsq = 40;
		vidaActualEsqDisplayed = (int) vidaActualEsq;
		vidaTotalEsq = 40;
		vidaTotalEsqDisplayed = (int) vidaTotalEsq;
		vidaActualEnmg = 15;
		vidaActualEnmgDisplayed = (int) vidaActualEnmg;
		vidaTotalEnmg = 15;
		vidaTotalEnmgDisplayed = (int) vidaTotalEnmg;
		dañoEsq = 3;
		dañoEnmg = 3;

	}

	public void comprobarExpNecesaria() {

		if (nivelActual == 1 && expNeed > 0) {
			expNeed = expNeed - expActual;
			comprobarSubidaNivel();
			expNeeded.setText("Exp necesaria: " + expNeed);
			System.out.println();
		} else if (nivelActual == 2 && expNeed > 0) {
			expNeed = expNeed - expActual;
			comprobarSubidaNivel();
			expNeeded.setText("Exp necesaria: " + expNeed);
		} else if (nivelActual == 3 && expNeed > 0) {
			expNeed = expNeed - expActual;
			comprobarSubidaNivel();
			expNeeded.setText("Exp necesaria: " + expNeed);
		} else if (nivelActual == 4 && expNeed > 0) {
			expNeed = expNeed - expActual;
			comprobarSubidaNivel();
			expNeeded.setText("Exp necesaria: " + expNeed);
		}else if (nivelActual == 5 && expNeed > 0) {
			expNeed = expNeed - expActual;
			comprobarSubidaNivel();
			expNeeded.setText("Exp necesaria: " + expNeed);
		}else if (nivelActual == 6 && expNeed > 0) {
			expNeed = expNeed - expActual;
			comprobarSubidaNivel();
			expNeeded.setText("Exp necesaria: " + expNeed);
		}else if (nivelActual == 7 && expNeed > 0) {
			expNeed = expNeed - expActual;
			comprobarSubidaNivel();
			expNeeded.setText("Exp necesaria: " + expNeed);
		}else if (nivelActual == 8 && expNeed > 0) {
			expNeed = expNeed - expActual;
			comprobarSubidaNivel();
			expNeeded.setText("Exp necesaria: " + expNeed);
		}

	}
	
	public void comprobarSubidaNivel() {
		if(expNeed <= 0) {
			nivelActual++;
			nivelDisplayed.setText("Nivel "+ nivelActual);
			expActual = 0;
			if(nivelActual == 2) {
				expNeed = 270;
			}else if(nivelActual == 3) {
				expNeed = 390;
			}else if(nivelActual == 4) {
				expNeed = 510;
			}else if(nivelActual == 5) {
				expNeed = 630;
			}else if(nivelActual == 6) {
				expNeed = 750;
			}else if(nivelActual == 7) {
				expNeed = 870;
			}else if(nivelActual == 8) {
				expNeed = 990;
			}else if(nivelActual == 9) {
				expNeed = 1200;
			}
			vidaTotalEsq += 5;
			vidaActualEsq = vidaTotalEsq;
			vidaActualEsqDisplayed = (int) vidaActualEsq;
			vidaTotalEsqDisplayed = (int) vidaTotalEsq;
			
			vidaTotalEnmg += 3;
			vidaActualEnmg = vidaTotalEnmg;
			vidaActualEnmgDisplayed = (int) vidaActualEnmg;
			vidaTotalEnmgDisplayed = (int) vidaTotalEnmg;
			
			vidaActualEsqTxt.setText(vidaActualEsqDisplayed + "/" + vidaTotalEsqDisplayed);
			vidaActualEnmgTxt.setText(vidaActualEnmgDisplayed + "/" + vidaTotalEnmgDisplayed);
			barraVidaEsq.setIcon(new ImageIcon(Combate.class.getResource("barraVida.png")));
			barraVidaEnmg.setIcon(new ImageIcon(Combate.class.getResource("barraVida.png")));
			dañoEsq += 2;
			dañoEnmg += 2;
			
		}
	}
	
	public void cambiarEnemigo() {
		if(enemigo.getIcon() == slime) {
			enemigo.setIcon(owlboy);
		}else if(enemigo.getIcon() == owlboy) {
			enemigo.setIcon(mago);
		}else if(enemigo.getIcon() == mago) {
			enemigo.setIcon(encapuchado);
		}else if(enemigo.getIcon() == encapuchado) {
			enemigo.setIcon(slime);
		}
	}

}
