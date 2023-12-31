/*
 * Created by JFormDesigner on Tue Jun 13 20:22:02 PET 2023
 */

package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import logica.ButtonClickListener;
import net.miginfocom.swing.*;

/**
 * @author andre
 */
public class MenuGerente extends JPanel {
    private ButtonClickListener buttonClickListenerAnadirProducto;
    private ButtonClickListener buttonClickListenerAgregarEmpleado;
    private ButtonClickListener buttonClickListenerEliminarEmpleado;
    private ButtonClickListener buttonClickListenerEliminarProducto;
    private ButtonClickListener buttonClickListenerReportes;
    private ButtonClickListener buttonClickListenerRegresar;

    public MenuGerente() {
        initComponents();
        botonAnadirProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonClickListenerAnadirProducto != null) {
                    buttonClickListenerAnadirProducto.onButtonClick();
                }
            }
        });
        botonEliminarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonClickListenerEliminarProducto != null) {
                    buttonClickListenerEliminarProducto.onButtonClick();
                }
            }
        });
        botonAgregarEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonClickListenerAgregarEmpleado != null) {
                    buttonClickListenerAgregarEmpleado.onButtonClick();
                }
            }
        });
        botonEliminarEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonClickListenerEliminarEmpleado != null) {
                    buttonClickListenerEliminarEmpleado.onButtonClick();
                }
            }
        });
        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonClickListenerRegresar != null) {
                    buttonClickListenerRegresar.onButtonClick();
                }
            }
        });
        botonEliminarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonClickListenerEliminarProducto != null) {
                    buttonClickListenerEliminarProducto.onButtonClick();
                }
            }
        });
        botonReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonClickListenerReportes != null) {
                    buttonClickListenerReportes.onButtonClick();
                }
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Educational license - Ricardo Reaño (André Forsyth)
        botonAnadirProducto = new JButton();
        botonEditarProducto = new JButton();
        botonEliminarProducto = new JButton();
        botonAgregarEmpleado = new JButton();
        botonEliminarEmpleado = new JButton();
        botonReportes = new JButton();
        botonRegresar = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {37, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 1.0E-4};

        //---- botonAnadirProducto ----
        botonAnadirProducto.setText("A\u00f1adir Producto");
        botonAnadirProducto.setPreferredSize(new Dimension(136, 80));
        add(botonAnadirProducto, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.BASELINE, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 7, 0), 0, 0));

        //---- botonEliminarProducto ----
        botonEliminarProducto.setText("Eliminar Producto");
        botonEliminarProducto.setPreferredSize(new Dimension(144, 80));
        add(botonEliminarProducto, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.BASELINE, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 7, 0), 0, 0));

        //---- botonAgregarEmpleado ----
        botonAgregarEmpleado.setText("Agregar Empleado");
        botonAgregarEmpleado.setPreferredSize(new Dimension(148, 80));
        add(botonAgregarEmpleado, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.BASELINE, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 7, 0), 0, 0));

        //---- botonEliminarEmpleado ----
        botonEliminarEmpleado.setText("Eliminar Empleado");
        botonEliminarEmpleado.setPreferredSize(new Dimension(148, 80));
        add(botonEliminarEmpleado, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.BASELINE, GridBagConstraints.HORIZONTAL,
            new Insets(0, 0, 7, 0), 0, 0));

        //---- botonReportes ----
        botonReportes.setText("Reportes");
        botonReportes.setPreferredSize(new Dimension(70, 80));
        add(botonReportes, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 7, 0), 0, 0));

        //---- botonRegresar ----
        botonRegresar.setText("Regresar");
        add(botonRegresar, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Educational license - Ricardo Reaño (André Forsyth)
    private JButton botonAnadirProducto;
    private JButton botonEditarProducto;
    private JButton botonEliminarProducto;
    private JButton botonAgregarEmpleado;
    private JButton botonEliminarEmpleado;
    private JButton botonReportes;
    private JButton botonRegresar;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
    public void setButtonClickListenerAnadirProducto(ButtonClickListener listener) {
        this.buttonClickListenerAnadirProducto = listener;
    }
    public void setButtonClickListenerAgregarEmpleado(ButtonClickListener listener) {
        this.buttonClickListenerAgregarEmpleado = listener;
    }
    public void setButtonClickListenerEliminarEmpleado(ButtonClickListener listener) {
        this.buttonClickListenerEliminarEmpleado = listener;
    }
    public void setButtonClickListenerRegresar(ButtonClickListener listener) {
        this.buttonClickListenerRegresar = listener;
    }
    public void setButtonClickListenerEliminarProducto(ButtonClickListener listener) {
        this.buttonClickListenerEliminarProducto = listener;
    }
    public void setButtonClickListenerReportes(ButtonClickListener listener) {
        this.buttonClickListenerReportes = listener;
    }
}
