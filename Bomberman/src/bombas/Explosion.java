package bombas;

import celda.Celda;
import entidades.ExplosionGrafica;
/**
 * Clase explosi�n.
 * @author Germ�n Herrou, Esteban Federico Canela
 *
 */
public class Explosion {
	
	protected ExplosionGrafica graf;
	/**
	 * Constructor de Explosi�n
	 * @param tipo Tipo de explosi�n (Horizontal, vertical o centro).
	 * @param c Celda que recibe la explosi�n.
	 */
	public Explosion(int tipo, Celda c){
		graf=new ExplosionGrafica(c.getPosX(),c.getPosY(),tipo);
	}
	/**
	 * Retorna el gr�fico de la explosi�n.
	 * @return Gr�fico de la explosi�n.
	 */
	public ExplosionGrafica obtenerGrafico() {
		return graf;
	}
	

}
