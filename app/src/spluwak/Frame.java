/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spluwak;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//import com.mysql.jdbc.Statement;
import java.awt.HeadlessException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import spluwak.Konek;
import java.sql.*;
import javax.swing.JDialog;
/**
 *
 * @author pikri
 */
public class Frame extends javax.swing.JFrame {
DefaultTableModel model;
String data[]=new String[4];
Object data2[]=new Object[4];

Fungsi fs = new Fungsi();
    /**
     * Creates new form home
     */
    public Frame() {
        initComponents();
        datatable();
        datatable2();
        datatable3("all","");
        datatable4();
        /*code tabel1*/
        Clear_Tmp();
    }
    
    public void datatable() {
    
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("ID");
        tbl.addColumn("Gejala");
        tabelgejala.setModel(tbl);
        tabelgejala.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabelgejala.getColumnModel().getColumn(1).setPreferredWidth(400);
        try {
            Statement statement = (Statement) Konek.getConnection().createStatement();
            ResultSet res = statement.executeQuery("Select * from gejala");
            while (res.next())
            {
                tbl.addRow(new Object []{
                    res.getString("id_gejala"),
                    res.getString("nama_gejala")
                });
                tabelgejala.setModel(tbl);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Gagal");
        }
    }
    
    public void datatable2() {
        DefaultTableModel tbl2 = new DefaultTableModel();
        tbl2.addColumn("ID");
        tbl2.addColumn("Penyakit");
        tbl2.addColumn("Solusi");
        tabelpenyakit.setModel(tbl2);
        tabelpenyakit.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabelpenyakit.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabelpenyakit.getColumnModel().getColumn(2).setPreferredWidth(200);
        try {
            Statement statement = (Statement) Konek.getConnection().createStatement();
            ResultSet res = statement.executeQuery("Select * from penyakit");
            while (res.next())
            {
                tbl2.addRow(new Object []{
                    res.getString("id_penyakit"),
                    res.getString("nama_penyakit"),
                    res.getString("solusi")
                });
                tabelpenyakit.setModel(tbl2);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Gagal");
        }
    }
    
    public void datatable3(String sts,String val) {
        final DefaultTableModel tbl = new DefaultTableModel(){
            public Class<?> getColumnClass(int column){
                switch(column){
                    case 0:
                        return Boolean.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
        tbl.addColumn("Pilih");
        tbl.addColumn("Gejala");
        tbl.addColumn("id_gejala");
        tabeldiagnosa.setModel(tbl);
        tabeldiagnosa.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabeldiagnosa.getColumnModel().getColumn(1).setPreferredWidth(350);
        
        tabeldiagnosa.removeColumn(tabeldiagnosa.getColumnModel().getColumn(2));
        try {
            if(sts.equals("all")){
            //Statement statement = (Statement) Konek.getConnection().createStatement();
            Connection kon= new Konek().getConnection();
            Statement statement= kon.createStatement();
            
            ResultSet res = statement.executeQuery("Select * from gejala");
            while (res.next())
            {
                
//                tbl.addRow(new Object []{
//                    res.getBoolean(1),
//                    //res.getBoolean(2),/*real*/
//                    res.getString("nama_gejala"),
//                    res.getString("id_gejala")
//                });
                
                data2[0]= res.getBoolean(1);
                data2[1]=res.getString("nama_gejala");//+" "+res.getString("id_gejala");
                data2[2]=res.getString("id_gejala");
                tbl.addRow(data2);       
            }
                kon.close();
                statement.close();
                res.close();
                
                tabeldiagnosa.setModel(tbl);
            }
            else if(sts.equals("fil")){
            Connection kon= new Konek().getConnection();
            Statement statement= kon.createStatement();
            
            ResultSet res_ck = statement.executeQuery("Select id_gejala from tmp_analisa where id_gejala not in(select id_gejala from tmp_gejala)");
            if(res_ck.next()){
            ResultSet res = statement.executeQuery("Select id_gejala from tmp_analisa where id_gejala not in(select id_gejala from tmp_gejala) group by id_gejala limit 1");
            while (res.next())
            {
//                tbl.addRow(new Object []{
//                    res.getBoolean(1),
//                    //res.getBoolean(2),/*real*/
//                    res.getString("id_gejala"),
//                    res.getString("id_gejala")
//                });
                
                data2[0]= res.getBoolean(1);
                data2[1]=fs.kol_gejala("nama_gejala", "id_gejala", res.getString("id_gejala"));//+" "+res.getString("id_gejala");
                data2[2]=res.getString("id_gejala");
                tbl.addRow(data2);     
            }
                kon.close();
                statement.close();
                res.close();
                
            }else{
                String jml_row_p = fs.jml_row_penyakit(fs.has_penyakit());
                String jml_has_row_p = fs.jml_row_has_penyakit();
                
//                JOptionPane.showMessageDialog(rootPane, "Penyakit Total : "+jml_row_p,"s",JOptionPane.OK_OPTION);
//                JOptionPane.showMessageDialog(rootPane, "Penyakit Hasil : "+jml_has_row_p,"s",JOptionPane.OK_OPTION);
                
                if (jml_row_p.equals(jml_has_row_p)){
//                JOptionPane.showMessageDialog(rootPane, "Penyakit : "+fs.has_penyakit(),"s",JOptionPane.OK_OPTION);
                //pane show hasil diagnosa
                JOptionPane.showMessageDialog(rootPane, //"Kode : "+fs.has_penyakit()+"\n"
                        /*+*/ "Penyakit : "+fs.kol_penyakit("nama_penyakit", "id_penyakit", fs.has_penyakit())+"\n"
                        + "Solusi : "+fs.kol_penyakit("solusi", "id_penyakit", fs.has_penyakit()));
                }else{
                JOptionPane.showMessageDialog(rootPane, "Data Penyakit tidak ditemukan","Hasil",JOptionPane.OK_OPTION);
                }
                
                //Clear_Tmp();
                
            }
            
                
                tabeldiagnosa.setModel(tbl);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Gagal terhubung");
        }
        tabeldiagnosa.getModel().addTableModelListener(new TableModelListener(){
            @Override
            public void tableChanged(TableModelEvent e){
                if(tabeldiagnosa.getValueAt(tabeldiagnosa.getSelectedRow(), e.getColumn()).equals(Boolean.TRUE)){
                    //JOptionPane.showMessageDialog(null, tabeldiagnosa.getModel().getValueAt(tabeldiagnosa.getSelectedRow(), 2));
                    //System.out.println(fs.kol_gejala("nama_gejala", "id_gejala", tabeldiagnosa.getModel().getValueAt(tabeldiagnosa.getSelectedRow(), 2).toString()));
                    String idg = (String) tabeldiagnosa.getModel().getValueAt(tabeldiagnosa.getSelectedRow(), 2);
                    //System.out.println(idg);
                    //tabeldiagnosa.remove(0);
                    
                    //mencari jumlah row dalam tabel
                    String jml_ta = fs.jml_row_tabel("tmp_analisa");
                    String jml_tg = fs.jml_row_tabel("tmp_gejala");
                    
                    String tanya =fs.kol_gejala("nama_gejala", "id_gejala", tabeldiagnosa.getModel().getValueAt(tabeldiagnosa.getSelectedRow(), 2).toString());
                    
    int reply = JOptionPane.showConfirmDialog(null, tanya +"? "/*+ idg*/, "Gejala", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
                    if(jml_tg.equals("0")){
                    //input pada tabel temporer
                    In_analisa("awal",idg);
                    In_gejala(idg,"y");
                    }else{
                        
                    //Clear_Analisa();
                    In_gejala(idg,"y");
                    
                    //In_analisa("pilih", "");
                    
                    }
                     //filter berdasarkan gejala yang dipilih
                    datatable3("fil","");
        }
        else {
                    if(jml_tg.equals("0")){
                        
                    //input pada tabel temporer
                    //In_analisa("awal",idg);
                    //In_gejala(idg,"n");
                        
                     datatable3("all","");   
                    }else{
                        
                    //Clear_Analisa();
                    In_gejala(idg,"n");
                    
                    //In_analisa("pilih", "");
                    
                     //filter berdasarkan gejala yang dipilih
                    datatable3("fil","");
                    }
           //System.exit(0);
        }

                    
                   
                    

                    
                    
//                try {
//                    Statement statement = (Statement) Konek.getConnection().createStatement();
//                    ResultSet res = statement.executeQuery("Select * from gejala");
//                    while (res.next())
//                    {
//                        tbl.addRow(new Object []{
//                            res.getBoolean(1),
//                            res.getString("nama_gejala"),
//                            res.getString("id_gejala")
//                        });
//                        tabeldiagnosa.setModel(tbl);
//                    }
//                } catch (Exception r) {
//                    //JOptionPane.showMessageDialog(rootPane, "Gagal terhubung");
//                }
                }
            }
        });
    }
    
    public void datatable4() {
        DefaultTableModel tbl3 = new DefaultTableModel();
        tbl3.addColumn("id relasi");
        tbl3.addColumn("id gejala");
        tbl3.addColumn("id penyakit");
        tabelrelasi.setModel(tbl3);
        try {
            //Statement statement = (Statement) Konek.getConnection().createStatement();
            Connection kon= new Konek().getConnection();
            Statement statement= kon.createStatement();
            
            ResultSet res = statement.executeQuery("Select * from relasi");
            while (res.next())
            {
                tbl3.addRow(new Object []{
                    res.getString("id_relasi"),
                    res.getString("id_gejala"),
                    res.getString("id_penyakit"),
                });
                tabelrelasi.setModel(tbl3);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Gagal");
        }
    }
    
    public void In_gejala(String idg, String spil){
        try{
             //Statement statement = (Statement)Konek.getConnection().createStatement();
            Connection kon= new Konek().getConnection();
            Statement statement= kon.createStatement();
            
             statement.executeUpdate("insert into tmp_gejala(id_gejala,pil) values ('"+ idg +"','"+spil+"');");
                kon.close();
                statement.close();
                
        }catch(SQLException s){
            JOptionPane.showMessageDialog(rootPane, s);
        }
    }
    
    public void In_analisa(String sts, String idg){
         try {

             
             
            Connection kon= new Konek().getConnection();
            Statement stt= kon.createStatement();
            
            
            String sql=null;
            if(sts.equals("awal")){
                sql="Select * from relasi where id_gejala='"+idg+"'";
            }else if(sts.equals("pilih")){
                sql="Select * from relasi where id_gejala in (select id_gejala from tmp_gejala) group by id_gejala";
            }
            ResultSet res2 = stt.executeQuery(sql);
            while (res2.next()){
                      data[0]=res2.getString(3);
                       
            System.out.println("Penyakit ditemukan: " + data[0]);
            findArrayValues(data);
            
//            ResultSet res3 = stt.executeQuery("Select * from relasi where id_penyakit='"+pny+"'");
//            while (res3.next()){
//                String kdg = data2[0]=res3.getString(2);
//                data3[0]=res3.getString(3);
//                       insertArrayValues(kdg,data3); 
//                    }
            
                    }
                kon.close();
                stt.close();
                res2.close();
            
                } catch (Exception r) {
                    //JOptionPane.showMessageDialog(rootPane, "Gagal terhubung");
                }
    
    }
    
    private  void findArrayValues(String[] anArray) {
      try {
            Connection kon= new Konek().getConnection();
            Statement stt=kon.createStatement();
            
            int arrayLength = anArray.length;
            for (int i = 0; i <= arrayLength - 1; i++) {
                String value = anArray[i];
                System.out.println("Penyakit Dicari: " + value);
            ResultSet res3 = stt.executeQuery("Select * from relasi where id_penyakit='"+value+"'");
            while (res3.next()){
                String kdg =res3.getString(2);
                data[0] =res3.getString(3);
                       insertArrayValues(kdg,data); 
                    }
            }
                kon.close();
                stt.close();
                //res3.close();
           } catch (Exception r) {
                    //JOptionPane.showMessageDialog(rootPane, "Gagal terhubung");
        }
   }
    
    private static void insertArrayValues(String g, String[] anArray) {
      try {
            Connection kon= new Konek().getConnection();
            Statement stt=kon.createStatement();
            
            int arrayLength = anArray.length;
            for (int i = 0; i <= arrayLength - 1; i++) {
                String value = anArray[i];
                System.out.println("The array contains the value: " + value);
                stt.executeUpdate("insert into tmp_analisa(id_gejala,id_penyakit) VALUES ('"+g+"','"+value+"')");
            }
                kon.close();
                stt.close();
           } catch (Exception r) {
                    //JOptionPane.showMessageDialog(rootPane, "Gagal terhubung");
        }
   }
    
    private void Clear_Tmp() {
        try {
              Connection kon= new Konek().getConnection();
            Statement stt=kon.createStatement();
            stt.executeUpdate("TRUNCATE tmp_analisa");
            stt.executeUpdate("TRUNCATE tmp_gejala");
             } catch (SQLException r) {
                   JOptionPane.showMessageDialog(rootPane, r);
             }
    }
    
    private void Clear_Analisa() {
        try {
              Connection kon= new Konek().getConnection();
            Statement stt=kon.createStatement();
            stt.executeUpdate("TRUNCATE tmp_analisa");
             } catch (SQLException r) {
                   JOptionPane.showMessageDialog(rootPane, r);
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

        jDialog1 = new javax.swing.JDialog();
        jLabel19 = new javax.swing.JLabel();
        lblkode = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lblnmp = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblsolusi = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Home = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        picluwak1 = new javax.swing.JLabel();
        Login = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        CRUD = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        Gejala = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelgejala = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        idgejala = new javax.swing.JTextField();
        gejalat = new javax.swing.JTextField();
        btambahgejala = new javax.swing.JButton();
        bubahgejala = new javax.swing.JButton();
        bhapusgejala = new javax.swing.JButton();
        brefreshgejala = new javax.swing.JButton();
        Penyakit = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        idpenyakit = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        penyakit = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        solusit = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelpenyakit = new javax.swing.JTable();
        btambahpenyakit = new javax.swing.JButton();
        bubahpenyakit = new javax.swing.JButton();
        brefreshpenyakit = new javax.swing.JButton();
        bhapuspenyakit = new javax.swing.JButton();
        relasi = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelrelasi = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        Fgejala = new javax.swing.JTextField();
        Fpenyakit = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        Frelasi = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        pDiagnosa1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabeldiagnosa = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        pTentangLuwak = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton16 = new javax.swing.JButton();
        pTentangApp = new javax.swing.JPanel();
        jButton18 = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();

        jDialog1.setTitle("Penyakit");
        jDialog1.setAlwaysOnTop(true);

        jLabel19.setText("Kode :");

        lblkode.setText("lblkode");

        jLabel20.setText("Nama Penyakit :");

        lblnmp.setText("lblnmp");

        jLabel21.setText("Solusi :");

        lblsolusi.setText("lblsolusi");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblkode))
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblnmp))
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblsolusi)))
                .addContainerGap(272, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(lblkode))
                .addGap(18, 18, 18)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(lblnmp))
                .addGap(18, 18, 18)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(lblsolusi))
                .addContainerGap(183, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setLayout(new java.awt.CardLayout());

        jButton3.setText("Tentang");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Tentang Luwak");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton1.setText("Mulai Diagnosa");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 15)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SISTEM PAKAR DIAGNOSA PENYAKIT LUWAK");

        jButton2.setText("Admin");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        picluwak1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        picluwak1.setIcon(new javax.swing.ImageIcon("C:\\Users\\pikri\\OneDrive\\Documents\\!!!!SKIPSI!!!!\\source\\luwak_pic__1538305936_85078.png")); // NOI18N

        javax.swing.GroupLayout HomeLayout = new javax.swing.GroupLayout(Home);
        Home.setLayout(HomeLayout);
        HomeLayout.setHorizontalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeLayout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(HomeLayout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(67, Short.MAX_VALUE))
            .addGroup(HomeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addComponent(picluwak1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        HomeLayout.setVerticalGroup(
            HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HomeLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(picluwak1)
                .addGap(17, 17, 17)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(HomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4)
                    .addComponent(jButton3))
                .addGap(31, 31, 31))
        );

        jPanel1.add(Home, "card2");

        jLabel3.setText("Password");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Login Admin");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });

        jButton5.setText("Masuk");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Kembali");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel4.setText("Username");

        javax.swing.GroupLayout LoginLayout = new javax.swing.GroupLayout(Login);
        Login.setLayout(LoginLayout);
        LoginLayout.setHorizontalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(LoginLayout.createSequentialGroup()
                            .addComponent(jButton5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginLayout.createSequentialGroup()
                            .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginLayout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(47, 47, 47))
                                .addGroup(LoginLayout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(49, 49, 49)))
                            .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPasswordField1)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addGap(61, 61, 61)))
                .addContainerGap(119, Short.MAX_VALUE))
        );
        LoginLayout.setVerticalGroup(
            LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LoginLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel2)
                .addGap(56, 56, 56)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(43, 43, 43)
                .addGroup(LoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jPanel1.add(Login, "card3");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Ubah Database");

        jButton7.setText("Gejala");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Penyakit & Solusi");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton17.setText("Kembali");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jPanel3.setLayout(new java.awt.CardLayout());

        tabelgejala.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Gejala"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelgejala);
        if (tabelgejala.getColumnModel().getColumnCount() > 0) {
            tabelgejala.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabelgejala.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jLabel5.setText("ID :");

        jLabel7.setText("Gejala :");

        idgejala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idgejalaActionPerformed(evt);
            }
        });

        btambahgejala.setText("Tambah");
        btambahgejala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btambahgejalaActionPerformed(evt);
            }
        });

        bubahgejala.setText("Ubah");
        bubahgejala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bubahgejalaActionPerformed(evt);
            }
        });

        bhapusgejala.setText("Hapus");
        bhapusgejala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapusgejalaActionPerformed(evt);
            }
        });

        brefreshgejala.setText("Refresh");
        brefreshgejala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brefreshgejalaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout GejalaLayout = new javax.swing.GroupLayout(Gejala);
        Gejala.setLayout(GejalaLayout);
        GejalaLayout.setHorizontalGroup(
            GejalaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(GejalaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(GejalaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(GejalaLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(idgejala, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gejalat))
                    .addGroup(GejalaLayout.createSequentialGroup()
                        .addComponent(btambahgejala)
                        .addGap(45, 45, 45)
                        .addComponent(bubahgejala)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(bhapusgejala)
                        .addGap(41, 41, 41)
                        .addComponent(brefreshgejala)))
                .addContainerGap())
        );
        GejalaLayout.setVerticalGroup(
            GejalaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, GejalaLayout.createSequentialGroup()
                .addGroup(GejalaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(idgejala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gejalat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(GejalaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btambahgejala)
                    .addComponent(bubahgejala)
                    .addComponent(bhapusgejala)
                    .addComponent(brefreshgejala))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(Gejala, "card2");

        jLabel8.setText("ID :");

        jLabel9.setText("Penyakit :");

        jLabel10.setText("Solusi :");

        tabelpenyakit.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Penyakit", "Solusi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabelpenyakit);
        if (tabelpenyakit.getColumnModel().getColumnCount() > 0) {
            tabelpenyakit.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabelpenyakit.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        btambahpenyakit.setText("Tambah");
        btambahpenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btambahpenyakitActionPerformed(evt);
            }
        });

        bubahpenyakit.setText("Ubah");
        bubahpenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bubahpenyakitActionPerformed(evt);
            }
        });

        brefreshpenyakit.setText("Refresh");
        brefreshpenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brefreshpenyakitActionPerformed(evt);
            }
        });

        bhapuspenyakit.setText("Hapus");
        bhapuspenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapuspenyakitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PenyakitLayout = new javax.swing.GroupLayout(Penyakit);
        Penyakit.setLayout(PenyakitLayout);
        PenyakitLayout.setHorizontalGroup(
            PenyakitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PenyakitLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PenyakitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(PenyakitLayout.createSequentialGroup()
                        .addGroup(PenyakitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PenyakitLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(idpenyakit, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9))
                            .addComponent(btambahpenyakit))
                        .addGroup(PenyakitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PenyakitLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(penyakit)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(solusit, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PenyakitLayout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(bubahpenyakit)
                                .addGap(44, 44, 44)
                                .addComponent(bhapuspenyakit)
                                .addGap(43, 43, 43)
                                .addComponent(brefreshpenyakit)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        PenyakitLayout.setVerticalGroup(
            PenyakitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PenyakitLayout.createSequentialGroup()
                .addGroup(PenyakitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(idpenyakit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(penyakit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(solusit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PenyakitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btambahpenyakit)
                    .addComponent(bubahpenyakit)
                    .addComponent(brefreshpenyakit)
                    .addComponent(bhapuspenyakit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(Penyakit, "card3");

        tabelrelasi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tabelrelasi);

        jLabel12.setText("id penyakit :");

        jLabel14.setText("id gejala :");

        jButton9.setText("Tambah");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton12.setText("Ubah");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("Hapus");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setText("Refresh");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jLabel15.setText("id relasi :");

        Frelasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FrelasiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout relasiLayout = new javax.swing.GroupLayout(relasi);
        relasi.setLayout(relasiLayout);
        relasiLayout.setHorizontalGroup(
            relasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(relasiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(relasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, relasiLayout.createSequentialGroup()
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(jButton12)
                        .addGap(38, 38, 38)
                        .addComponent(jButton13)
                        .addGap(47, 47, 47)
                        .addComponent(jButton14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, relasiLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Frelasi)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Fgejala, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Fpenyakit, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        relasiLayout.setVerticalGroup(
            relasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, relasiLayout.createSequentialGroup()
                .addGroup(relasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(Fgejala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Fpenyakit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(Frelasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(relasiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton12)
                    .addComponent(jButton13)
                    .addComponent(jButton14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(relasi, "card8");

        jButton15.setText("Relasi");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CRUDLayout = new javax.swing.GroupLayout(CRUD);
        CRUD.setLayout(CRUDLayout);
        CRUDLayout.setHorizontalGroup(
            CRUDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(CRUDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton17)
                .addContainerGap())
            .addGroup(CRUDLayout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        CRUDLayout.setVerticalGroup(
            CRUDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CRUDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(CRUDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton17)
                    .addComponent(jButton15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(CRUD, "card4");

        tabeldiagnosa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Pilih", "Gejala"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabeldiagnosa);
        if (tabeldiagnosa.getColumnModel().getColumnCount() > 0) {
            tabeldiagnosa.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabeldiagnosa.getColumnModel().getColumn(0).setMaxWidth(40);
        }

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Diagnosa");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Silakan pilih gejala yang diderita luwak");

        jButton10.setText("Kembali");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Refresh");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pDiagnosa1Layout = new javax.swing.GroupLayout(pDiagnosa1);
        pDiagnosa1.setLayout(pDiagnosa1Layout);
        pDiagnosa1Layout.setHorizontalGroup(
            pDiagnosa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDiagnosa1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pDiagnosa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addGroup(pDiagnosa1Layout.createSequentialGroup()
                        .addComponent(jButton11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton10))
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pDiagnosa1Layout.setVerticalGroup(
            pDiagnosa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pDiagnosa1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pDiagnosa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11)
                    .addComponent(jButton10))
                .addContainerGap())
        );

        jPanel1.add(pDiagnosa1, "card5");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Tentang Luwak");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setText("          Luwak adalah hewan menyusu (mamalia) yang termasuk suku\n musang dan garangan (Viverridae). Nama ilmiahnya adalah\n Paradoxurushermaphroditus dan di Malaysia dikenal sebagai musang\n pulut. \n\n          Hewan ini juga dipanggil dengan berbagai sebutan lain seperti\n musang (nama umum, Betawi), careuh bulan (Sunda), luak atau\n luwak (Jawa), serta common palm civet, common musang, house\n musang atau toddy cat dalam bahasa Inggris.\n\n Ciri-ciri :\n\n          Musang bertubuh sedang, dengan panjang total sekitar 90\n cm (termasuk ekor, sekitar 40 cm atau kurang). Abu-abu kecoklatan\n dengan ekor hitam-coklat mulus.\n\n          Sisi atas tubuh abu-abu kecoklatan, dengan variasi dari warna\n tengguli (coklat merah tua) sampai kehijauan. Jalur di punggung\n lebih gelap, biasanya berupa tiga atau lima garis gelap yang tidak\n begitu jelas dan terputus-putus, atau membentuk deretan\n bintik-bintik besar. Sisi samping dan bagian perut lebih pucat.\n Terdapat beberapa bintik samar di sebelah tubuhnya.\n\n          Wajah, kaki dan ekor coklat gelap sampai hitam. Dahi dan sisi\n samping wajah hingga di bawah telinga berwarna keputih-putihan,\n seperti beruban. Satu garis hitam samar-samar lewat di tengah dahi,\n dari arah hidung ke atas kepala.\n\n          Posisi kelamin musang betina dekat dengan anus dan memiliki\n tiga pasang puting susu, sedangkan posisi kelamin musang jantan\n dekat dengan pusar.\n\n Kebiasaan :\n\n          Musang luwak adalah salah satu jenis mamalia liar yang kerap\n ditemui di sekitar pemukiman dan bahkan perkotaan. Hewan ini\n amat pandai memanjat dan bersifat arboreal, lebih kerap berkeliaran\n di atas pepohonan, meskipun tidak segan pula untuk turun ke\n tanah. Luwak juga bersifat nokturnal, aktif di malam hari untuk\n mencari makanan dan aktivitas lainnya.\n\n          Di alam liar, luwak kerap dijumpai di atas pohon aren atau\n pohon kawung, rumpun bambu, dan pohon kelapa, jika di\n perkotaan biasanya Luwak bersarang di atap rumah warga, karena\n habitat alaminya sudah terganti oleh rumah-rumah manusia.\n\n          Dalam gelap malam tidak jarang luwak luwak terlihat berjalan\n di atas atap rumah, meniti kabel listrik untuk berpindah dari satu\n bangunan ke lain bangunan, atau bahkan juga turun ke tanah di\n dekat dapur rumah. Luwak luwak juga menyukai hutan-hutan\n sekunder.\n");
        jScrollPane5.setViewportView(jTextArea1);

        jButton16.setText("Kembali");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pTentangLuwakLayout = new javax.swing.GroupLayout(pTentangLuwak);
        pTentangLuwak.setLayout(pTentangLuwakLayout);
        pTentangLuwakLayout.setHorizontalGroup(
            pTentangLuwakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTentangLuwakLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pTentangLuwakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pTentangLuwakLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton16)))
                .addContainerGap())
        );
        pTentangLuwakLayout.setVerticalGroup(
            pTentangLuwakLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTentangLuwakLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton16)
                .addContainerGap())
        );

        jPanel1.add(pTentangLuwak, "card6");

        pTentangApp.setPreferredSize(new java.awt.Dimension(421, 354));

        jButton18.setText("Kembali");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Selamat Datang di Aplikasi Sistem Pakar ");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Diagnosa Penyakit Luwak");

        jTextArea2.setEditable(false);
        jTextArea2.setBackground(java.awt.SystemColor.control);
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setText("\n  Sistem pakar diagnosa penyakit luwak ini merupakan sebuah aplikasi\n  berbasis dekstop yang digunakan untuk mendiagnosa penyakit dan\n  memberikan solusi untuk luwak yang menderita.\n\n\n  Selain itu aplikasi ini dibuat sebagai persyaratan kelulusan pendidikan S1\n  Sistem Informasi Universitas Amikom Yogyakarta\n\n\n  Dibuat oleh:\n  Pikri Taufan Aziz\n  14.12.8108");
        jTextArea2.setBorder(null);
        jTextArea2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane6.setViewportView(jTextArea2);

        javax.swing.GroupLayout pTentangAppLayout = new javax.swing.GroupLayout(pTentangApp);
        pTentangApp.setLayout(pTentangAppLayout);
        pTentangAppLayout.setHorizontalGroup(
            pTentangAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pTentangAppLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pTentangAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6)
                    .addGroup(pTentangAppLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton18))
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE))
                .addContainerGap())
        );
        pTentangAppLayout.setVerticalGroup(
            pTentangAppLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pTentangAppLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton18)
                .addContainerGap())
        );

        jPanel1.add(pTentangApp, "card7");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 421, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 354, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, "card8");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel4, "card9");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5, "card10");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel6, "card11");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel7, "card12");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel8, "card13");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Home.setVisible(false);
        Login.setVisible(false);
        CRUD.setVisible(false);
        pDiagnosa1.setVisible(true);
        pTentangLuwak.setVisible(false);
        pTentangApp.setVisible(false);
        relasi.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Home.setVisible(false);
        Login.setVisible(true);
        CRUD.setVisible(false);
        pDiagnosa1.setVisible(false);
        pTentangLuwak.setVisible(false);
        pTentangApp.setVisible(false);
        relasi.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Home.setVisible(true);
        Login.setVisible(false);
        CRUD.setVisible(false);
        pDiagnosa1.setVisible(false);
        pTentangLuwak.setVisible(false);
        pTentangApp.setVisible(false);
        relasi.setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //tombol login
        String username = jTextField1.getText();
        String password = jPasswordField1.getText();
        
        if (username.contains("admin") && (password.contains("admin")))
        {
            Home.setVisible(false);
            Login.setVisible(false);
            CRUD.setVisible(true);
            pDiagnosa1.setVisible(false);
            pTentangLuwak.setVisible(false);
            pTentangApp.setVisible(false);
            relasi.setVisible(false);
            jTextField1.setText(null);
            jPasswordField1.setText(null);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Username atau password salah","login gagal",JOptionPane.ERROR_MESSAGE);
            jTextField1.setText(null);
            jPasswordField1.setText(null);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        Gejala.setVisible(true);
        Penyakit.setVisible(false);
        relasi.setVisible(false);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        Gejala.setVisible(false);
        Penyakit.setVisible(true);
        relasi.setVisible(false);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void idgejalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idgejalaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idgejalaActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        Home.setVisible(true);
        CRUD.setVisible(false);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void btambahgejalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btambahgejalaActionPerformed
        //tombol tambah tabel gejala
        String ID = idgejala.getText();
        String Gejala = gejalat.getText();
        try{
            Statement statement = (Statement)Konek.getConnection().createStatement();
            statement.executeUpdate("insert into gejala VALUES ('"+ ID +"','"+ Gejala +"');");
            statement.close();
            JOptionPane.showMessageDialog(null, "Data berhasil ditambah");
        }catch(Exception t){
            JOptionPane.showMessageDialog(null, "Data gagal ditambah");
        }
        datatable();
    }//GEN-LAST:event_btambahgejalaActionPerformed

    private void bubahgejalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahgejalaActionPerformed
            //tombol ubah tabel gejala
        String ID = idgejala.getText();
        String Gejala = gejalat.getText();
            
        try{
            Statement statement = (Statement)Konek.getConnection().createStatement();
            statement.executeUpdate("UPDATE gejala SET id_gejala='"+ID+"',"+"nama_gejala='"+Gejala+"'"+"WHERE id_gejala='"+ID+"' OR nama_gejala='"+Gejala+"'");
            statement.close();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        }catch(Exception t) {
            JOptionPane.showMessageDialog(null, "Data gagal diubah");
        }
        datatable();        
    }//GEN-LAST:event_bubahgejalaActionPerformed

    private void bhapusgejalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusgejalaActionPerformed
        //tombol hapus tabel gejala
        String ID = idgejala.getText();
        try{
             Statement statement = (Statement)Konek.getConnection().createStatement();
             statement.executeUpdate("delete from gejala where id_gejala='"+ID+"'");
             JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        }catch(Exception t){
             JOptionPane.showMessageDialog(null, "Data gagal dihapus");
        }
        datatable();
    }//GEN-LAST:event_bhapusgejalaActionPerformed

    private void brefreshgejalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brefreshgejalaActionPerformed
        //refresh text field
        idgejala.setText("");
        gejalat.setText("");
        datatable();/*refresh database*/
    }//GEN-LAST:event_brefreshgejalaActionPerformed

    private void btambahpenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btambahpenyakitActionPerformed
        //tombol tambah tabel penyakit & solusi
        String ID2 = idpenyakit.getText();
        String Penyakit = penyakit.getText();
        String Solusi = solusit.getText();
        try{
            Statement statement = (Statement)Konek.getConnection().createStatement();
            statement.executeUpdate("insert into penyakit VALUES ('"+ID2+"','"+Penyakit+"','"+Solusi+"');");
            statement.close();
            JOptionPane.showMessageDialog(null, "Data berhasil ditambah");
        }catch(Exception t){
            JOptionPane.showMessageDialog(null, "Data gagal ditambah");
        }
        datatable2();
    }//GEN-LAST:event_btambahpenyakitActionPerformed

    private void bubahpenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bubahpenyakitActionPerformed
        //tombol ubah tabel penyakit
            String ID2 = idpenyakit.getText();
            String Penyakit = penyakit.getText();
            String Solusi = solusit.getText();
            
            try{
                Statement statement = (Statement)Konek.getConnection().createStatement();
                statement.executeUpdate("UPDATE penyakit SET id_penyakit='"+ID2+"',"+"nama_penyakit='"+Penyakit+"',"+"solusi='"+Solusi+"'"+"WHERE id_penyakit='"+ID2+"' OR nama_penyakit='"+Penyakit+"'");
                statement.close();
                JOptionPane.showMessageDialog(null, "Data berhasil diubah");
            }catch(Exception t) {
                JOptionPane.showMessageDialog(null, "Data gagal diubah");
            }
        datatable2();
    }//GEN-LAST:event_bubahpenyakitActionPerformed

    private void bhapuspenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapuspenyakitActionPerformed
        //tombol hapus tabel gejala
        String ID2 = idpenyakit.getText();
        try{
             Statement statement = (Statement)Konek.getConnection().createStatement();
             statement.executeUpdate("delete from penyakit where id_penyakit='"+ID2+"'");
             JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        }catch(Exception t){
             JOptionPane.showMessageDialog(null, "Data gagal dihapus");
        }
        datatable2();
    }//GEN-LAST:event_bhapuspenyakitActionPerformed

    private void brefreshpenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brefreshpenyakitActionPerformed
        //refresh text field
        idpenyakit.setText("");
        penyakit.setText("");
        solusit.setText("");
        datatable2();/*refresh database*/
    }//GEN-LAST:event_brefreshpenyakitActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        Home.setVisible(true);
        Login.setVisible(false);
        CRUD.setVisible(false);
        pDiagnosa1.setVisible(false);
        pTentangLuwak.setVisible(false);
        pTentangApp.setVisible(false);
        relasi.setVisible(false);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        Gejala.setVisible(false);
        Penyakit.setVisible(false);
        relasi.setVisible(true);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        //refresh text field
        Frelasi.setText("");
        Fgejala.setText("");
        Fpenyakit.setText("");
        datatable4();/*refresh database*/
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        //tombol tambah relasi
        String IDR = Frelasi.getText();
        String IDG = Fgejala.getText();
        String IDP = Fpenyakit.getText();
        try{
            Statement statement = (Statement)Konek.getConnection().createStatement();
            statement.executeUpdate("insert into relasi VALUES ('"+IDR+"','"+IDG+"','"+IDP+"');");
            statement.close();
            JOptionPane.showMessageDialog(null, "Data berhasil ditambah");
        }catch(Exception t){
            JOptionPane.showMessageDialog(null, "Data gagal ditambah");
        }
        datatable4();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        //tombol ubah relasi
        String IDR = Frelasi.getText();
        String IDG = Fgejala.getText();
        String IDP = Fpenyakit.getText();
            
        try{
            Statement statement = (Statement)Konek.getConnection().createStatement();
            statement.executeUpdate("UPDATE relasi SET id_gejala='"+IDG+"',"+"id_penyakit='"+IDP+"'"+"WHERE id_relasi='"+IDR+"'");
            statement.close();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        }catch(Exception t) {
            JOptionPane.showMessageDialog(null, "Data gagal diubah");
        }
        datatable4();  
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        //tombol hapus
        String IDR = Frelasi.getText();
        String IDG = Fgejala.getText();
        String IDP = Fpenyakit.getText();
        try{
             Statement statement = (Statement)Konek.getConnection().createStatement();
             statement.executeUpdate("delete from relasi where id_relasi='"+IDR+"'");
             JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        }catch(Exception t){
             JOptionPane.showMessageDialog(null, "Data gagal dihapus");
        }
        datatable4();
    }//GEN-LAST:event_jButton13ActionPerformed

    private void FrelasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FrelasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FrelasiActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Home.setVisible(false);
        Login.setVisible(false);
        CRUD.setVisible(false);
        pDiagnosa1.setVisible(false);
        pTentangLuwak.setVisible(true);
        pTentangApp.setVisible(false);
        relasi.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Home.setVisible(false);
        Login.setVisible(false);
        CRUD.setVisible(false);
        pDiagnosa1.setVisible(false);
        pTentangLuwak.setVisible(false);
        pTentangApp.setVisible(true);
        relasi.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        Home.setVisible(true);
        Login.setVisible(false);
        CRUD.setVisible(false);
        pDiagnosa1.setVisible(false);
        pTentangLuwak.setVisible(false);
        pTentangApp.setVisible(false);
        relasi.setVisible(false);
    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        Home.setVisible(true);
        Login.setVisible(false);
        CRUD.setVisible(false);
        pDiagnosa1.setVisible(false);
        pTentangLuwak.setVisible(false);
        pTentangApp.setVisible(false);
        relasi.setVisible(false);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        Clear_Tmp();
        datatable3("all", "");
    }//GEN-LAST:event_jButton11ActionPerformed

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
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CRUD;
    private javax.swing.JTextField Fgejala;
    private javax.swing.JTextField Fpenyakit;
    private javax.swing.JTextField Frelasi;
    private javax.swing.JPanel Gejala;
    private javax.swing.JPanel Home;
    private javax.swing.JPanel Login;
    private javax.swing.JPanel Penyakit;
    private javax.swing.JButton bhapusgejala;
    private javax.swing.JButton bhapuspenyakit;
    private javax.swing.JButton brefreshgejala;
    private javax.swing.JButton brefreshpenyakit;
    private javax.swing.JButton btambahgejala;
    private javax.swing.JButton btambahpenyakit;
    private javax.swing.JButton bubahgejala;
    private javax.swing.JButton bubahpenyakit;
    private javax.swing.JTextField gejalat;
    private javax.swing.JTextField idgejala;
    private javax.swing.JTextField idpenyakit;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    public javax.swing.JLabel lblkode;
    public javax.swing.JLabel lblnmp;
    public javax.swing.JLabel lblsolusi;
    private javax.swing.JPanel pDiagnosa1;
    private javax.swing.JPanel pTentangApp;
    private javax.swing.JPanel pTentangLuwak;
    private javax.swing.JTextField penyakit;
    private javax.swing.JLabel picluwak1;
    private javax.swing.JPanel relasi;
    private javax.swing.JTextField solusit;
    private javax.swing.JTable tabeldiagnosa;
    private javax.swing.JTable tabelgejala;
    private javax.swing.JTable tabelpenyakit;
    private javax.swing.JTable tabelrelasi;
    // End of variables declaration//GEN-END:variables


    
}
