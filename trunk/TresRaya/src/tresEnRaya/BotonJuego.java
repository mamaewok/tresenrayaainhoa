package tresEnRaya;

import javax.swing.JButton;
/**
 * 
 * @author Ainhoa Suárez Sánchez
 *
 */
public class BotonJuego extends JButton {

	private static final long serialVersionUID = 1L;
	private int y; // coordenadas del boton en vwrtical
	private int x; // coordenadas del boton en horizontal
	
	
	public BotonJuego(int y, int x) {
		super();
		this.y = y;
		this.x = x;
	}

	// returns
	public int getCoordenadaY() {
		return y;
	}

	public int getCoordenadaX() {
		return x;
	}

}
