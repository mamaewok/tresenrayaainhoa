package tresEnRaya;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Crea el panel con los botones y a�ade la funcionalidad b�sica dejuego
 * @author MAMAEWOK
 *
 */
public class PanelJuego extends JPanel implements ActionListener {

	// Constantes
	private static final long serialVersionUID = 1L;
	public static final String JUGADOR1 = "X";
	public static final String JUGADOR2 = "O";
	public static final ImageIcon JUGADOR1_ICON = new ImageIcon("equis.jpg");
	public static final ImageIcon JUGADOR2_ICON = new ImageIcon("circulo.jpg");

	// Declaro mis atributos
	
	// matriz que rellenaremos con las teclas de tipo BotonJuego
	private BotonJuego[][] panelJuego;
	//mensaje que cambiaremos seg�n gane un jugador u otro, o se produzca un empate
	private JLabel mensaje; 
	// acumula mov. y cuando llega a 9 sabe que el juego ha terminado
	private int movimientos; 
	// true=turno1; false=turno2
	private boolean turno; 
	//Sirve para decir si esta el panel de juego bloqueado
	private boolean bloqueado; 
	//guarda todos los botones de mi tablero y los borra seg�n se usan
	private ArrayList<BotonJuego> posibilidades; 
	//dice el tipo de juego en el que nos encontramos
	private boolean tipoJuego; 

	/**
	 * Crea el panel de juego e inicializa los atributos
	 * 
	 * @param mensaje emnsaje que ir� cambiando seg�n las circunstancias
	 */
	public PanelJuego(JLabel mensaje) {
		panelJuego = new BotonJuego[3][3];
		posibilidades = new ArrayList<BotonJuego>();
		movimientos = 0;
		turno = true;
		bloqueado = false;
		this.mensaje = mensaje;

		this.setLayout(new GridLayout(3, 3));

		for (int x = 0; x < panelJuego.length; x++) {
			for (int y = 0; y < panelJuego[x].length; y++) {
				panelJuego[x][y] = new BotonJuego(x, y);
				panelJuego[x][y].setEnabled(false);
				panelJuego[x][y].addActionListener(this);
				this.add(panelJuego[x][y]);
				posibilidades.add(panelJuego[x][y]);
			}
		}
	}

	// M�todos.
	/**
	 * Establece true para Humano vs Ordenador o false para Humano vs Humano
	 * 
	 * @param tipoJuego
	 */
	public void setTipoJuego(boolean tipoJuego) {
		this.tipoJuego = tipoJuego;
	}

	/**
	 * Establece el s�mbolo seg�n si el turno esta en true(X) o false(O)
	 * 
	 * @param botonJuego
	 */
	private void ponerSimbolo(BotonJuego botonJuego) {
		if (turno == true) {
			botonJuego.setText(JUGADOR1);
			if (tipoJuego == false)
				mensaje.setText("Turno de JUGADOR 1");
			// botonJuego.setIcon(JUGADOR1_ICON); --> Deber�a establecer el
			// icono como imagen pero no lo hace :(
			turno = false;
		} else {
			botonJuego.setText(JUGADOR2);
			if (tipoJuego == false)
				mensaje.setText("Turno de JUGADOR 2");
			// botonJuego.setIcon(JUGADOR2_ICON);
			turno = true;
		}
	}

	/**
	 * Reinicia el tablero vaciando las posibilidades que quedaran, rellenandolas de nuevo con los botones sin tocar, reiniciando los movimientos
	 * y bloqueando las teclas hasta que se acepte un nuevo tipo de juego
	 */
	public void reiniciar() {
		posibilidades.removeAll(posibilidades);
		//El error se encontraba en que vaciaba las posibilidades pero no volvia a llenarlas con los posibles botones. As� ya funciona!! :D:D
		for (int x = 0; x < panelJuego.length; x++) {  
			for (int y = 0; y < panelJuego[x].length; y++) {
				posibilidades.add(panelJuego[x][y]);
			}
		}
		movimientos = 0;
		bloquear();
	}

	/**
	 * Desbloquea el tablero de juego 
	 */
	public void desbloquear() {
		for (int x = 0; x < panelJuego.length; x++) {
			for (int y = 0; y < panelJuego[x].length; y++) {
				panelJuego[x][y].setText("");
				panelJuego[x][y].setEnabled(true);
			}
		}
		bloqueado = false;
	}

	/**
	 * Bloquea el tablero de juego 
	 */
	private void bloquear() {
		for (int x = 0; x < panelJuego.length; x++) {
			for (int y = 0; y < panelJuego[x].length; y++) {
				panelJuego[x][y].setText("");
				panelJuego[x][y].setEnabled(false);
			}
		}
		bloqueado = true;
	}

	/**
	 * Calcula el movimiento que realizar� el ordenador de manera aleatoria
	 * @return un boton elegido de manera aleatoria entre las posibilidades que queden todavia
	 */
	private BotonJuego calcularMovimiento() {
		int mov = (int) Math.random() * (posibilidades.size());
		return (BotonJuego) posibilidades.get(mov);
	}

	/**
	 * Comprueba la matriz si gana o no el boton introducido como par�metro
	 * @param botonJuego
	 * @return true si el boton gana o false si pierde
	 */
	private boolean comprobarGanador(BotonJuego botonJuego) {
		int y = botonJuego.getCoordenadaY();
		int x = botonJuego.getCoordenadaX();
		String simbolo = botonJuego.getText();

		if (y == 0) {
			if (x == 0) {
				// Horizontal
				if (panelJuego[y][x + 1].getText().equals(simbolo)
						&& panelJuego[y][x + 2].getText().equals(simbolo))
					return true;
				// Vertical
				if (panelJuego[y + 1][x].getText().equals(simbolo)
						&& panelJuego[y + 2][x].getText().equals(simbolo))
					return true;
				// Diagonal
				if (panelJuego[y + 1][x + 1].getText().equals(simbolo)
						&& panelJuego[y + 2][x + 2].getText().equals(simbolo))
					return true;
			} else if (x == 2) {
				// Horizontal
				if (panelJuego[y][x - 1].getText().equals(simbolo)
						&& panelJuego[y][x - 2].getText().equals(simbolo))
					return true;
				// Vertical
				if (panelJuego[y + 1][x].getText().equals(simbolo)
						&& panelJuego[y + 2][x].getText().equals(simbolo))
					return true;
				// Diagonal
				if (panelJuego[y + 1][x - 1].getText().equals(simbolo)
						&& panelJuego[y + 2][x - 2].getText().equals(simbolo))
					return true;
			} else {
				// Horizontal
				if (panelJuego[y][x - 1].getText().equals(simbolo)
						&& panelJuego[y][x + 1].getText().equals(simbolo))
					return true;
				// Vertical
				if (panelJuego[y + 1][x].getText().equals(simbolo)
						&& panelJuego[y + 2][x].getText().equals(simbolo))
					return true;
			}
		} else if (y == 2) {
			if (x == 0) {
				// Horizontal
				if (panelJuego[y][x + 1].getText().equals(simbolo)
						&& panelJuego[y][x + 2].getText().equals(simbolo))
					return true;
				// Vertical
				if (panelJuego[y - 1][x].getText().equals(simbolo)
						&& panelJuego[y - 2][x].getText().equals(simbolo))
					return true;
				// Diagonal
				if (panelJuego[y - 1][x + 1].getText().equals(simbolo)
						&& panelJuego[y - 2][x + 2].getText().equals(simbolo))
					return true;
			} else if (x == 2) {
				// Horizontal
				if (panelJuego[y][x - 1].getText().equals(simbolo)
						&& panelJuego[y][x - 2].getText().equals(simbolo))
					return true;
				// Vertical
				if (panelJuego[y - 1][x].getText().equals(simbolo)
						&& panelJuego[y - 2][x].getText().equals(simbolo))
					return true;
				// Diagonal
				if (panelJuego[y - 1][x - 1].getText().equals(simbolo)
						&& panelJuego[y - 2][x - 2].getText().equals(simbolo))
					return true;
			} else {
				// Horizontal
				if (panelJuego[y][x - 1].getText().equals(simbolo)
						&& panelJuego[y][x + 1].getText().equals(simbolo))
					return true;
				// Vertical
				if (panelJuego[y - 1][x].getText().equals(simbolo)
						&& panelJuego[y - 2][x].getText().equals(simbolo))
					return true;
			}
		} else {
			if (x == 0) {
				// Horizontal
				if (panelJuego[y][x + 1].getText().equals(simbolo)
						&& panelJuego[y][x + 2].getText().equals(simbolo))
					return true;
				// Vertical
				if (panelJuego[y - 1][x].getText().equals(simbolo)
						&& panelJuego[y + 1][x].getText().equals(simbolo))
					return true;
			} else if (x == 2) {
				// Horizontal
				if (panelJuego[y][x - 1].getText().equals(simbolo)
						&& panelJuego[y][x - 2].getText().equals(simbolo))
					return true;
				// Vertical
				if (panelJuego[y - 1][x].getText().equals(simbolo)
						&& panelJuego[y + 1][x].getText().equals(simbolo))
					return true;
			} else {
				// Horizontal
				if (panelJuego[y][x - 1].getText().equals(simbolo)
						&& panelJuego[y][x + 1].getText().equals(simbolo))
					return true;
				// Vertical
				if (panelJuego[y - 1][x].getText().equals(simbolo)
						&& panelJuego[y + 1][x].getText().equals(simbolo))
					return true;
				// Diagonal
				if (panelJuego[y - 1][x - 1].getText().equals(simbolo)
						&& panelJuego[y + 1][x + 1].getText().equals(simbolo))
					return true;
				if (panelJuego[y - 1][x + 1].getText().equals(simbolo)
						&& panelJuego[y + 1][x - 1].getText().equals(simbolo))
					return true;
			}
		}
		return false;
	}

	/**
	 * Gestiona el evento de pulsado de los botones del tablero de juego
	 */
	public void actionPerformed(ActionEvent ev) {
		BotonJuego botonJuego = (BotonJuego) ev.getSource();
		if (botonJuego.getText().equals("")) {  
			ponerSimbolo(botonJuego);					//Si el boton esta vacio pon el simbolo correspondiente y suma el movimiento
			movimientos++;
			if (comprobarGanador(botonJuego) == true) {
				mensaje.setText("GANADOR: " + botonJuego.getText());		//Si ese boton gana dilo y reinicia
				reiniciar();
			} else if (movimientos == 9) {									//Si los mov = 9 hay empate y reinicia
				mensaje.setText("EMPATE");
				reiniciar();
			}
			if (tipoJuego == true && !bloqueado) {							//Si se juega HvsO quito la posibilidad del boton pulsado por el H y lo convierto
				posibilidades.remove(botonJuego);							//en boton pulsado por el Ordenador y borro la posilidad el bot�n del ordenador, pone el simbolo
				botonJuego = calcularMovimiento();							//y suma movimiento
				posibilidades.remove(botonJuego);
				ponerSimbolo(botonJuego);
				movimientos++;
				if (comprobarGanador(botonJuego) == true) {								//Comprueba ganador o empate HvsO
					mensaje.setText("GANADOR: " + botonJuego.getText());
					reiniciar();
				} else if (movimientos == 9) {
					mensaje.setText("Final de juego. Tablas.");
					reiniciar();
				}
			}
		}
	}

}
