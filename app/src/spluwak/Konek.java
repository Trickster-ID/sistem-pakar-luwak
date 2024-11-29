/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spluwak;

import java.sql.DriverManager;
import javax.swing.JOptionPane;
import com.mysql.jdbc.Driver;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author pikri
 */
class Konek {
    //static Connection Koneksi;
    public static Connection getConnection(){
        Connection Koneksi = null;
        try{
            Koneksi = DriverManager.getConnection("jdbc:mysql://localhost/luwak","root","");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Koneksi Database Gagal");
        }
        return Koneksi;
    }
}
