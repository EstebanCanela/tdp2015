package logica;

import Gui.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import bombas.Bomba;
import personaje.*;
import powerUp.*;
import threads.ThreadBomba;
import threads.ThreadEnemigo;
import celda.*;

/**
 * Clase Nivel
 * 
 * @author Esteban Federico Canela, Germ�n Herrou
 *
 */
public class Nivel {
	// Atributos
	private final int ancho = 31;
	private final int largo = 13;
	private final float porcentajeDestructibles = 0.5f;
	protected Celda[][] matrizCeldas;
	protected Bomberman miBomberman;
	protected List<Enemigo> enemigos;
	protected Random rnd;
	protected int puntuacion;
	protected int bloquesParaGanar;
	protected gui miGui;

	protected ThreadBomba tb;

	/**
	 * Constructor de Clase Nivel
	 * 
	 * @param guigraf
	 *            Interfaz gr�fica.
	 */
	public Nivel(gui guigraf) {

		// Crea un thread que explota las bombas colocadas
		tb = new ThreadBomba();
		tb.start();

		miGui = guigraf;
		puntuacion = 0;
		rnd = new Random();
		enemigos = new ArrayList<Enemigo>();
		matrizCeldas = new Celda[ancho][largo];

		// Creo una lista con los powerUp
		List<PowerUp> listap = new ArrayList<PowerUp>();

		for (int i = 0; i < 4; i++)
			listap.add(new SpeedUp(this));
		/*
		 * for (int i = 0; i < 3; i++) listap.add(new Masacrality(this));
		 */
		for (int i = 0; i < 3; i++)
			listap.add(new Bombality(this));

		listap.add(new Fatality(this));

		// Creo una matriz vac�a.
		for (int i = 0; i < ancho - 1; i++)
			for (int j = 0; j < largo - 1; j++)
				matrizCeldas[i][j] = null;

		int celdasVacias = ancho * largo;

		// Inicializo los bordes del mapa con paredes indestructibles.
		for (int i = 0; i < ancho; i++) {
			matrizCeldas[i][0] = crearPI(i, 0);
			matrizCeldas[i][largo - 1] = crearPI(i, largo - 1);
			celdasVacias -= 2;
			miGui.add(matrizCeldas[i][0].getCeldaGrafica().obtenerGrafico(),
					new Integer(1));
			miGui.add(matrizCeldas[i][largo - 1].getCeldaGrafica()
					.obtenerGrafico(), new Integer(1));
		}
		for (int j = 0; j < largo; j++) {
			matrizCeldas[0][j] = crearPI(0, j);
			matrizCeldas[ancho - 1][j] = crearPI(ancho - 1, j);
			miGui.add(matrizCeldas[0][j].getCeldaGrafica().obtenerGrafico(),
					new Integer(1));
			miGui.add(matrizCeldas[ancho - 1][j].getCeldaGrafica()
					.obtenerGrafico(), new Integer(1));
			celdasVacias -= 2;
		}

		// Inicializo con celdas sin paredes aquellas correspondientes a la
		// ubicaci�n inicial de Bomberman y Sirius.
		matrizCeldas[1][1] = crearPiso(1, 1);
		matrizCeldas[1][2] = crearPiso(1, 2);
		matrizCeldas[2][1] = crearPiso(2, 1);

		miGui.add(matrizCeldas[1][1].getCeldaGrafica().obtenerGrafico(),
				new Integer(1));
		miGui.add(matrizCeldas[1][2].getCeldaGrafica().obtenerGrafico(),
				new Integer(1));
		miGui.add(matrizCeldas[2][1].getCeldaGrafica().obtenerGrafico(),
				new Integer(1));

		matrizCeldas[ancho - 2][largo - 2] = crearPiso(ancho - 2, largo - 2);
		matrizCeldas[ancho - 3][largo - 2] = crearPiso(ancho - 3, largo - 2);
		matrizCeldas[ancho - 2][largo - 3] = crearPiso(ancho - 2, largo - 3);

		miGui.add(matrizCeldas[ancho - 2][largo - 2].getCeldaGrafica()
				.obtenerGrafico(), new Integer(1));
		miGui.add(matrizCeldas[ancho - 3][largo - 2].getCeldaGrafica()
				.obtenerGrafico(), new Integer(1));
		miGui.add(matrizCeldas[ancho - 2][largo - 3].getCeldaGrafica()
				.obtenerGrafico(), new Integer(1));

		celdasVacias -= 6;

		// Inicializo las paredes indestructibles dentro del nivel.
		for (int i = 2; i < ancho - 1; i = i + 2)
			for (int j = 2; j < largo - 1; j = j + 2) {
				matrizCeldas[i][j] = crearPI(i, j);
				miGui.add(
						matrizCeldas[i][j].getCeldaGrafica().obtenerGrafico(),
						new Integer(1));
				celdasVacias--;
			}

		// Estimo cuantas paredes destructibles debo colocar
		int destruiblesRestantes = (int) (celdasVacias * porcentajeDestructibles);

		bloquesParaGanar = destruiblesRestantes; // El jugador gana cuando
													// bloquesParaGanar vale 0;

		// Coloco las paredes destructibles en lugares aleatorios.
		while (destruiblesRestantes > 0) {
			int rx = rnd.nextInt(ancho - 1);
			int ry = rnd.nextInt(largo - 1);
			if (matrizCeldas[rx][ry] == null) {
				if (listap.isEmpty())
					matrizCeldas[rx][ry] = crearPD(rx, ry, null);
				else {
					PowerUp p = listap.remove(0);
					matrizCeldas[rx][ry] = crearPD(rx, ry, p);
					p.ubicarEnCelda(matrizCeldas[rx][ry]);
				}
				miGui.add(matrizCeldas[rx][ry].getCeldaGrafica()
						.obtenerGrafico(), new Integer(1));
				destruiblesRestantes--;
				celdasVacias--;
			}
		}

		// Coloco celdas sin estructuras en las ubicaciones restantes.
		for (int i = 1; i < ancho - 1; i++)
			for (int j = 1; j < largo - 1; j++)
				if (matrizCeldas[i][j] == null) {
					matrizCeldas[i][j] = crearPiso(i, j);
					miGui.add(matrizCeldas[i][j].getCeldaGrafica()
							.obtenerGrafico(), new Integer(1));
					celdasVacias--;
				}
		// Finaliza creaci�n mapa.
		assert (celdasVacias == 0);

		// Creo e inserto el bomberman
		miBomberman = new Bomberman(matrizCeldas[1][1], this);
		matrizCeldas[1][1].colocar(miBomberman);
		miGui.add(miBomberman.obtenerGrafico().obtenergraf(), new Integer(50));

		// Creo e inserto el rugulos.

		Rugulos rg = new Rugulos(matrizCeldas[ancho - 2][largo - 2], this);
		ThreadEnemigo te = new ThreadEnemigo(rg);
		te.start();
		miGui.add(rg.obtenerGrafico().obtenergraf(), new Integer(50));

		/*
		 * Sirius sr = new Sirius(matrizCeldas[ancho - 2][largo - 2], this);
		 * matrizCeldas[ancho - 2][largo - 2].colocar(sr); enemigos.add(sr);
		 */

		// Falta colocar al resto de los enemigos.

	}

	/**
	 * Retorna el bomberman que maneja el jugador.
	 * 
	 * @return El personaje bomberman.
	 */
	public Bomberman obtenerBomberman() {
		return miBomberman;
	}

	/**
	 * Retorna una celda dada sus coordenadas.
	 * 
	 * @param x
	 *            Coordenada x de la celda.
	 * @param y
	 *            Coordenada y de la celda
	 * @return Celda con coordenadas (x,y)
	 */
	public Celda getCelda(int x, int y) {
		if (x >= 0 && x < ancho && y >= 0 && y > largo)
			return matrizCeldas[x][y];
		else
			return null;
	}

	/**
	 * Analiza si el jugador ya gano.
	 * 
	 * @return Verdadero si no quedan paredes por destruir, falso en caso
	 *         contrario.
	 */
	public boolean gano() {
		return (bloquesParaGanar == 0);
	}

	/**
	 * Aumenta la puntuacion del juego.
	 * 
	 * @param p
	 *            valor a sumar a la puntuacion.
	 */
	public void aumentarPuntuacion(int p) {
		puntuacion += p;
	}

	/**
	 * Dada una celda, retorna la adyacente en una direccion determinada.
	 * 
	 * @param c
	 *            La celda cuya adyacente se desea conocer.
	 * @param x
	 *            Direccion de la celda adyacente. A: arriba B: abajo D: derecha
	 *            I:izquierda
	 * @return La celda correspondiente a la direccion, null si la direccion no
	 *         es valida.
	 */
	public Celda getAdyacente(Celda c, char x) {
		switch (x) {
		case 'a':
			return matrizCeldas[c.getPosX()][c.getPosY() - 1];

		case 'i':
			return matrizCeldas[c.getPosX() - 1][c.getPosY()];

		case 'd':
			return matrizCeldas[c.getPosX() + 1][c.getPosY()];

		case 'b':
			return matrizCeldas[c.getPosX()][c.getPosY() + 1];

		}
		return null;

	}

	/**
	 * Quita un enemigo del juego.
	 * 
	 * @param e
	 *            Enemigo a quitar.
	 */
	public void destruirEnemigo(Enemigo e) {
		enemigos.remove(e);
	}

	/**
	 * Finaliza el juego.
	 */

	public void gameOver() {
		miGui.gameOver();
	}
	/**
	 * Mueve el personaje jugador a partir de una direccion definida por el ususario.
	 * @param direccion Direccion en la que el personaje se debe mover. 0: Derecha 1: Izquierda 2:Arriba 3:Abajo. No esta definido para otra direccion
	 */

	public void moverPersonaje(int direccion) {
		switch (direccion) {
		case 0: // Derecha
			miBomberman.moverDerecha();
			break;
		case 1: // Izquierda
			miBomberman.moverIzquierda();
			break;
		case 2: // Arriba
			miBomberman.moverArriba();
			break;
		case 3: // Abajo
			miBomberman.moverAbajo();
			break;
		}
	}
	/**
	 * Agrega una bomba al nivel.
	 * @param b Bomba a agregar.
	 */
	public void agregarBomba(Bomba b) {
		tb.colocarBomba(b);
		miGui.add(b.obtenerGrafico().obtenerGrafico(), new Integer(8));
		miGui.repaint();

	}
	/**
	 * Quita una bomba del nivel (Debido a que explot�).
	 * @param b Bomba a remover.
	 */
	public void removerBomba(Bomba b) {
		miGui.remove(b.obtenerGrafico().obtenerGrafico());
		miGui.repaint();
	}
	/**
	 * Quita un powerUp del nivel (Dado que se activ�).
	 * @param p PowerUp a remover.
	 */
	public void removerPowerUp(PowerUp p) {
		miGui.remove(p.obtenerGrafico().obtenerGrafico());
		miGui.repaint();
	}
	/**
	 * Quita una pared destruible de una celda. (Dado que explot�).
	 * @param c celda cuya pared fue destruida.
	 */

	public void quitarPared(Celda c) {
		miGui.remove(c.getCeldaGrafica().obtenerGrafico());
		c.setEstructura(null);
		miGui.add(c.getCeldaGrafica().obtenerGrafico(), new Integer(1));
		if (c.getPowerUp() != null) {
			miGui.add(c.getPowerUp().obtenerGrafico().obtenerGrafico(),
					new Integer(5));
		}
		miGui.repaint();
	}
	/**
	 * Quita un personaje del Nivel. (Dado que muri�).
	 * @param p personaje a remover.
	 */
	
	//Creadores de celdas.
	public void quitarPersonaje(Personaje p) {
		miGui.remove(p.obtenerGrafico().obtenergraf());
		miGui.repaint();
	}
	
	/**
	 * Crea un piso en la celda
	 * @param x Posicion X
	 * @param y Posicion Y 
	 * @return
	 */
	private Celda crearPiso(int x, int y) {
		return new Celda(x, y, this);

	}
	
	/**
	 * Crea una pared indestructible en la celda
	 * @param x Posicion X 
	 * @param y Posicion Y
	 * @return
	 */

	private Celda crearPI(int x, int y) {
		Celda c = new Celda(x, y, this);
		c.setEstructura(new ParedIndestructible(c));
		return c;

	}
	
	/**
	 * Crea una pared destructible en la celda
	 * @param x Posicion X 
	 * @param y Posicion Y
	 * @param p PowerUp que contendrar la pared indestructible
	 * @return
	 */

	
	private Celda crearPD(int x, int y, PowerUp p) {
		Celda c = new Celda(x, y, p, this);
		c.setEstructura(new ParedDestructible(c));
		return c;

	}

}
