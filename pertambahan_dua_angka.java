import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class pertambahan_dua_angka extends JFrame {
    private JPanel panel;
    private JTextField input1;
    private JTextField input2;
    private JLabel labelHasil;
    private JButton buttonTambah;
    private JButton buttonKurang;
    private JButton buttonKali;
    private JButton buttonBagi;
    private JButton buttonHapus;
    private JButton buttonKeluar;

    public pertambahan_dua_angka() {
        setTitle("Pertambahan Dua Angka");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2, 10, 10));

        JLabel label1 = new JLabel("Angka Pertama:");
        input1 = new JTextField();
        input1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                filterKeyTyped(e);
            }
        });
        input1.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                input1.setText("");
            }
        });

        JLabel label2 = new JLabel("Angka Kedua:");
        input2 = new JTextField();
        input2.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                filterKeyTyped(e);
            }
        });
        input2.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                input2.setText("");
            }
        });

        labelHasil = new JLabel("Hasil: ");
        
        buttonTambah = new JButton("Tambah");
        buttonKurang = new JButton("Kurang");
        buttonKali = new JButton("Kali");
        buttonBagi = new JButton("Bagi");
        buttonKeluar = new JButton("Keluar");
        buttonHapus = new JButton("Hapus");

        panel.add(label1);
        panel.add(input1);
        panel.add(label2);
        panel.add(input2);
        panel.add(buttonTambah);
        panel.add(buttonKurang);
        panel.add(buttonKali);
        panel.add(buttonBagi);
        panel.add(buttonHapus);
        panel.add(buttonKeluar);
        panel.add(labelHasil);

        add(panel);

        buttonTambah.addActionListener(e -> operasiMatematika("tambah"));
        buttonKurang.addActionListener(e -> operasiMatematika("kurang"));
        buttonKali.addActionListener(e -> operasiMatematika("kali"));
        buttonBagi.addActionListener(e -> operasiMatematika("bagi"));
        buttonKeluar.addActionListener(e -> System.exit(0));
        buttonHapus.addActionListener(e -> hapusData());
    }

    private void filterKeyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE) {
            e.consume();
        }
    }

    private void operasiMatematika(String operasi) {
        try {
            String input1Text = input1.getText();
            String input2Text = input2.getText();

            if (input1Text.isEmpty() || input2Text.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Harap masukkan kedua angka!");
                return;
            }

            double angka1 = Double.parseDouble(input1Text);
            double angka2 = Double.parseDouble(input2Text);
            double hasil = 0;

            if (operasi.equals("tambah")) {
                hasil = angka1 + angka2;
            } else if (operasi.equals("kurang")) {
                hasil = angka1 - angka2;
            } else if (operasi.equals("kali")) {
                hasil = angka1 * angka2;
            } else if (operasi.equals("bagi")) {
                if (angka2 != 0) {
                    hasil = angka1 / angka2;
                } else {
                    JOptionPane.showMessageDialog(this, "Tidak bisa membagi dengan nol!");
                    return;
                }
            }

            labelHasil.setText("Hasil: " + hasil);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid!");
        }
    }

    private void hapusData() {
        input1.setText("");
        input2.setText("");
        labelHasil.setText("Hasil: ");
        input1.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new pertambahan_dua_angka().setVisible(true);
        });
    }
}
