package logica;

import java.util.Random;

import personaje.Personaje;
import powerUp.PowerUp;
import threads.ThreadBomba;
import threads.ThreadExplosionGrafica;
import bombas.Bomba;
import bombas.Explosion;
import celda.Celda;
import entidades.CeldaIndestructibleGrafica;
import Gui.gui;

public class ProcesadorGrafico {
	
	protected gui miGui;
	protected ThreadBomba tb;
	/**
	 * Constructor de ProcesadorGrafico
	 * @param g gui en la que se mostraran los cambios.
	 */
	public ProcesadorGrafico(gui g){
		miGui=g;
		// Crea un thread que explota las bombas colocadas
		tb = new ThreadBomba();
		tb.start();
	}
	/**
	 * A�ade una celda gr�fica.
	 * @param c Celda a agregar.
	 */
	public void agregarCelda(Celda c){
		miGui.miMapa().add(c.getCeldaGrafica().obtenerGrafico(),new Integer(1));
		miGui.miMapa().repaint();
	}
	/**
	 * A�ade un personaje gr�fico.
	 * @param p personaje a agregar.
	 */
	public void agregarPersonaje(Personaje p){
		miGui.miMapa().add(p.obtenerGrafico().obtenergraf(), new Integer(50));
		miGui.miMapa().repaint();
	}
	/**
	 * Aumenta la puntuaci�n del juego.
	 * @param p Cantidad de puntos a aumentar.
	 */
	public void aumentarPuntos(int p){
		miGui.aumentarPuntos(p);
	}
	/**
	 * Finaliza el juego.
	 */
	public void gameOver(){
		miGui.gameOver();
	}
	
	/**
	 * Agrega una bomba al nivel.
	 * @param b Bomba a agregar.
	 */
	public void agregarBomba(Bomba b) {
		tb.colocarBomba(b);
		miGui.miMapa().add(b.obtenerGrafico().obtenerGrafico(), new Integer(8));
		miGui.miMapa().repaint();

	}
	/**
	 * Quita una bomba del nivel (Debido a que explot�).
	 * @param b Bomba a remover.
	 */
	public void removerBomba(Bomba b) {
		miGui.miMapa().remove(b.obtenerGrafico().obtenerGrafico());
		miGui.miMapa().repaint();
	}
	/**
	 * Inicializa una explosi�n gr�fica.
	 * @param c Celda donde ocurre la explosi�n.
	 * @param tipo tipo de explosi�n.
	 */
	public void mostrarExplosion(Celda c,int tipo){
		ThreadExplosionGrafica th=new ThreadExplosionGrafica(tipo,c);
		th.start();
		miGui.miMapa().repaint();
	}
	/**
	 * Agrega el gr�fico de una explosi�n.
	 * @param e Explosi�n a agregar.
	 */
	public void agregarExplosion(Explosion e){
		miGui.miMapa().add(e.obtenerGrafico().obtenerGrafico(),new Integer(9));
		miGui.miMapa().repaint();
	}
	/**
	 * Quita el gr�fico de una explosi�n.
	 * @param e Explosi�n a quitar.
	 */
	public void quitarExplosion(Explosion e){
		miGui.miMapa().remove(e.obtenerGrafico().obtenerGrafico());
		miGui.miMapa().repaint();
	}
	
	
	/**
	 * Quita un powerUp del nivel (Dado que se activ�).
	 * @param p PowerUp a remover.
	 */
	public void removerPowerUp(PowerUp p) {
		miGui.miMapa().remove(p.obtenerGrafico().obtenerGrafico());
		miGui.miMapa().repaint();
	}
	/**
	 * Quita una pared destruible de una celda. (Dado que explot�).
	 * @param c celda cuya pared fue destruida.
	 */

	public void quitarPared(Celda c) {
		miGui.miMapa().remove(c.getCeldaGrafica().obtenerGrafico());
		c.setEstructura(null);
		miGui.miMapa().add(c.getCeldaGrafica().obtenerGrafico(), new Integer(1));
		if (c.getPowerUp() != null) {
			miGui.miMapa().add(c.getPowerUp().obtenerGrafico().obtenerGrafico(),
					new Integer(5));
		}
		miGui.miMapa().repaint();
	}
	/**
	 * Quita un personaje del Nivel. (Dado que muri�).
	 * @param p personaje a remover.
	 */
	public void quitarPersonaje(Personaje p) {
		miGui.miMapa().remove(p.obtenerGrafico().obtenergraf());
		miGui.miMapa().repaint();
	}
	
	/**
	 * Muestra el mensaje de victoria.
	 */
	public void mostrarVictoria() {
		miGui.mostrarVictoria();
		
	}
	/**
	 * Aumenta la cantidad de SpeedUps recogidos en el contador.
	 */
	public void aumentarSpeedUp() {
		miGui.aumentarSpeedUp();
	}
	/**
	 * Aumenta la cantidad de Fatalitys recogidos en el contador.
	 */
	public void aumentarFatality() {
		miGui.aumentarFatality();
	}
	/**
	 * Aumenta la cantidad de Bombalitys recogidos en el contador.
	 */
	public void aumentarBombality() {
		miGui.aumentarBombality();
	}
	/**
	 * Aumenta la cantidad de Masacralitys recogidos en el contador.
	 */
	public void aumentarMasacrality() {
		miGui.aumentarMasacrality();
	}
	
	/**
	 * Selecciona al azar un tipo de pared indestructible
	 */
	
	public void seleccionarTipoGrafico(){
		CeldaIndestructibleGrafica.seleccionarTipoGrafico();	
	}
	
	/**
	 * Desactiva el icono del Massacrality
	 */
	public void desactivarMassa() {
		miGui.desactivarMassa();
	}

}
