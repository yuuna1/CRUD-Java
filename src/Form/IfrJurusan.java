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

public class IfrJurusan extends javax.swing.JInternalFrame {
    KoneksiDB getCnn = new KoneksiDB();
    Connection Conn;
    private DefaultTableModel tbljurusan;
    String vkd_jur, vjurusan;
    String sqlinsert;
    
    public IfrJurusan() {
        initComponents();
        
        formTengah();
        clearForm();
        disableForm();
        setTabel();
        showDataTabel();
    }
    
    private void clearForm(){
        txtKdJurusan.setText("");
        txtNmJurusan.setText("");
    }
    
    private void disableForm(){
        txtKdJurusan.setEnabled(false);
        txtNmJurusan.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    private void enableForm(){
        txtKdJurusan.setEnabled(true);
        txtNmJurusan.setEnabled(true);
        btnSimpan.setEnabled(true);
    }
    
    private void setTabel(){
        String[]kolom1 = {"KD. Jur", "Jurusan"};
        tbljurusan = new DefaultTableModel(null,kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class
            };
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            // Agar tabel tidak bisa diedit
            public boolean isCellEdittable(int row, int col){
                int cola = tbljurusan.getColumnCount();
                return (col < cola) ? false : true;
            }
    };
    tbDataJurusan.setModel(tbljurusan);
    tbDataJurusan.getColumnModel().getColumn(0).setPreferredWidth(75);
    tbDataJurusan.getColumnModel().getColumn(1).setPreferredWidth(250);
    }
    
    private void clearTabel(){
        int row = tbljurusan.getRowCount();
        for (int i = 0;i < row;i++){
            tbljurusan.removeRow(0);
        }
    }
    
    private void showDataTabel(){
        try{
            Conn = null;
            Conn = getCnn.getConnection();
            clearTabel();
            String sql = "select * from tbjurusan order by kd_jur";
            Statement stat = Conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            while(res.next()){
                String xkd = res.getString(1);
                String xjurusan = res.getString(2);
                Object[]data = {xkd, xjurusan};
                tbljurusan.addRow(data);
            }
            lblRecord.setText("Record : "+tbDataJurusan.getRowCount());
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(this,"Error Method showDataTabel : " + ex);
            }
    }
    
    private void aksiSimpan(){
        vkd_jur = txtKdJurusan.getText();
        vjurusan = txtNmJurusan.getText();
        if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into tbjurusan values ('"+vkd_jur+"' , '"+vjurusan+"') ";
        }else{
            sqlinsert = "update tbjurusan set nm_jurusan = '"+vjurusan+"' where kd_jur= '"+vkd_jur+"' ";
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
                "Apakah anda yakin akan menghapus data ini ? Kode : " +vkd_jur,
                "Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(jawab== JOptionPane.YES_OPTION){
            try{
                Conn = null;
                Conn = getCnn.getConnection();
                String sqldelete = "delete from tbjurusan where kd_jur= '"+vkd_jur+"'";
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

        panelCool11 = new Tool.PanelCool1();
        jLabel3 = new javax.swing.JLabel();
        label1 = new Tool.Label();
        panelCool1 = new Tool.PanelCool();
        label2 = new Tool.Label();
        label3 = new Tool.Label();
        txtKdJurusan = new Tool.TextField();
        txtNmJurusan = new Tool.TextField();
        panelCool2 = new Tool.PanelCool();
        btnTambah = new Tool.botton();
        btnSimpan = new Tool.botton();
        btnHapus = new Tool.botton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDataJurusan = new javax.swing.JTable();
        lblRecord = new Tool.Label();

        setClosable(true);
        setTitle("AKN-Jurusan");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/logo-medium.png"))); // NOI18N

        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("DATA JURUSAN");
        label1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        panelCool1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Form Data Jurusan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        label2.setForeground(new java.awt.Color(255, 255, 255));
        label2.setText("KD. Jurusan");

        label3.setForeground(new java.awt.Color(255, 255, 255));
        label3.setText("Jurusan");

        txtKdJurusan.setForeground(new java.awt.Color(255, 255, 255));
        txtKdJurusan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKdJurusanKeyPressed(evt);
            }
        });

        txtNmJurusan.setForeground(new java.awt.Color(255, 255, 255));
        txtNmJurusan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNmJurusanKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panelCool1Layout = new javax.swing.GroupLayout(panelCool1);
        panelCool1.setLayout(panelCool1Layout);
        panelCool1Layout.setHorizontalGroup(
            panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNmJurusan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelCool1Layout.createSequentialGroup()
                        .addComponent(txtKdJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCool1Layout.setVerticalGroup(
            panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKdJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCool1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNmJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelCool2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Navigasi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

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
                .addContainerGap(110, Short.MAX_VALUE)
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

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Data Jurusan: Klik untuk mengubah/ menghapus data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        tbDataJurusan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "KD. Jurusan", "Jurusan"
            }
        ));
        tbDataJurusan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataJurusanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbDataJurusan);

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
                .addContainerGap(34, Short.MAX_VALUE))
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

    private void txtKdJurusanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKdJurusanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKdJurusanKeyPressed

    private void txtNmJurusanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNmJurusanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNmJurusanKeyPressed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        enableForm();
        clearForm();
        txtKdJurusan.requestFocus(true);
        btnSimpan.setText("Simpan");
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtKdJurusan.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Kode jurusan harus diisi !",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtNmJurusan.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nama jurusan harus diisi !",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        aksiHapus();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataJurusanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataJurusanMouseClicked
        if(evt.getClickCount()==1){
            vkd_jur = tbDataJurusan.getValueAt(tbDataJurusan.getSelectedRow(), 0).toString();
            vjurusan = tbDataJurusan.getValueAt(tbDataJurusan.getSelectedRow(), 1).toString();
            txtKdJurusan.setText(vkd_jur); 
            txtNmJurusan.setText(vjurusan);
            btnSimpan.setText("Ubah");
            enableForm();
            btnHapus.setEnabled(true);
            txtKdJurusan.setEnabled(false);
        }
    }//GEN-LAST:event_tbDataJurusanMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Tool.botton btnHapus;
    private Tool.botton btnSimpan;
    private Tool.botton btnTambah;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private Tool.Label label1;
    private Tool.Label label2;
    private Tool.Label label3;
    private Tool.Label lblRecord;
    private Tool.PanelCool panelCool1;
    private Tool.PanelCool1 panelCool11;
    private Tool.PanelCool panelCool2;
    private javax.swing.JTable tbDataJurusan;
    private Tool.TextField txtKdJurusan;
    private Tool.TextField txtNmJurusan;
    // End of variables declaration//GEN-END:variables
}
