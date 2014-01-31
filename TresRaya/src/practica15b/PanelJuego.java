package practica15b;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Ainhoa Suárez Sánchez
 * 
 *         Crea el panel con los botones y añade la funcionalidad básica de
 *         juego
 */
public class PanelJuego extends JPanel implements ActionListener {

	// Constantes
	private static final long serialVersionUID = 1L;
	public static final String JUGADOR1 = "X";
	public static final String JUGADOR2 = "O";
	public static final ImageIcon JUGADOR1_ICON = new ImageIcon("equis.jpg");
	public static final ImageIcon JUGADOR2_ICON = new ImageIcon("circulo.jpg");

	// Declaro mis atributos
	private BotonJuego[][] panelJuego; // matriz que rellenaremos con las teclas
										// de tipo BotonJuego
	private JLabel mensaje;
	private int movimientos; // acumula mov. y cuando llega a 9 sabe que el
								// juego ha terminado
	private boolean turno; // true=turno1; false=turno2
	private boolean bloqueado;

	private ArrayList<BotonJuego> posibilidades;
	private boolean tipoJuego;

	/**
	 * Crea el panel de juego e inicializa los atributos
	 * 
	 * @param mensaje
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

	// Métodos.
	/**
	 * Establece true para Humano vs Ordenador o false para Humano vs Humano
	 * 
	 * @param tipoJuego
	 */
	public void setTipoJuego(boolean tipoJuego) {
		this.tipoJuego = tipoJuego;
	}

	/**
	 * Establece el símbolo según si el turno esta en true(X) o false(O)
	 * 
	 * @param botonJuego
	 */
	private void ponerSimbolo(BotonJuego botonJuego) {
		if (turno == true) {
			botonJuego.setText(JUGADOR1);
			if (tipoJuego == false)
				mensaje.setText("Turno de JUGADOR 1");
			// botonJuego.setIcon(JUGADOR1_ICON); --> Debería establecer el
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

	public void reiniciar() {
		bloqueado = false;
		movimientos = 0;
		bloquear();
	}

	public void desbloquear() {
		for (int x = 0; x < panelJuego.length; x++) {
			for (int y = 0; y < panelJuego[x].length; y++) {
				panelJuego[x][y].setText("");
				panelJuego[x][y].setEnabled(true);
			}
		}
		bloqueado = false;
	}

	private void bloquear() {
		for (int x = 0; x < panelJuego.length; x++) {
			for (int y = 0; y < panelJuego[x].length; y++) {
				panelJuego[x][y].setText("");
				panelJuego[x][y].setEnabled(false);
			}
		}
		bloqueado = true;
	}

	private BotonJuego calcularMovimiento() {
		if(posibilidades.isEmpty())
			System.out.println("Esta vacio");
		int mov = (int) Math.random() * (posibilidades.size());
		return (BotonJuego) posibilidades.get(mov);
	}

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

	public void actionPerformed(ActionEvent ev) {
		BotonJuego botonJuego = (BotonJuego) ev.getSource();
		if (botonJuego.getText().equals("")) {
			ponerSimbolo(botonJuego);
			
			movimientos++;
			if (comprobarGanador(botonJuego)) {
				mensaje.setText("GANADOR: " + botonJuego.getText());
				bloquear();
				reiniciar();
			} else if (movimientos == 9) {
				mensaje.setText("EMPATE");
				bloquear();
				reiniciar();
			}
			if (tipoJuego && !bloqueado) {
				movimientos++;
				posibilidades.remove(botonJuego);
				botonJuego = calcularMovimiento();
				posibilidades.remove(botonJuego);
				ponerSimbolo(botonJuego);
				if (comprobarGanador(botonJuego)) {
					mensaje.setText("GANADOR: " + botonJuego.getText());
					bloquear();
					reiniciar();
				} else if (movimientos == 9) {
					mensaje.setText("Final de juego. Tablas.");
					bloquear();
					reiniciar();
				}
			}
		}
	}

}
