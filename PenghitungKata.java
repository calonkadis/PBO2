import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.*;

public class PenghitungKata {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Aplikasi Penghitung Kata");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelInput = new JLabel("Masukkan Teks:");
        JTextArea textArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        JButton hitungButton = new JButton("Hitung");
        
        JLabel labelKata = new JLabel("Jumlah Kata: 0");
        JLabel labelKarakter = new JLabel("Jumlah Karakter: 0");
        JLabel labelKalimat = new JLabel("Jumlah Kalimat: 0");
        JLabel labelParagraf = new JLabel("Jumlah Paragraf: 0");

        // ActionListener untuk tombol Hitung
        hitungButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hitungTeks(textArea.getText(), labelKata, labelKarakter, labelKalimat, labelParagraf);
            }
        });

        // DocumentListener untuk menghitung secara real-time saat teks berubah
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                hitungTeks(textArea.getText(), labelKata, labelKarakter, labelKalimat, labelParagraf);
            }
            public void removeUpdate(DocumentEvent e) {
                hitungTeks(textArea.getText(), labelKata, labelKarakter, labelKalimat, labelParagraf);
            }
            public void changedUpdate(DocumentEvent e) {
                hitungTeks(textArea.getText(), labelKata, labelKarakter, labelKalimat, labelParagraf);
            }
        });

        panel.add(labelInput);
        panel.add(scrollPane);
        panel.add(hitungButton);
        panel.add(labelKata);
        panel.add(labelKarakter);
        panel.add(labelKalimat);
        panel.add(labelParagraf);
        
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Fungsi untuk menghitung jumlah kata, karakter, kalimat, dan paragraf
    private static void hitungTeks(String teks, JLabel labelKata, JLabel labelKarakter, JLabel labelKalimat, JLabel labelParagraf) {
        // Menghitung jumlah kata
        String[] kata = teks.trim().split("\\s+");
        int jumlahKata = kata.length == 1 && kata[0].isEmpty() ? 0 : kata.length;

        // Menghitung jumlah karakter
        int jumlahKarakter = teks.length();

        // Menghitung jumlah kalimat (berdasarkan titik, tanda tanya, dan tanda seru)
        String[] kalimat = teks.split("[.!?]+");
        int jumlahKalimat = kalimat.length == 1 && kalimat[0].isEmpty() ? 0 : kalimat.length;

        // Menghitung jumlah paragraf (berdasarkan baris baru)
        String[] paragraf = teks.split("\n");
        int jumlahParagraf = paragraf.length == 1 && paragraf[0].isEmpty() ? 0 : paragraf.length;

        // Update JLabel dengan hasil perhitungan
        labelKata.setText("Jumlah Kata: " + jumlahKata);
        labelKarakter.setText("Jumlah Karakter: " + jumlahKarakter);
        labelKalimat.setText("Jumlah Kalimat: " + jumlahKalimat);
        labelParagraf.setText("Jumlah Paragraf: " + jumlahParagraf);
    }
}
