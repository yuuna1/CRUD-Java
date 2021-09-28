package Form;

import Tool.KoneksiDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Laporan extends javax.swing.JInternalFrame {
    KoneksiDB getCnn = new KoneksiDB();
    Connection Conn;
    String sqlselect;
    String vkd_prodi, vnm_prodi;
    
    public Laporan() {
        initComponents();
        
        Locale locale =new Locale ("id", "ID");
        locale.setDefault(locale);
        listProdi();
        
    }
    
    String[] KeyProdi;
    private void listProdi(){
        try{
            Conn = null;
            Conn = getCnn.getConnection();
            sqlselect = "SELECT kd_prodi, nm_prodi " +
                    " FROM tbprodi order by nm_prodi asc";
            Statement stat = Conn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbProdi.removeAllItems();
            cmbProdi.repaint();
            cmbProdi.addItem("-- Pilih Program Studi --");
            int i = 1;
            while(res.next()){
                cmbProdi.addItem(res.getString(2));
                i++;
            }
            res.first();
            KeyProdi = new String[i+1];
            for(Integer x =1;x < i;x++){
                KeyProdi[x] = res.getString(1);
                res.next();
            }
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listProdi " +ex);
        }
    }
    
    private void cetakLaporanMahasiswa1(){
        String pth = System.getProperty("user.dir") + "/laporan/LapMahasiswa1.jrxml";
        try{
            Map<String, Object> parameters = new HashMap<>();
            Conn = null;
            Conn = getCnn.getConnection();
            JasperReport jrpt = JasperCompileManager.compileReport(pth);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, parameters, Conn);
            
            JasperViewer.viewReport(jprint, false);
            
        }catch (SQLException | JRException ex){
            JOptionPane.showConfirmDialog(null, "Error method cetakLaporanMahasiswa1 : " + ex,
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void cetakLaporanMahasiswa2(){
        vkd_prodi = KeyProdi[cmbProdi.getSelectedIndex()];
        String pth = System.getProperty("user.dir") + "/laporan/LapMahasiswa2.jrxml";
        try{
            Map<String, Object> parameters = new HashMap<>();
            Conn = null;
            Conn = getCnn.getConnection();
            parameters.put("parProdi", vkd_prodi);
            JasperReport jrpt = JasperCompileManager.compileReport(pth);
            JasperPrint jprint = JasperFillManager.fillReport(jrpt, parameters, Conn);
            
            JasperViewer.viewReport(jprint, false);
            
        }catch (SQLException | JRException ex){
            JOptionPane.showConfirmDialog(null, "Error method cetakLaporanMahasiswa2 : " + ex,
            "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCool11 = new Tool.PanelCool1();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        label1 = new Tool.Label();
        panelCool12 = new Tool.PanelCool1();
        btnCetak = new javax.swing.JButton();
        panelCool13 = new Tool.PanelCool1();
        cmbProdi = new javax.swing.JComboBox<>();
        btnCetak1 = new javax.swing.JButton();

        setClosable(true);
        setTitle("AKN-Laporan");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Form ini digunakan untuk mencetak laporan mahasiswa !");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/logo-medium.png"))); // NOI18N

        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("LAPORAN MAHASISWA");
        label1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        panelCool12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 255, 255)), "Cetak All : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        btnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/printer_on.png"))); // NOI18N
        btnCetak.setText("Cetak");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCool12Layout = new javax.swing.GroupLayout(panelCool12);
        panelCool12.setLayout(panelCool12Layout);
        panelCool12Layout.setHorizontalGroup(
            panelCool12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCool12Layout.setVerticalGroup(
            panelCool12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCetak)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelCool13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 255, 255)), "Cetak Per Program Studi : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        cmbProdi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Program Studi --" }));

        btnCetak1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/printer_on.png"))); // NOI18N
        btnCetak1.setText("Cetak");
        btnCetak1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetak1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCool13Layout = new javax.swing.GroupLayout(panelCool13);
        panelCool13.setLayout(panelCool13Layout);
        panelCool13Layout.setHorizontalGroup(
            panelCool13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbProdi, 0, 196, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCetak1)
                .addContainerGap())
        );
        panelCool13Layout.setVerticalGroup(
            panelCool13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCool13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCetak1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbProdi))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelCool11Layout = new javax.swing.GroupLayout(panelCool11);
        panelCool11.setLayout(panelCool11Layout);
        panelCool11Layout.setHorizontalGroup(
            panelCool11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCool11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panelCool12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelCool11Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCool11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(panelCool13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCool11Layout.setVerticalGroup(
            panelCool11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool11Layout.createSequentialGroup()
                .addGroup(panelCool11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCool11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3))
                    .addGroup(panelCool11Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)))
                .addGap(18, 18, 18)
                .addComponent(panelCool12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCool13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCool11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelCool11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        cetakLaporanMahasiswa1();
    }//GEN-LAST:event_btnCetakActionPerformed

    private void btnCetak1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetak1ActionPerformed
        cetakLaporanMahasiswa2();
    }//GEN-LAST:event_btnCetak1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnCetak1;
    private javax.swing.JComboBox<String> cmbProdi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private Tool.Label label1;
    private Tool.PanelCool1 panelCool11;
    private Tool.PanelCool1 panelCool12;
    private Tool.PanelCool1 panelCool13;
    // End of variables declaration//GEN-END:variables
}
