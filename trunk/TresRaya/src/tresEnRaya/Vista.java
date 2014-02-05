package tresEnRaya;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
/**
 * 
 * @author MAMAEWOK
 *
 */
public class Vista extends JFrame implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	// Atributos
	private PanelJuego tablero;
	private JButton reiniciar, aceptar;
	private JLabel mensaje;
	private JRadioButton humanoVsOrdenador = null;
	private JRadioButton humanoVsHumano = null;

	/**
	 * Crea todo la vista de nuestro juego al instanciarse
	 */
	public Vista() {
		this.setTitle("Tres en raya");
		this.setBackground(Color.lightGray);
		this.setSize(400, 250);
		this.setResizable(false);
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //importante para que la ventana de confirmación al cerrar actue adecuadamente
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) { // Para tener que aceptar al cerrar
				cerrar();
			}
		});
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(4, 1));
		mensaje = new JLabel();
		mensaje.setHorizontalAlignment(SwingConstants.CENTER);
		
		tablero = new PanelJuego(mensaje); //se instancia el tablero en su lugar correspondiente con su mensaje cambiante
		this.getContentPane().add(tablero, BorderLayout.CENTER);
		panel1.add(mensaje);
		JPanel panel1Aux = new JPanel();
		aceptar = new JButton("Aceptar");
		aceptar.setVisible(false);
		aceptar.addActionListener(this);
		panel1Aux.add(aceptar);
		panel1.add(panel1Aux);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(2, 1));
		
		humanoVsOrdenador = new JRadioButton("Humano vs Ordenador", false);
		humanoVsOrdenador.addItemListener(this);
		humanoVsHumano = new JRadioButton("Humano vs Humano", false);
		humanoVsHumano.addItemListener(this);
		
		panel2.add(humanoVsOrdenador);
		panel2.add(humanoVsHumano);
		panel1.add(panel2);

		panel2 = new JPanel();
		reiniciar = new JButton("Reiniciar");
		reiniciar.addActionListener(this);
		panel2.add(reiniciar);
		panel1.add(panel2);

		this.getContentPane().add(panel1, BorderLayout.EAST);
		this.setVisible(true);
	}

	/**
	 * Ofrece la vista de una ventana de confirmación antes de cerrar el programa
	 */
	public void cerrar() {
		Object[] opciones = { "Aceptar", "Cancelar" };

		int eleccion = JOptionPane.showOptionDialog(null,
				"¿Seguro que quiere salir de este juego tan chulo?",
				"Mensaje de Confirmacion", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, opciones, "Aceptar");
		if (eleccion == JOptionPane.YES_OPTION) {
			System.exit(0);
		} 
	}
	
	/**
	 * Deshabilita los botones de tipo de juego
	 */
	public void bloquearTipoJuego(){
		humanoVsHumano.setVisible(false);
		humanoVsOrdenador.setVisible(false);
	}
	
	/**
	 * Habilita los botones de tipo de juego
	 */
	public void desbloquearTipoJuego(){
		humanoVsHumano.setVisible(true);
		humanoVsOrdenador.setVisible(true);
	}
	
	/**
	 * Deshabilita el botón Aceptar
	 */
	public void desbloquearAceptar() {
		aceptar.setVisible(true);
	}

	/**
	 * Habilita el botón Aceptar
	 */
	public void bloquearAceptar() {
		aceptar.setVisible(false);
	}

	/**
	 * Si el botón que produce el evento es Reiniciar realiza ciertos efectos sobre la vista y llama a reiniciar tablero
	 * y si es Aceptar desbloquea el tablero y oculta los botones de elección de juego 
	 */
	public void actionPerformed(ActionEvent ev) {
		JButton btn = (JButton) ev.getSource();
		
		if(btn.getText().equals("Reiniciar")){
			mensaje.setText("");
			tablero.reiniciar();
			desbloquearTipoJuego();
			humanoVsHumano.setSelected(false);
			humanoVsOrdenador.setSelected(false);
		}
		if(btn.getText().equals("Aceptar")){
			tablero.desbloquear();
			bloquearAceptar();
			bloquearTipoJuego();
		}
	}

	/**
	 * Controla los eventos que se producirán al seleccionar los botones de selección de tipo de juego, 
	 * estableciendo true para HvsO o false para HvsH
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
		if (humanoVsOrdenador.isSelected() && humanoVsHumano.isSelected()) {
			mensaje.setText("Elige solo un tipo a la vez");
			bloquearAceptar();
		} else if (humanoVsOrdenador.isSelected()) {
			tablero.setTipoJuego(true);
			mensaje.setText("Tipo de juego: HvsO");
			desbloquearAceptar();
		} else if (humanoVsHumano.isSelected()) {
			tablero.setTipoJuego(false);
			mensaje.setText("Tipo de juego: HvsH");
			desbloquearAceptar();
		} else {
			mensaje.setText("Elige modo de juego");
			bloquearAceptar();
		}
	}
}
