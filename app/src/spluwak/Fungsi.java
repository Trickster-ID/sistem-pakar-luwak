/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spluwak;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Amrie.Dev
 */
public class Fungsi {
    String data[]=new String[1];
    
    public String kol_gejala(String kol, String param, String kd){
        
        try {
            //Statement statement = (Statement) Konek.getConnection().createStatement();
            Connection kon= new Konek().getConnection();
            java.sql.Statement statement= kon.createStatement();
            
            ResultSet res = statement.executeQuery("Select "+kol+" from gejala where "+param+"='"+kd+"'");
            while (res.next()){
              data[0]=res.getString(1);
            }
            res.close();
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        return data[0];
    }
    
    public String kol_penyakit(String kol, String param, String kd){
        
        try {
            //Statement statement = (Statement) Konek.getConnection().createStatement();
            Connection kon= new Konek().getConnection();
            java.sql.Statement statement= kon.createStatement();
            
            ResultSet res = statement.executeQuery("Select "+kol+" from penyakit where "+param+"='"+kd+"'");
            while (res.next()){
              data[0]=res.getString(1);
            }
            res.close();
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        return data[0];
    }
    
    public String jml_row_tabel(String tabel){
        
        try {
            //Statement statement = (Statement) Konek.getConnection().createStatement();
            Connection kon= new Konek().getConnection();
            java.sql.Statement statement= kon.createStatement();
            
            ResultSet res = statement.executeQuery("Select count(*) as jml from "+tabel+"");
            while (res.next()){
              data[0]=res.getString(1);
            }
            res.close();
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        return data[0];
    }
    
    public String has_penyakit(){
        
        try {
            //Statement statement = (Statement) Konek.getConnection().createStatement();
            Connection kon= new Konek().getConnection();
            java.sql.Statement statement= kon.createStatement();
            
            ResultSet res = statement.executeQuery("Select id_penyakit from relasi where id_gejala in(select id_gejala from tmp_gejala where pil='y') group by id_gejala");
            while (res.next()){
              data[0]=res.getString(1);
            }
            res.close();
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        return data[0];
    }
    
    // Hasil penyakit
    public String jml_row_penyakit(String pny){
        
        try {
            //Statement statement = (Statement) Konek.getConnection().createStatement();
            Connection kon= new Konek().getConnection();
            java.sql.Statement statement= kon.createStatement();
            
            ResultSet res = statement.executeQuery("select count(*) as jml from relasi where id_penyakit='"+pny+"'");
            while (res.next()){
              data[0]=res.getString(1);
            }
            res.close();
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        return data[0];
    }
    
    public String jml_row_has_penyakit(){
        
        try {
            //Statement statement = (Statement) Konek.getConnection().createStatement();
            Connection kon= new Konek().getConnection();
            java.sql.Statement statement= kon.createStatement();
            
            ResultSet res = statement.executeQuery("Select count(id_penyakit) as jml from relasi where id_gejala in(select id_gejala from tmp_gejala where pil='y' group by id_gejala) group by id_penyakit order by jml asc");
            while (res.next()){
              data[0]=res.getString(1);
            }
            res.close();
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        return data[0];
    }
}
