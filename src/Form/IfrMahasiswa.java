package Form;

import Tool.KoneksiDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrMahasiswa extends javax.swing.JInternalFrame {

    KoneksiDB getCnn = new KoneksiDB();
    Connection Conn;
    String sqlselect, sqlinsert, sqldelete;
    String vta, vkd_prodi, vnm_prodi, vnim, vnim2, vnama_mhs, vjns_kel, vtmp_lahir, vtgl_lahir, vagama,
            vnama_ayah, vnama_ibu, valamat, vno_telepon;
    DefaultTableModel tblmahasiswa;
    SimpleDateFormat tglview = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat tglinput = new SimpleDateFormat("yyyy-MM-dd");
    
    public IfrMahasiswa() {
        initComponents();
        
        listTa();
        listProdi();
        formTengah();
        setTabelMahasiswa();
        showDataTabel();
        clearForm();
    }
    
    private void clearForm(){
        cmbTa.setSelectedIndex(0);
        cmbProdi.setSelectedIndex(0);
        txtNim.setText("");
        txtNamaMhs.setText("");
        buttonGroup1.clearSelection();
        txtTempatLahir.setText("");
        dtTglLahir.setDate(new Date());
        cmbAgama.setSelectedIndex(0);
        txtNamaAyah.setText("");
        txtNamaIbu.setText("");
        txtAlamat.setText("");
        txtNoTelepon.setText("");
        txtCariMhs.setText("");
        btnSimpan.setText("Simpan");
        btnHapus.setEnabled(false);
        btnTambah.setText("Tambah");
        vnim="";
        vjns_kel="";
        lblTa.setText("");
    }
    
    private void setTabelMahasiswa(){
        String[]kolom1 = {"Program Studi","NIM","Nama Mahasiswa","L/P", "Tempat", "Tgl Lahir", "Agama",
            "Nama Ayah", "Nama Ibu", "Alamat", "No. Telepon"};
        tblmahasiswa = new DefaultTableModel(null,kolom1){
            Class[] types = new Class[]{
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class,
                java.lang.String.class
            };
            public Class getColumnClass(int columnIndex){
                return types [columnIndex];
            }
            public boolean isCellEdittable(int row, int col){
                int cola = tblmahasiswa.getColumnCount();
                return (col < cola) ? false : true;
            }
        };
        tbDataMahasiswa.setModel(tblmahasiswa);
        tbDataMahasiswa.getColumnModel().getColumn(0).setPreferredWidth(300);
        tbDataMahasiswa.getColumnModel().getColumn(1).setPreferredWidth(200);
        tbDataMahasiswa.getColumnModel().getColumn(2).setPreferredWidth(300);
        tbDataMahasiswa.getColumnModel().getColumn(3).setPreferredWidth(75);
        tbDataMahasiswa.getColumnModel().getColumn(4).setPreferredWidth(200);
        tbDataMahasiswa.getColumnModel().getColumn(5).setPreferredWidth(200);
        tbDataMahasiswa.getColumnModel().getColumn(6).setPreferredWidth(200);
        tbDataMahasiswa.getColumnModel().getColumn(7).setPreferredWidth(200);
        tbDataMahasiswa.getColumnModel().getColumn(8).setPreferredWidth(200);
        tbDataMahasiswa.getColumnModel().getColumn(9).setPreferredWidth(400);
        tbDataMahasiswa.getColumnModel().getColumn(10).setPreferredWidth(200);
    }
    
    private void clearTabel(){
        int row = tblmahasiswa.getRowCount();
        for(int i =0;i < row;i++){
            tblmahasiswa.removeRow(0);
        }
    }
    
    private void showDataTabel(){
        try{
            Conn = null;
            Conn = getCnn.getConnection();
            clearTabel();
            sqlselect = "select * from tbmahasiswa a, tbprodi b"
                    + " where a.kd_prodi=b.kd_prodi order by a.kd_prodi and a.nama_mhs asc";
            Statement stat = Conn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vnm_prodi = res.getString("nm_prodi");
                vnim = res.getString("nim");
                vnama_mhs = res.getString("nama_mhs");
                vjns_kel = res.getString("jns_kel");
                vtmp_lahir = res.getString("tmp_lahir");
                vtgl_lahir = tglview.format(res.getDate("tgl_lahir"));
                vagama = res.getString("agama");
                vnama_ayah = res.getString("nama_ayah");
                vnama_ibu = res.getString("nama_ibu");
                valamat = res.getString("alamat");
                vno_telepon = res.getString("nama_telepon");
                Object[]data = {vnm_prodi, vnim, vnama_mhs, vjns_kel, vtmp_lahir,
                    vtgl_lahir, vagama, vnama_ayah, vnama_ibu, valamat, vno_telepon};
                tblmahasiswa.addRow(data); 
                clearForm();
            }
            lblRecord.setText("Record : "+tbDataMahasiswa.getRowCount());
            } catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method showDataTabel : "+ ex);
            }
    }
    
    //Tampil Tabel Ketika Pencarian
   private void cariMahasiswa(){
        try{
            Conn = null;
            Conn = getCnn.getConnection();
            clearTabel();
            sqlselect = "select * from tbmahasiswa a, tbprodi b"
                    + " where a.kd_prodi=b.kd_prodi and nama_mhs like '%"+txtCariMhs.getText()+"%' "
                    + " order by a.kd_prodi, a.nama_mhs asc";
            Statement stat = Conn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vnm_prodi = res.getString("nm_prodi");
                vnim = res.getString("nim");
                vnama_mhs = res.getString("nama_mhs");
                vjns_kel = res.getString("jns_kel");
                vtmp_lahir = res.getString("tmp_lahir");
                vtgl_lahir = tglview.format(res.getDate("tgl_lahir"));
                vagama = res.getString("agama");
                vnama_ayah = res.getString("nama_ayah");
                vnama_ibu = res.getString("nama_ibu");
                valamat = res.getString("alamat");
                vno_telepon = res.getString("nama_telepon");
                Object[]data = {vnm_prodi, vnim, vnama_mhs, vjns_kel, vtmp_lahir,
                    vtgl_lahir, vagama, vnama_ayah, vnama_ibu, valamat, vno_telepon};
                tblmahasiswa.addRow(data);
            }
            lblRecord.setText("Record : "+tbDataMahasiswa.getRowCount());
            } catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error Method cariMahasiswa : "+ ex);
            }
    }
    
    private void getDataMahasiswa(){
        try{
            Conn = null;
            Conn = getCnn.getConnection();
            sqlselect = "select * from tbmahasiswa a, tbprodi b, tbthakademik c"
                    + " where a.kd_prodi=b.kd_prodi and nim='"+vnim+"'"
                    + " order by a.kd_prodi, a.nama_mhs asc ";
            Statement stat = Conn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            if(res.first()){
                cmbTa.setSelectedItem(res.getString("ta"));
                cmbProdi.setSelectedItem(res.getString("nm_prodi"));
                txtNim.setText(res.getString("nim"));
                txtNamaMhs.setText(res.getString("nama_mhs"));
                vjns_kel = res.getString("jns_kel");
                if(vjns_kel.equals("Laki-laki")){
                    rbLaki.setSelected(true);
                }else{
                    rbPerempuan.setSelected(true);
                }
                txtTempatLahir.setText(res.getString("tmp_lahir"));
                dtTglLahir.setDate(res.getDate("tgl_lahir"));
                cmbAgama.setSelectedItem(res.getString("agama"));
                txtNamaAyah.setText(res.getString("nama_ayah"));
                txtNamaIbu.setText(res.getString("nama_ibu"));
                txtAlamat.setText(res.getString("alamat"));
                txtNoTelepon.setText(res.getString("nama_telepon"));
                lblTa.setText("*Input Kembali !");
               
            }
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(this, "Error mthod getDataMahasiswa : " + ex);
            }
    }
    
    private void aksiSimpan(){
        vkd_prodi = KeyProdi[cmbProdi.getSelectedIndex()];
        vta = KeyTa[cmbTa.getSelectedIndex()];
        vnim = txtNim.getText();
        vnim2 = txtNim.getText();
        vnama_mhs = txtNamaMhs.getText();
        if(rbLaki.isSelected()){
            vjns_kel = "L";
        }else {
            vjns_kel = "P";
        }
        vtmp_lahir = txtTempatLahir.getText();
        vtgl_lahir = tglinput.format(dtTglLahir.getDate());
        vagama = cmbAgama.getSelectedItem().toString();
        vnama_ayah = txtNamaAyah.getText();
        vnama_ibu = txtNamaIbu.getText();
        valamat = txtAlamat.getText();
        vno_telepon = txtNoTelepon.getText();
        if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into tbmahasiswa (nim, ta, kd_prodi, nama_mhs,"
                    + " jns_kel, tmp_lahir, tgl_lahir, agama, nama_ayah, nama_ibu, alamat, "
                    + " nama_telepon) values ('"+vnim+"', '"+vta+"', '"+vkd_prodi+"',"
                    + " '"+vnama_mhs+"', '"+vjns_kel+"', '"+vtmp_lahir+"', "
                    + " '"+vtgl_lahir+"', '"+vagama+"', '"+vnama_ayah+"', '"+vnama_ibu+"', "
                    + " '"+valamat+"', '"+vno_telepon+"') ";
        }else {
            sqlinsert = "update tbmahasiswa set nim = '"+vnim2+"', ta = '"+vta+"', kd_prodi = '"+vkd_prodi+"', "
                    + " nama_mhs = '"+vnama_mhs+"', jns_kel = '"+vjns_kel+"', "
                    + " tmp_lahir = '"+vtmp_lahir+"', tgl_lahir = '"+vtgl_lahir+"', "
                    + " agama = '"+vagama+"', nama_ayah='"+vnama_ayah+"', nama_ibu = '"+vnama_ibu+"', "
                    + " alamat = '"+valamat+"', no_telepon = '"+vno_telepon+"' "
                    + " where nim = '"+vnim2+"' ";
        }
        try{
            Conn = null;
            Conn = getCnn.getConnection();
            Statement state = Conn.createStatement();
            state.executeUpdate(sqlinsert);
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            clearForm(); showDataTabel();
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiSimpan " + ex);
        }
    }
    
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, 
                "Apakah anda yakin akan menghapus data ini ? NIM : " +vnim,
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(jawab==JOptionPane.YES_OPTION){
            try{
            Conn = null;
            Conn = getCnn.getConnection();
            sqldelete = "delete from tbmahasiswa where nim= '"+vnim+"'";
            java.sql.Statement state = Conn.createStatement();
            state.executeUpdate(sqldelete);
            JOptionPane.showMessageDialog(null, "Data Berhasil dihapus");
            clearForm(); showDataTabel();
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method aksiHapus " +ex);
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
    
    String[] KeyTa;
    private void listTa(){
        try{
            Conn = null;
            Conn = getCnn.getConnection();
            sqlselect = "select distinct(left(thakademik,4))"
                    + " from tbthakademik order by thakademik asc ";
            Statement stat = Conn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            cmbTa.removeAllItems();
            cmbTa.repaint();
            cmbTa.addItem("-- Pilih Tahun Ajaran --");
            int i = 1;
            while(res.next()){
                cmbTa.addItem(res.getString(1));
                i++;
            }
            res.first();
            KeyTa = new String[i+1];
            for(Integer x =1;x < i;x++){
                KeyTa[x] = res.getString(1);
                res.next();
            }
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(this, "Error Method listTa " +ex);
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

        jfInputMahasiswa = new javax.swing.JFrame();
        panelCool13 = new Tool.PanelCool1();
        jLabel3 = new javax.swing.JLabel();
        label1 = new Tool.Label();
        panelCool2 = new Tool.PanelCool();
        label2 = new Tool.Label();
        label3 = new Tool.Label();
        txtNim = new Tool.TextField();
        txtNamaMhs = new Tool.TextField();
        label4 = new Tool.Label();
        rbLaki = new javax.swing.JRadioButton();
        rbPerempuan = new javax.swing.JRadioButton();
        label5 = new Tool.Label();
        txtTempatLahir = new Tool.TextField();
        label6 = new Tool.Label();
        cmbAgama = new javax.swing.JComboBox<>();
        label7 = new Tool.Label();
        txtNamaAyah = new Tool.TextField();
        label8 = new Tool.Label();
        txtNamaIbu = new Tool.TextField();
        label9 = new Tool.Label();
        label10 = new Tool.Label();
        txtNoTelepon = new Tool.TextField();
        dtTglLahir = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        label11 = new Tool.Label();
        label12 = new Tool.Label();
        cmbTa = new javax.swing.JComboBox<>();
        cmbProdi = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        lblTa = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        panelCool12 = new Tool.PanelCool1();
        btnSimpan = new Tool.botton();
        btnBatal = new Tool.botton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jFrame1 = new javax.swing.JFrame();
        panelMhs = new Tool.PanelCool();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        panelCool11 = new Tool.PanelCool1();
        btnTambah = new Tool.botton();
        btnHapus = new Tool.botton();
        btnCetak = new Tool.botton();
        jLabel19 = new javax.swing.JLabel();
        txtCariMhs = new Tool.TextField();
        lblReload = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbDataMahasiswa = new javax.swing.JTable();
        lblRecord = new javax.swing.JLabel();

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/logo-medium.png"))); // NOI18N

        label1.setForeground(new java.awt.Color(255, 255, 255));
        label1.setText("Form Entry Mahasiswa");
        label1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        panelCool2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Input Mahasiswa"));

        label2.setForeground(new java.awt.Color(255, 255, 255));
        label2.setText("NIM");

        label3.setForeground(new java.awt.Color(255, 255, 255));
        label3.setText("Nama Mahasiswa");

        txtNim.setForeground(new java.awt.Color(255, 255, 255));
        txtNim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNimKeyPressed(evt);
            }
        });

        txtNamaMhs.setForeground(new java.awt.Color(255, 255, 255));
        txtNamaMhs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaMhsActionPerformed(evt);
            }
        });
        txtNamaMhs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaMhsKeyPressed(evt);
            }
        });

        label4.setForeground(new java.awt.Color(255, 255, 255));
        label4.setText("Jenis Kelamin");

        rbLaki.setBackground(new java.awt.Color(51, 153, 255));
        buttonGroup1.add(rbLaki);
        rbLaki.setForeground(new java.awt.Color(255, 255, 255));
        rbLaki.setText("Laki-laki");
        rbLaki.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbLakiKeyPressed(evt);
            }
        });

        rbPerempuan.setBackground(new java.awt.Color(51, 153, 255));
        buttonGroup1.add(rbPerempuan);
        rbPerempuan.setForeground(new java.awt.Color(255, 255, 255));
        rbPerempuan.setText("Perempuan");
        rbPerempuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbPerempuanKeyPressed(evt);
            }
        });

        label5.setForeground(new java.awt.Color(255, 255, 255));
        label5.setText("Tempat, Tanggal Lahir");

        txtTempatLahir.setForeground(new java.awt.Color(255, 255, 255));
        txtTempatLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTempatLahirKeyPressed(evt);
            }
        });

        label6.setForeground(new java.awt.Color(255, 255, 255));
        label6.setText("Agama");

        cmbAgama.setBackground(new java.awt.Color(0, 102, 255));
        cmbAgama.setForeground(new java.awt.Color(255, 255, 255));
        cmbAgama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Agama --", "Islam", "Kristen", "Katolik", "Hindu", "Budha" }));

        label7.setForeground(new java.awt.Color(255, 255, 255));
        label7.setText("Nama Ayah");

        txtNamaAyah.setForeground(new java.awt.Color(255, 255, 255));
        txtNamaAyah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaAyahKeyPressed(evt);
            }
        });

        label8.setForeground(new java.awt.Color(255, 255, 255));
        label8.setText("Nama Ibu");

        txtNamaIbu.setForeground(new java.awt.Color(255, 255, 255));
        txtNamaIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaIbuKeyPressed(evt);
            }
        });

        label9.setForeground(new java.awt.Color(255, 255, 255));
        label9.setText("Alamat");

        label10.setForeground(new java.awt.Color(255, 255, 255));
        label10.setText("No. Telepon/ Hp");

        txtNoTelepon.setForeground(new java.awt.Color(255, 255, 255));
        txtNoTelepon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNoTeleponKeyPressed(evt);
            }
        });

        dtTglLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dtTglLahirKeyPressed(evt);
            }
        });

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane1.setViewportView(txtAlamat);

        label11.setForeground(new java.awt.Color(255, 255, 255));
        label11.setText("Tahun Angkatan");

        label12.setForeground(new java.awt.Color(255, 255, 255));
        label12.setText("Program Studi");

        cmbTa.setBackground(new java.awt.Color(0, 102, 255));
        cmbTa.setForeground(new java.awt.Color(255, 255, 255));

        cmbProdi.setBackground(new java.awt.Color(0, 102, 255));
        cmbProdi.setForeground(new java.awt.Color(255, 255, 255));
        cmbProdi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        lblTa.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout panelCool2Layout = new javax.swing.GroupLayout(panelCool2);
        panelCool2.setLayout(panelCool2Layout);
        panelCool2Layout.setHorizontalGroup(
            panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelCool2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCool2Layout.createSequentialGroup()
                        .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNamaMhs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbAgama, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNamaAyah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNamaIbu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                            .addComponent(txtNoTelepon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbProdi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(panelCool2Layout.createSequentialGroup()
                                .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelCool2Layout.createSequentialGroup()
                                        .addComponent(rbLaki)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rbPerempuan))
                                    .addGroup(panelCool2Layout.createSequentialGroup()
                                        .addComponent(cmbTa, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblTa)))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(panelCool2Layout.createSequentialGroup()
                        .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTempatLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dtTglLahir, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCool2Layout.setVerticalGroup(
            panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbTa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbProdi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNim, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNamaMhs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbLaki)
                    .addComponent(rbPerempuan))
                .addGap(4, 4, 4)
                .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTempatLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dtTglLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbAgama, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCool2Layout.createSequentialGroup()
                        .addComponent(txtNamaAyah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelCool2Layout.createSequentialGroup()
                                .addComponent(txtNamaIbu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelCool2Layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelCool2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtNoTelepon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Form ini digunakan untuk memasukan data mahasiswa !");

        panelCool12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)), "Navigasi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        btnSimpan.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/save_blue.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setForeground(new java.awt.Color(255, 255, 255));
        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/refresh.png"))); // NOI18N
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCool12Layout = new javax.swing.GroupLayout(panelCool12);
        panelCool12.setLayout(panelCool12Layout);
        panelCool12Layout.setHorizontalGroup(
            panelCool12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        panelCool12Layout.setVerticalGroup(
            panelCool12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCool12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelCool13Layout = new javax.swing.GroupLayout(panelCool13);
        panelCool13.setLayout(panelCool13Layout);
        panelCool13Layout.setHorizontalGroup(
            panelCool13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCool13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCool2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelCool12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelCool13Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelCool13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelCool13Layout.setVerticalGroup(
            panelCool13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool13Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(panelCool13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(panelCool13Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelCool2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelCool12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jfInputMahasiswaLayout = new javax.swing.GroupLayout(jfInputMahasiswa.getContentPane());
        jfInputMahasiswa.getContentPane().setLayout(jfInputMahasiswaLayout);
        jfInputMahasiswaLayout.setHorizontalGroup(
            jfInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCool13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jfInputMahasiswaLayout.setVerticalGroup(
            jfInputMahasiswaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCool13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("AKN-Data Mahasiswa");

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/logo-medium.png"))); // NOI18N

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("DATA MAHASISWA");

        panelCool11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Navigasi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/insert.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
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

        btnCetak.setForeground(new java.awt.Color(255, 255, 255));
        btnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/printer_on.png"))); // NOI18N
        btnCetak.setText("Cetak");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Cari Nama Mahasiswa : ");

        txtCariMhs.setForeground(new java.awt.Color(255, 255, 255));
        txtCariMhs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariMhsKeyTyped(evt);
            }
        });

        lblReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/update24.png"))); // NOI18N
        lblReload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblReloadMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelCool11Layout = new javax.swing.GroupLayout(panelCool11);
        panelCool11.setLayout(panelCool11Layout);
        panelCool11Layout.setHorizontalGroup(
            panelCool11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblReload)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 316, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCariMhs, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelCool11Layout.setVerticalGroup(
            panelCool11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCool11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCool11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(txtCariMhs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblReload))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Data Mahasiswa"));

        tbDataMahasiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbDataMahasiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataMahasiswaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbDataMahasiswaMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tbDataMahasiswa);

        lblRecord.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblRecord.setText("Record : 0");

        javax.swing.GroupLayout panelMhsLayout = new javax.swing.GroupLayout(panelMhs);
        panelMhs.setLayout(panelMhsLayout);
        panelMhsLayout.setHorizontalGroup(
            panelMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMhsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCool11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelMhsLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMhsLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblRecord)))
                .addContainerGap())
        );
        panelMhsLayout.setVerticalGroup(
            panelMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMhsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelMhsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMhsLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(26, 26, 26))
                    .addGroup(panelMhsLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(panelCool11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRecord)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMhs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMhs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNimKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNimKeyPressed

    private void txtNamaMhsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaMhsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaMhsKeyPressed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if(txtNim.getText().equals("")){
            JOptionPane.showMessageDialog(this, "NIM harus diisi !",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(cmbProdi.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Jurusan belum dipilih !",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(cmbTa.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Tahun Ajaran belum dipilih !",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtNamaMhs.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nama Mahasiswa harus diisi !",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(buttonGroup1.isSelected(null)){
            JOptionPane.showMessageDialog(this, "Jenis Kelamin belum dipilih !",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtTempatLahir.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Tempat lahir harus diisi !",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(dtTglLahir.getDate().equals(null)){
            JOptionPane.showMessageDialog(this, "Tanggal lahir harus diisi !",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtNamaAyah.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nama Ayah harus diisi !",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtNamaIbu.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Nama Ibu harus diisi !",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtAlamat.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Alamat harus diisi !",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else if(txtNoTelepon.getText().equals("")){
            JOptionPane.showMessageDialog(this, "No Telepon harus diisi !",
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiSimpan(); btnSimpan.setText("Simpan");
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void txtTempatLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTempatLahirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTempatLahirKeyPressed

    private void txtNamaAyahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaAyahKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaAyahKeyPressed

    private void txtNamaIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaIbuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaIbuKeyPressed

    private void txtNoTeleponKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNoTeleponKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoTeleponKeyPressed

    private void txtNamaMhsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaMhsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaMhsActionPerformed

    private void rbLakiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbLakiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbLakiKeyPressed

    private void rbPerempuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbPerempuanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbPerempuanKeyPressed

    private void dtTglLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dtTglLahirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_dtTglLahirKeyPressed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        clearForm();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if(vnim.equals("")){
            JOptionPane.showMessageDialog(this, "Belum ada data yang dipilih !", 
                    "Informasi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiHapus();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        jfInputMahasiswa.setVisible(true);
        jfInputMahasiswa.setSize(500, 750);
        jfInputMahasiswa.setLocationRelativeTo(this);
        formTengah();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        Laporan internalLap;
        
        panelMhs.removeAll();
        panelMhs.repaint();
        internalLap = new Laporan();
        internalLap.setVisible(true);
        try{
            internalLap.setMaximum(true);
        }catch(Exception e) {
            
        }
            panelMhs.add(internalLap);
    }//GEN-LAST:event_btnCetakActionPerformed

    private void txtCariMhsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariMhsKeyTyped
        cariMahasiswa();
    }//GEN-LAST:event_txtCariMhsKeyTyped

    private void tbDataMahasiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataMahasiswaMouseClicked
        if(evt.getClickCount()==1){
            vnim = tbDataMahasiswa.getValueAt(tbDataMahasiswa.getSelectedRow(), 1).toString();
            btnHapus.setEnabled(true);
            btnTambah.setText("Ubah");
            getDataMahasiswa();
            btnSimpan.setText("Ubah");
       }
    }//GEN-LAST:event_tbDataMahasiswaMouseClicked

    private void tbDataMahasiswaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataMahasiswaMousePressed

    }//GEN-LAST:event_tbDataMahasiswaMousePressed

    private void lblReloadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReloadMouseClicked
        showDataTabel();
    }//GEN-LAST:event_lblReloadMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Tool.botton btnBatal;
    private Tool.botton btnCetak;
    private Tool.botton btnHapus;
    private Tool.botton btnSimpan;
    private Tool.botton btnTambah;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbAgama;
    private javax.swing.JComboBox<String> cmbProdi;
    private javax.swing.JComboBox<String> cmbTa;
    private com.toedter.calendar.JDateChooser dtTglLahir;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JFrame jfInputMahasiswa;
    private Tool.Label label1;
    private Tool.Label label10;
    private Tool.Label label11;
    private Tool.Label label12;
    private Tool.Label label2;
    private Tool.Label label3;
    private Tool.Label label4;
    private Tool.Label label5;
    private Tool.Label label6;
    private Tool.Label label7;
    private Tool.Label label8;
    private Tool.Label label9;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JLabel lblReload;
    private javax.swing.JLabel lblTa;
    private Tool.PanelCool1 panelCool11;
    private Tool.PanelCool1 panelCool12;
    private Tool.PanelCool1 panelCool13;
    private Tool.PanelCool panelCool2;
    private Tool.PanelCool panelMhs;
    private javax.swing.JRadioButton rbLaki;
    private javax.swing.JRadioButton rbPerempuan;
    private javax.swing.JTable tbDataMahasiswa;
    private javax.swing.JTextArea txtAlamat;
    private Tool.TextField txtCariMhs;
    private Tool.TextField txtNamaAyah;
    private Tool.TextField txtNamaIbu;
    private Tool.TextField txtNamaMhs;
    private Tool.TextField txtNim;
    private Tool.TextField txtNoTelepon;
    private Tool.TextField txtTempatLahir;
    // End of variables declaration//GEN-END:variables
}
