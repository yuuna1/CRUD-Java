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

public class IfrThAkademik extends javax.swing.JInternalFrame {
    KoneksiDB getCnn = new KoneksiDB();
    Connection Conn;
    String sqlselect, sqlinsert, sqldelete;
    String vkd_ta, vta, vsmt;
    
    DefaultTableModel tblta;
    
    public IfrThAkademik() {
        initComponents();
        
        formTengah();
        clearForm();
        disableForm();
        setTabelTa();
        showDataTabel();
    }
    
     private void clearForm(){
        txtKdTa.setText("");
        txtTa.setText("");
         buttonGroup1.clearSelection();
    }
    
    private void disableForm(){
        txtKdTa.setEnabled(false);
        txtTa.setEnabled(false);
        rbGasal.setEnabled(false);
        rbGenap.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    private void enableForm(){
        txtKdTa.setEnabled(true);
        txtTa.setEnabled(true);
        rbGasal.setEnabled(true);
        rbGenap.setEnabled(true);
        btnSimpan.setEnabled(true);
        
    }
    private void setTabelTa(){
        String[]kolom1 = {"KD. Tahun Akademik", "Tahun Akademik", "Semester"};
        tblta = new DefaultTableModel(null,kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class
            };
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            // Agar tabel tidak bisa diedit
            public boolean isCellEdittable(int row, int col){
                int cola = tblta.getColumnCount();
                return (col < cola) ? false : true;
            }
    };
    tbDataTa.setModel(tblta);
    tbDataTa.getColumnModel().getColumn(0).setPreferredWidth(75);
    tbDataTa.getColumnModel().getColumn(1).setPreferredWidth(200);
    tbDataTa.getColumnModel().getColumn(2).setPreferredWidth(75);
    }
    
    private void clearTabel(){
        int row = tblta.getRowCount();
        for (int i = 0;i < row;i++){
            tblta.removeRow(0);
        }
    }
    
    private void showDataTabel(){
        try{
            Conn = null;
            Conn = getCnn.getConnection();
            clearTabel();
            sqlselect = "select * from tbthakademik order by thakademik";
            Statement stat = Conn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vkd_ta = res.getString("kd_ta");
                vta = res.getString("thakademik");
                vsmt = res.getString("semester");
                Object[]data = {vkd_ta, vta, vsmt};
                tblta.addRow(data);
            }
            lblRecord.setText("Record : "+tbDataTa.getRowCount());
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(this,"Error Method showDataTabel : " + ex);
            }
    }
    
    private void aksiSimpan(){
        vkd_ta = txtKdTa.getText();
        vta = txtTa.getText();
        if(rbGenap.isSelected()){
            vsmt = "Genap";
        }else {
            vsmt = "Gasal";
        }
        if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into tbthakademik values ('"+vkd_ta+"' , '"+vta+"', '"+vsmt+"') ";
        }else{
            sqlinsert = "update tbthakademik set thakademik = '"+vta+"', semester = '"+vsmt+"' "
                    + " where kd_ta= '"+vkd_ta+"' ";
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
                "Apakah anda yakin akan menghapus data ini ? Kode : " +vkd_ta,
                "Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
            try{
                Conn = null;
                Conn = getCnn.getConnection();
                sqldelete = "delete from tbthakademik where kd_ta= '"+vkd_ta+"'";
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panelCool11 = new Tool.PanelCool1();
        jLabel3 = new javax.swing.JLabel();
        label1 = new Tool.Label();
        panelCool1 = new Tool.PanelCool();
        label2 = new Tool.Label();
        label3 = new Tool.Label();
        txtKdTa = new Tool.TextField();
        txtTa = new Tool.TextField();
        label4 = new Tool.Label();
        rbGenap = new javax.swing.JRadioButton();
        rbGasal = new javax.swing.JRadioButton();
        panelCool2 = new Tool.PanelCool();
        btnTambah = new Tool.botton();
        btnSimpan = new Tool.botton();
        btnHapus = new Tool.botton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataTa = new javax.swing.JTable();
        lblRecord = new Tool.Label();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("AKN-Tahun Akademik");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/logo-medium.png"))); // NOI18N

        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("DATA TAHUN AKADEMIK");
        label1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        panelCool1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Form Data Tahun Akademik"));

        label2.setForeground(new java.awt.Color(255, 255, 255));
        label2.setText("KD. Tahun Akademik");

        label3.setForeground(new java.awt.Color(255, 255, 255));
        label3.setText("Tahun Akademik");

        txtKdTa.setForeground(new java.awt.Color(255, 255, 255));
        txtKdTa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKdTaKeyPressed(evt);
            }
        });

        txtTa.setForeground(new java.awt.Color(255, 255, 255));
        txtTa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTaKeyPressed(evt);
            }
        });

        label4.setForeground(new java.awt.Color(255, 255, 255));
        label4.setText("Semester");

        rbGenap.setBackground(new java.awt.Color(0, 51, 255));
        buttonGroup1.add(rbGenap);
        rbGenap.setForeground(new java.awt.Color(255, 255, 255));
        rbGenap.setText("Genap");

        rbGasal.setBackground(new java.awt.Color(0, 51, 255));
        buttonGroup1.add(rbGasal);
        rbGasal.setForeground(new java.awt.Color(255, 255, 255));
        rbGasal.setText("Gasal");

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
                    .addComponent(txtTa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelCool1Layout.createSequentialGroup()
                        .addGroup(panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtKdTa, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelCool1Layout.createSequentialGroup()
                                .addComponent(rbGenap)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rbGasal)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCool1Layout.setVerticalGroup(
            panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKdTa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbGenap)
                        .addComponent(rbGasal)))
                .addContainerGap(13, Short.MAX_VALUE))
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

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Data Tahun Akademik: Klik untuk mengubah/ menghapus data"));

        tbDataTa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "KD. Prodi", "KD. Jurusan", "Prodi"
            }
        ));
        tbDataTa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataTaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataTa);

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
                .addContainerGap(59, Short.MAX_VALUE))
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

    private void txtKdTaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdTaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKdTaKeyPressed

    private void txtTaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTaKeyPressed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        enableForm();
        clearForm();
        txtKdTa.requestFocus(true);
        btnSimpan.setText("Simpan");
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtKdTa.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Kode tahunakademik harus diisi !",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtTa.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Tahun akademik harus diisi !",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(buttonGroup1.isSelected(null)){
            JOptionPane.showMessageDialog(this, "Semester belum dipilih !",
                "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        aksiHapus();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataTaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataTaMouseClicked
        if(evt.getClickCount()==1){
            vkd_ta = tbDataTa.getValueAt(tbDataTa.getSelectedRow(), 0).toString();
            vta = tbDataTa.getValueAt(tbDataTa.getSelectedRow(), 1).toString();
            vsmt = tbDataTa.getValueAt(tbDataTa.getSelectedRow(), 2).toString();
            txtKdTa.setText(vkd_ta); 
            txtTa.setText(vta);
            if(vsmt.equals("Genap")){
                    rbGenap.setSelected(true);
                }else{
                    rbGasal.setSelected(true);
                }
            btnSimpan.setText("Ubah");
            enableForm();
            btnHapus.setEnabled(true);
            txtKdTa.setEnabled(false);
            
        }
    }//GEN-LAST:event_tbDataTaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Tool.botton btnHapus;
    private Tool.botton btnSimpan;
    private Tool.botton btnTambah;
    private javax.swing.ButtonGroup buttonGroup1;
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
    private javax.swing.JRadioButton rbGasal;
    private javax.swing.JRadioButton rbGenap;
    private javax.swing.JTable tbDataTa;
    private Tool.TextField txtKdTa;
    private Tool.TextField txtTa;
    // End of variables declaration//GEN-END:variables
}
