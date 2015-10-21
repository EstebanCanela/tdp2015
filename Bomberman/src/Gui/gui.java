package Gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import logica.Nivel;

public class gui extends JFrame {

	private static final long serialVersionUID = 1L;
	protected Nivel n;
	
	public gui () {
		
		super ("Proyecto X - TECNOLOGIA DE PROGRAMACION - 2015");
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				mover(arg0);
			}
		});
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(998,445);
		JLayeredPane contentPane = new JLayeredPane();
		setContentPane(contentPane);
		getContentPane().setLayout(null);
		//this.setUndecorated(true); SE USA PARA PONER PANTALLA COMPLETA
		//this.setExtendedState(MAXIMIZED_BOTH); SE USA PARA PONER PANTALLA COMPLETA
		n = new Nivel (this);
		
	}
	
	protected void mover(KeyEvent key) {
		switch (key.getKeyCode()) {
			case KeyEvent.VK_RIGHT : // Derecha
				n.obtenerBomberman().moverDerecha();
				break;
			case KeyEvent.VK_LEFT : // Izquierda
				n.obtenerBomberman().moverIzquierda();
				break;
			case KeyEvent.VK_UP : // Arriba
				n.obtenerBomberman().moverArriba();
				break;
			case KeyEvent.VK_DOWN : // Abajo
				n.obtenerBomberman().moverAbajo();
				break;
			case KeyEvent.VK_SPACE : // Poner bomba
				n.obtenerBomberman().colocarBomba();
				break;
			default : // Cualquier otra tecla
				break;
		}
		this.repaint();
	}
	
	
}