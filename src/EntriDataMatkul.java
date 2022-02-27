import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EntriDataMatkul extends JFrame {
    private JPanel mainPanel;
    private JTextField inputSKS;
    private JTextField inputNamaMatkul;
    private JTextField inputKodeMatkul;
    private JTextField inputKodePrasyarat;
    private JButton tambahButton;
    private JButton ubahButton;
    private JButton hapusButton;
    private JButton bersihButton;
    private JButton searchButton;

    public Connection conn;
    public Statement stmt;
    public ResultSet rs;
    public EntriDataMatkul(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        //panggil koneksi
        Koneksi koneksi = new Koneksi();
        this.conn = koneksi.connection();

        tambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insert();
            }
        });
        ubahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchByKodeMatkul();
            }
        });
        hapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
        bersihButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new EntriDataMatkul("Entri Data Matkul");
        frame.setVisible(true);
    }



    /**
     * Insert Data
     */
    public void insert(){
        String kodeMatkul, nama, kodeprasyarat;
        int sks;

        kodeMatkul = inputKodeMatkul.getText();
        nama = inputNamaMatkul.getText();
        kodeprasyarat = inputKodePrasyarat.getText();
        sks = Integer.parseInt(inputSKS.getText());

        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO matakuliah (kode_matakuliah, nama, sks, kode_prasyarat) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kodeMatkul);
            ps.setString(2, nama);
            ps.setInt(3, sks);
            ps.setString(4, kodeprasyarat);

            int i = ps.executeUpdate();
            if (i > 0){
                JOptionPane.showMessageDialog(null, "Data Berhasil diTambahkan");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void searchByKodeMatkul(){
        String kodeMatkul;

        kodeMatkul = inputKodeMatkul.getText();

        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM matakuliah WHERE kode_matakuliah = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kodeMatkul);

            rs = ps.executeQuery();
            if (rs.next()){
                inputNamaMatkul.setText(rs.getString("nama"));
                inputSKS.setText(String.valueOf(rs.getInt("sks")));
                inputKodePrasyarat.setText(rs.getString("kode_prasyarat"));
            }else{
                JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Update Data
     */
    public void update(){
        String kodeMatkul, nama, kodeprasyarat;
        int sks;

        kodeMatkul = inputKodeMatkul.getText();
        nama = inputNamaMatkul.getText();
        kodeprasyarat = inputKodePrasyarat.getText();
        sks = Integer.parseInt(inputSKS.getText());

        try {
            stmt = conn.createStatement();
            String sql = "UPDATE matakuliah SET kode_matakuliah = ?, nama = ?, sks = ?, kode_prasyarat = ? " +
                    "WHERE kode_matakuliah = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kodeMatkul);
            ps.setString(2, nama);
            ps.setInt(3, sks);
            ps.setString(4, kodeprasyarat);
            ps.setString(5, kodeMatkul);

            int i = ps.executeUpdate();
            if (i > 0){
                JOptionPane.showMessageDialog(null, "Data Berhasil diubah");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Delete Data
     *
     */
    public void delete(){
        String kodeMatkul;

        kodeMatkul = inputKodeMatkul.getText();
        try {
            stmt = conn.createStatement();
            String sql = "DELETE FROM matakuliah WHERE kode_matakuliah = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kodeMatkul);

            int i = ps.executeUpdate();
            if (i > 0){
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Clear Form
     *
     */
    public void clear()
    {
        inputKodeMatkul.setText("");
        inputNamaMatkul.setText("");
        inputSKS.setText("");
        inputKodePrasyarat.setText("");
    }
}
