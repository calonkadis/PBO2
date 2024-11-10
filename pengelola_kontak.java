import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;
import java.io.*;

public class PengelolaKontak extends JFrame {
    private JPanel panel;
    private JTextField inputNama, inputNomor;
    private JComboBox<String> comboKategori;
    private JTable tableKontak;
    private DefaultTableModel tableModel;
    private JButton buttonTambah, buttonEdit, buttonHapus, buttonCari, buttonKeluar, buttonExport, buttonImport;
    private Connection conn;
    private PreparedStatement pst;

    public PengelolaKontak() {
        setTitle("Pengelola Kontak");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel labelNama = new JLabel("Nama:");
        JLabel labelNomor = new JLabel("Nomor Telepon:");
        JLabel labelKategori = new JLabel("Kategori:");

        inputNama = new JTextField();
        inputNomor = new JTextField();

        comboKategori = new JComboBox<>(new String[] { "Keluarga", "Teman", "Kerja" });

        inputPanel.add(labelNama);
        inputPanel.add(inputNama);
        inputPanel.add(labelNomor);
        inputPanel.add(inputNomor);
        inputPanel.add(labelKategori);
        inputPanel.add(comboKategori);

        JPanel buttonPanel = new JPanel();
        buttonTambah = new JButton("Tambah");
        buttonEdit = new JButton("Edit");
        buttonHapus = new JButton("Hapus");
        buttonCari = new JButton("Cari");
        buttonKeluar = new JButton("Keluar");
        buttonExport = new JButton("Export CSV");
        buttonImport = new JButton("Import CSV");

        buttonPanel.add(buttonTambah);
        buttonPanel.add(buttonEdit);
        buttonPanel.add(buttonHapus);
        buttonPanel.add(buttonCari);
        buttonPanel.add(buttonExport);
        buttonPanel.add(buttonImport);
        buttonPanel.add(buttonKeluar);

        tableModel = new DefaultTableModel(new String[] { "ID", "Nama", "Nomor Telepon", "Kategori" }, 0);
        tableKontak = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableKontak);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        add(panel);

        initDatabase();

        buttonTambah.addActionListener(e -> tambahKontak());
        buttonEdit.addActionListener(e -> editKontak());
        buttonHapus.addActionListener(e -> hapusKontak());
        buttonCari.addActionListener(e -> cariKontak());
        buttonExport.addActionListener(e -> exportToCSV());
        buttonImport.addActionListener(e -> importFromCSV());
        buttonKeluar.addActionListener(e -> System.exit(0));

        loadData();
    }

    private void initDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:kontak.db");
            Statement stmt = conn.createStatement();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS kontak (id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT, nomor TEXT, kategori TEXT)";
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void tambahKontak() {
        try {
            String nama = inputNama.getText();
            String nomor = inputNomor.getText();
            String kategori = (String) comboKategori.getSelectedItem();

            if (nama.isEmpty() || nomor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Harap lengkapi semua data!");
                return;
            }

            if (!nomor.matches("\\d{10,12}")) {
                JOptionPane.showMessageDialog(this, "Nomor telepon harus terdiri dari 10 hingga 12 digit angka.");
                return;
            }

            String sql = "INSERT INTO kontak (nama, nomor, kategori) VALUES (?, ?, ?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, nama);
            pst.setString(2, nomor);
            pst.setString(3, kategori);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Kontak berhasil ditambahkan!");
            loadData();
            clearInputFields();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menambah kontak.");
        }
    }

    private void editKontak() {
        int row = tableKontak.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih kontak yang ingin diedit!");
            return;
        }

        try {
            String id = tableKontak.getValueAt(row, 0).toString();
            String nama = inputNama.getText();
            String nomor = inputNomor.getText();
            String kategori = (String) comboKategori.getSelectedItem();

            if (!nomor.matches("\\d{10,12}")) {
                JOptionPane.showMessageDialog(this, "Nomor telepon harus terdiri dari 10 hingga 12 digit angka.");
                return;
            }

            String sql = "UPDATE kontak SET nama = ?, nomor = ?, kategori = ? WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, nama);
            pst.setString(2, nomor);
            pst.setString(3, kategori);
            pst.setString(4, id);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Kontak berhasil diedit!");
            loadData();
            clearInputFields();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengedit kontak.");
        }
    }

    private void hapusKontak() {
        int row = tableKontak.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih kontak yang ingin dihapus!");
            return;
        }

        try {
            String id = tableKontak.getValueAt(row, 0).toString();
            String sql = "DELETE FROM kontak WHERE id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, id);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Kontak berhasil dihapus!");
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menghapus kontak.");
        }
    }

    private void cariKontak() {
        String nama = inputNama.getText();
        String nomor = inputNomor.getText();

        try {
            String sql = "SELECT * FROM kontak WHERE nama LIKE ? OR nomor LIKE ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + nama + "%");
            pst.setString(2, "%" + nomor + "%");
            ResultSet rs = pst.executeQuery();

            tableModel.setRowCount(0);

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(rs.getString("id"));
                row.add(rs.getString("nama"));
                row.add(rs.getString("nomor"));
                row.add(rs.getString("kategori"));
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mencari kontak.");
        }
    }

    private void loadData() {
        try {
            String sql = "SELECT * FROM kontak";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            tableModel.setRowCount(0);

            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(rs.getString("id"));
                row.add(rs.getString("nama"));
                row.add(rs.getString("nomor"));
                row.add(rs.getString("kategori"));
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearInputFields() {
        inputNama.setText("");
        inputNomor.setText("");
    }

    private void exportToCSV() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    StringBuilder row = new StringBuilder();
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        row.append(tableModel.getValueAt(i, j).toString());
                        if (j < tableModel.getColumnCount() - 1) {
                            row.append(",");
                        }
                    }
                    writer.write(row.toString());
                    writer.newLine();
                }
                writer.close();
                JOptionPane.showMessageDialog(this, "Data berhasil diekspor!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ter
