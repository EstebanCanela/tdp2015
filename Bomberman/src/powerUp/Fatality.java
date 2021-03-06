package powerUp;
import java.applet.AudioClip;

import celda.Celda;
import entidades.FatalityGrafica;
import logica.Nivel;
import personaje.Bomberman;

/**
 * Clase Fatality
 * @author Esteban Federico Canela y German Herrou
 *
 */
public class Fatality extends PowerUp {
	
	/**
	 * Constructor de Fatality
	 * @param n Nivel al que pertenece el PowerUp
	 */
	
	public Fatality (Nivel n) {
		super(n);
	}
	
	
	public void activar(Bomberman b){
		b.duplicarAlcance();
		getNivel().aumentarPuntuacion(50);
		getNivel().procesarGrafico().removerPowerUp(this);
		b.getNivel().procesarGrafico().aumentarFatality();
		AudioClip poweru;
		poweru = java.applet.Applet.newAudioClip(getClass().getResource("/Sounds/Bonus.mid"));
		poweru.play();
	}
	
	public void ubicarEnCelda(Celda c){
		miCelda=c;
		miGrafico= new FatalityGrafica(c.getPosX(),c.getPosY());
	}

}
