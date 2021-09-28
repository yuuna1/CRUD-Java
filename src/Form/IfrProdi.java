package Form;

import Tool.KoneksiDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrProdi extends javax.swing.JInternalFrame {
    KoneksiDB getCnn = new KoneksiDB();
    Connection Conn;
    String sqlselect, sqlinsert, sqldelete;
    String vkd_prodi, vkd_jur, vnm_prodi, vnm_jurusan;
    
    DefaultTableModel tblprodi;
    
    public IfrProdi() {
        initComponents();
        
        formTengah();
        listJurusan();
        disableForm();
        setTabelprodi();
        showDataTabel();
    }
    
     private void clearForm(){
        txtKdProdi.setText("");
        txtProdi.setText("");
        cmbJurusan.setSelectedIndex(0);
    }
    
    private void disableForm(){
        txtKdProdi.setEnabled(false);
        txtProdi.setEnabled(false);
        cmbJurusan.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    private void enableForm(){
        txtKdProdi.setEnabled(true);
        txtProdi.setEnabled(true);
        cmbJurusan.setEnabled(true);
        btnSimpan.setEnabled(true);
        
    }
    private void setTabelprodi(){
        String[]kolom1 = {"KD. Jur", "Jurusan", "KD. Prodi", "Program Studi"};
        tblprodi = new DefaultTableModel(null,kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class
            };
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            // Agar tabel tidak bisa diedit
            public boolean isCellEdittable(int row, int col){
                int cola = tblprodi.getColumnCount();
                return (col < cola) ? false : true;
            }
    };
    tbDataProdi.setModel(tblprodi);
    tbDataProdi.getColumnModel().getColumn(0).setPreferredWidth(75);
    tbDataProdi.getColumnModel().getColumn(1).setPreferredWidth(200);
    tbDataProdi.getColumnModel().getColumn(2).setPreferredWidth(75);
    tbDataProdi.getColumnModel().getColumn(3).setPreferredWidth(200);
    }
    
    private void clearTabel(){
        int row = tblprodi.getRowCount();
        for (int i = 0;i < row;i++){
            tblprodi.removeRow(0);
        }
    }
    
    private void showDataTabel(){
        try{
            Conn = null;
            Conn = getCnn.getConnection();
            clearTabel();
            String sql = "select * from tbprodi a, tbjurusan b "
                    + " where a.kd_jur=b.kd_jur order by b.nm_jurusan asc";
            Statement stat = Conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while(res.next()){
                vkd_jur = res.getString("kd_jur");
                vnm_jurusan = res.getString("nm_jurusan");
                vkd_prodi = res.getString("kd_prodi");
                vnm_prodi = res.getString("nm_prodi");
                Object[]data = {vkd_jur, vnm_jurusan, vkd_prodi, vnm_prodi};
                tblprodi.addRow(data);
            }
            lblRecord.setText("Record : "+tbDataProdi.getRowCount());
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(this,"Error Method showDataTabel : " + ex);
            }                                   
    }
    
    private void aksiSimpan(){
        vkd_jur = KeyJurusan[cmbJurusan.getSelectedIndex()];
        vkd_prodi = txtKdProdi.getText();
        vnm_prodi = txtProdi.getText();
        if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into tbprodi values ('"+vkd_prodi+"' , '"+vkd_jur+"', '"+vnm_prodi+"') ";
        }else{
            sqlinsert = "update tbprodi set nm_prodi = '"+vnm_prodi+"', kd_jur = '"+vkd_jur+"' "
                    + " where kd_prodi= '"+vkd_prodi+"' ";
        }
        try{
            Conn = null;
            Conn = getCnn.getConnection();
            Statement state = Conn.createStatement();
            state.executeUpdate(sqlinsert);
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            clearForm(); disableForm(); showDataTabel();
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiSimpan : " + ex);
            
        }
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this,
                "Apakah anda yakin akan menghapus data ini ? Kode : " +vkd_prodi,
                "Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
            try{
                Conn = null;
                Conn = getCnn.getConnection();
                String sqldelete = "delete from tbprodi where kd_prodi= '"+vkd_prodi+"'";
                java.sql.Statement state = Conn.createStatement();
                state.executeUpdate(sqldelete);
                JOptionPane.showMessageDialog(null, "Data Berhasil dihapus");
                clearForm(); disableForm(); showDataTabel();
            }catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error Method aksiHapus : " + ex);
            }
        }
    }
    
    private void formTengah(){
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if(frameSize.height < screensize.height){
           frameSize.height =  screensize.height;
        }
        if(frameSize.width < screensize.width){
           frameSize.width =  screensize.width;
        }
        this.setLocation((screensize.width - frameSize.width)/2, (screensize.height - frameSize.height)/2);
    }
    
    String[] KeyJurusan;
    private void listJurusan(){
        try{
            Conn = null;
            Conn = getCnn.getConnection();
            String sql = "" +
                    " SELECT kd_jur, nm_jurusan " +
                    " FROM tbjurusan order by nm_jurusan";
            Statement stat = Conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            cmbJurusan.removeAllItems();
            cmbJurusan.repaint();
            cmbJurusan.addItem("-- Pilih jurusan --");
            int i = 1;
            while(res.next()){
                cmbJurusan.addItem(res.getString(2));
                i++;
            }
            res.first();
            KeyJurusan = new String[i+1];
            for(Integer x =1;x < i;x++){
                KeyJurusan[x] = res.getString(1);
                res.next();
            }
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listJurusan " +ex);
        }
    }
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCool11 = new Tool.PanelCool1();
        jLabel3 = new javax.swing.JLabel();
        label1 = new Tool.Label();
        panelCool1 = new Tool.PanelCool();
        label2 = new Tool.Label();
        label3 = new Tool.Label();
        txtKdProdi = new Tool.TextField();
        txtProdi = new Tool.TextField();
        cmbJurusan = new javax.swing.JComboBox<>();
        label4 = new Tool.Label();
        panelCool2 = new Tool.PanelCool();
        btnTambah = new Tool.botton();
        btnSimpan = new Tool.botton();
        btnHapus = new Tool.botton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataProdi = new javax.swing.JTable();
        lblRecord = new Tool.Label();

        setClosable(true);
        setTitle("AKN-Program Studi");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/logo-medium.png"))); // NOI18N

        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("DATA PROGRAM STUDI");
        label1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        panelCool1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Form Data Program Studi"));

        label2.setForeground(new java.awt.Color(255, 255, 255));
        label2.setText("KD. Prodi");

        label3.setForeground(new java.awt.Color(255, 255, 255));
        label3.setText("Program Studi");

        txtKdProdi.setForeground(new java.awt.Color(255, 255, 255));
        txtKdProdi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKdProdiKeyPressed(evt);
            }
        });

        txtProdi.setForeground(new java.awt.Color(255, 255, 255));
        txtProdi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtProdiKeyPressed(evt);
            }
        });

        cmbJurusan.setBackground(new java.awt.Color(0, 153, 255));
        cmbJurusan.setForeground(new java.awt.Color(255, 255, 255));
        cmbJurusan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        label4.setForeground(new java.awt.Color(255, 255, 255));
        label4.setText("Jurusan");

        javax.swing.GroupLayout panelCool1Layout = new javax.swing.GroupLayout(panelCool1);
        panelCool1.setLayout(panelCool1Layout);
        panelCool1Layout.setHorizontalGroup(
            panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtProdi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelCool1Layout.createSequentialGroup()
                        .addComponent(txtKdProdi, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cmbJurusan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelCool1Layout.setVerticalGroup(
            panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtKdProdi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtProdi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelCool2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Navigasi"));

        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/insert.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/save_blue.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/btn_delete.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCool2Layout = new javax.swing.GroupLayout(panelCool2);
        panelCool2.setLayout(panelCool2Layout);
        panelCool2Layout.setHorizontalGroup(
            panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        panelCool2Layout.setVerticalGroup(
            panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Data Program Studi: Klik untuk mengubah/ menghapus data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        tbDataProdi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "KD. Prodi", "KD. Jurusan", "Prodi"
            }
        ));
        tbDataProdi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataProdiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataProdi);

        lblRecord.setForeground(new java.awt.Color(255, 255, 255));
        lblRecord.setText("Record : 0");

        javax.swing.GroupLayout panelCool11Layout = new javax.swing.GroupLayout(panelCool11);
        panelCool11.setLayout(panelCool11Layout);
        panelCool11Layout.setHorizontalGroup(
            panelCool11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCool11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCool1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelCool11Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(panelCool2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCool11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblRecord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelCool11Layout.setVerticalGroup(
            panelCool11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool11Layout.createSequentialGroup()
                .addGroup(panelCool11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCool11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3))
                    .addGroup(panelCool11Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCool1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCool2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblRecord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCool11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCool11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtKdProdiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdProdiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKdProdiKeyPressed

    private void txtProdiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProdiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProdiKeyPressed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        enableForm();
        clearForm();
        txtKdProdi.requestFocus(true);
        btnSimpan.setText("Simpan");
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtKdProdi.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Kode prodi harus diisi !",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtProdi.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Program Studi harus diisi !",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(cmbJurusan.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Jurusan belum dipilih !",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        aksiHapus();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataProdiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataProdiMouseClicked
        if(evt.getClickCount()==1){
            vkd_jur = tbDataProdi.getValueAt(tbDataProdi.getSelectedRow(), 0).toString();
            vnm_jurusan = tbDataProdi.getValueAt(tbDataProdi.getSelectedRow(), 1).toString();
            vkd_prodi = tbDataProdi.getValueAt(tbDataProdi.getSelectedRow(), 2).toString();
            vnm_prodi = tbDataProdi.getValueAt(tbDataProdi.getSelectedRow(), 3).toString();
            txtKdProdi.setText(vkd_prodi); 
            txtProdi.setText(vnm_prodi);
            cmbJurusan.setSelectedItem(vnm_jurusan);
            btnSimpan.setText("Ubah");
            enableForm();
            btnHapus.setEnabled(true);
            txtKdProdi.setEnabled(false);
            
        }
    }//GEN-LAST:event_tbDataProdiMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Tool.botton btnHapus;
    private Tool.botton btnSimpan;
    private Tool.botton btnTambah;
    private javax.swing.JComboBox<String> cmbJurusan;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private Tool.Label label1;
    private Tool.Label label2;
    private Tool.Label label3;
    private Tool.Label label4;
    private Tool.Label lblRecord;
    private Tool.PanelCool panelCool1;
    private Tool.PanelCool1 panelCool11;
    private Tool.PanelCool panelCool2;
    private javax.swing.JTable tbDataProdi;
    private Tool.TextField txtKdProdi;
    private Tool.TextField txtProdi;
    // End of variables declaration//GEN-END:variables
}
