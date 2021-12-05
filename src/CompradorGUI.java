
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class CompradorGUI extends javax.swing.JFrame {

    private final Comprador comprador;

    public CompradorGUI(Comprador comprador) {
        this.comprador = comprador;
        initComponents();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                comprador.doDelete();
            }
        });
        this.setTitle(comprador.getLocalName());
        listaIntereses.setModel(new DefaultListModel<>());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        campoTitulo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        campoPrecio = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        botonLanzar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaIntereses = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Comprador");

        jLabel1.setText("Título");

        jLabel2.setText("Precio");

        botonLanzar.setText("Lanzar");
        botonLanzar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLanzarActionPerformed(evt);
            }
        });

        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Titulo", "Precio actual", "Vendedor", "Ganador?"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Float.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabla);

        jLabel3.setText("Subastas en las que se participa");

        jLabel4.setText("Intereses");

        listaIntereses.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(listaIntereses);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(campoTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(campoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(botonLanzar))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(260, 260, 260)
                        .addComponent(jLabel4)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonLanzar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonLanzarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLanzarActionPerformed
        if (!campoTitulo.getText().isBlank() && !campoPrecio.getText().isBlank()) {
            comprador.updateIntereses(campoTitulo.getText().trim(), Float.parseFloat(campoPrecio.getText().trim()));
            campoTitulo.setText("");
            campoPrecio.setText("");
        }
    }//GEN-LAST:event_botonLanzarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonLanzar;
    private javax.swing.JTextField campoPrecio;
    private javax.swing.JTextField campoTitulo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList<String> listaIntereses;
    private javax.swing.JTable tabla;
    // End of variables declaration//GEN-END:variables

    void lanzarVentana() {
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    void actualizarTabla(HashMap<String, Subasta> subastas) {

        Object[] columnas = {"Titulo", "Precio actual", "Vendedor", "Ganador?"};

        Object[][] filas = new Object[subastas.keySet().size()][4];
        int count = 0;
        for (Subasta subasta : subastas.values()) {
            Object[] fila = {subasta.getTitulo(), subasta.getPrecio(), subasta.getVendedor(), subasta.getGanador() != null};
            filas[count] = fila;
            count++;
        }

        DefaultTableModel modelo = new DefaultTableModel(filas, columnas) {
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                    case 2:
                        return String.class;
                    case 1:
                        return Float.class;
                    default:
                        return Boolean.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Object getValueAt(int row, int col) {
                if (!this.dataVector.isEmpty()) {
                    return super.getValueAt(row, col);
                } else {
                    return null;
                }
            }
        };

        SwingUtilities.invokeLater(() -> {
            tabla.setModel(modelo);
        });

    }

    void notificar(String titulo, Float precio, String vendedor) {
        JOptionPane pane = new JOptionPane("\"" + titulo + "\" fue comprado a " + vendedor + " por " + String.format("%.2f", precio));
        JDialog dialog = pane.createDialog("Subasta finalizada");
        dialog.setModal(false);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    void notificarInteres(String titulo, boolean insertar) {
        if (insertar) {
            SwingUtilities.invokeLater(() -> {
                ((DefaultListModel) listaIntereses.getModel()).addElement(titulo);
            });

        } else {
            JOptionPane pane = new JOptionPane("Ya se interesó en ese título");
            JDialog dialog = pane.createDialog("Interés duplicado");
            dialog.setModal(false);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }
}
