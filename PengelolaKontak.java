import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PengelolaKontak extends JFrame {
    private JPanel panel;
    private JTextField inputNama;
    private JTextField inputNomorTelepon;
    private JTextField inputEmail;
    private JLabel labelHasil;
    private JButton buttonSimpan;
    private JButton buttonKeluar;

    public PengelolaKontak() {
        setTitle("Pengelola Kontak");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel label1 = new JLabel("Nama:");
        inputNama = new JTextField();

        JLabel label2 = new JLabel("Nomor Telepon:");
        inputNomorTelepon = new JTextField();
        inputNomorTelepon.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                filterKeyTyped(e);
            }
        });

        JLabel label3 = new JLabel("Email:");
        inputEmail = new JTextField();

        labelHasil = new JLabel("Kontak Disimpan: ");
        
        buttonSimpan = new JButton("Simpan");
        buttonKeluar = new JButton("Keluar");

        panel.add(label1);
        panel.add(inputNama);
        panel.add(label2);
        panel.add(inputNomorTelepon);
        panel.add(label3);
        panel.add(inputEmail);
        panel.add(buttonSimpan);
        panel.add(buttonKeluar);
        panel.add(labelHasil);

        add(panel);

        buttonSimpan.addActionListener(e -> simpanKontak());
        buttonKeluar.addActionListener(e -> System.exit(0));
    }

    private void filterKeyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
            e.consume();
        }
    }

    private void simpanKontak() {
        try {
            String nama = inputNama.getText();
            String nomorTelepon = inputNomorTelepon.getText();
            String email = inputEmail.getText();

            if (nama.isEmpty() || nomorTelepon.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Harap masukkan semua data kontak!");
                return;
            }

            // Menampilkan data kontak yang sudah dimasukkan
            labelHasil.setText("Kontak Disimpan: " + "Nama: " + nama + ", Telepon: " + nomorTelepon + ", Email: " + email);

            // Membersihkan input setelah data disimpan
            inputNama.setText("");
            inputNomorTelepon.setText("");
            inputEmail.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan kontak!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PengelolaKontak().setVisible(true);
        });
    }
}
