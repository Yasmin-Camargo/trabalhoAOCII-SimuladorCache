/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package cache_simulator;

/**
 @author Caroline Camargo e Yasmin Camargo
 * --- Trabalho de Implementação de um Simulador de Caches ---
 *  Disciplina Arquitetura e Organização de Computadores II
 *  Prof. Marcelo Schiavon Porto
 **/

public class frameCache extends javax.swing.JFrame {

    public frameCache() {
        initComponents();
    }
    
    public void inicializaComponentes(int nsets, int bsize, int assoc, String subst, int flagOut, String arquivoEntrada){
        nsetsC.setValue(nsets); //coloca valores calculados para interface gráfica
        bsizeC.setValue(bsize);
        assocC.setValue(assoc);
        arquivoEntradaC.setText(arquivoEntrada);
        if (subst.equals("F") || subst.equals("f")) {       //fifo ou lru
            substC.setSelectedIndex(1);
        } else if(subst.equals("R") || subst.equals("r")){ 
            substC.setSelectedIndex(0);
        } else{
            substC.setSelectedIndex(2);
        }
    }
    
    public void atualizaLog(String log){
        this.log.setText(log);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nsetsC = new javax.swing.JSpinner();
        bsizeC = new javax.swing.JSpinner();
        substC = new javax.swing.JComboBox<>();
        start = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        log = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        arquivoEntradaC = new javax.swing.JTextField();
        assocC = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Cache Simulator");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 222, 55));

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 51, 0));
        jLabel3.setText("Grau de associatividade:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, 48));

        jLabel4.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 51, 0));
        jLabel4.setText("Número de conjuntos:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 170, 48));

        jLabel5.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 51, 0));
        jLabel5.setText("Arquivo de entrada:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 184, 48));

        nsetsC.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        jPanel1.add(nsetsC, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 150, 30));

        bsizeC.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        jPanel1.add(bsizeC, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, 130, 30));

        substC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Random", "FIFO", "LRU" }));
        substC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                substCActionPerformed(evt);
            }
        });
        jPanel1.add(substC, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 170, 40));

        start.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        start.setForeground(new java.awt.Color(255, 51, 0));
        start.setText("START");
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });
        jPanel1.add(start, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 240, 110, 70));

        jScrollPane1.setBackground(new java.awt.Color(253, 245, 245));

        log.setEditable(false);
        log.setBackground(new java.awt.Color(255, 255, 255));
        log.setColumns(20);
        log.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        log.setRows(5);
        jScrollPane1.setViewportView(log);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 40, 570, 430));

        jLabel7.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 51, 0));
        jLabel7.setText("Tamanho do bloco:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 155, 48));

        jLabel8.setFont(new java.awt.Font("Microsoft YaHei", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 51, 0));
        jLabel8.setText("Política de substituição:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 184, 48));

        arquivoEntradaC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arquivoEntradaCActionPerformed(evt);
            }
        });
        jPanel1.add(arquivoEntradaC, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, 330, 40));

        assocC.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        jPanel1.add(assocC, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 150, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 490));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void substCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_substCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_substCActionPerformed

    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed
        // extrai dados e executa simulação na cache
        int nsets, bsize, assoc;
        String arquivoEntrada, subst;
        
        nsets = (int) nsetsC.getValue();
        bsize = (int) bsizeC.getValue();
        assoc = (int) assocC.getValue();
        arquivoEntrada = arquivoEntradaC.getText();
        if (substC.getSelectedIndex() == 0) {       
            subst = "r";
        } else if(substC.getSelectedIndex() == 1){ 
            subst = "f";
        } else{
            subst = "l";
        }
       
        atualizaLog(cache_simulator.execucao(nsets, bsize, assoc, subst, 0, arquivoEntrada));
    }//GEN-LAST:event_startActionPerformed

    private void arquivoEntradaCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arquivoEntradaCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_arquivoEntradaCActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frameCache.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frameCache.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frameCache.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frameCache.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frameCache().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField arquivoEntradaC;
    private javax.swing.JSpinner assocC;
    private javax.swing.JSpinner bsizeC;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea log;
    private javax.swing.JSpinner nsetsC;
    private javax.swing.JButton start;
    private javax.swing.JComboBox<String> substC;
    // End of variables declaration//GEN-END:variables
}
