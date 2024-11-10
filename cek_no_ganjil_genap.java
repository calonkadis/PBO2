import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class cek_no_ganjil_genap extends JFrame {
    private JTextField inputField;
    private JButton buttonCek, buttonKeluar;
    private JLabel resultLabel;

    public cek_no_ganjil_genap() {
        setTitle("Cek Bilangan Prima");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel inputLabel = new JLabel("Masukkan angka: ");
        inputField = new JTextField(15);
        buttonCek = new JButton("Cek");
        resultLabel = new JLabel("Hasil akan ditampilkan di sini.");
        buttonKeluar = new JButton("Keluar");

        panel.add(inputLabel);
        panel.add(inputField);
        panel.add(buttonCek);
        panel.add(resultLabel);
        panel.add(buttonKeluar);

        add(panel);

        buttonCek.addActionListener(e -> cekBilanganPrima());
        buttonKeluar.addActionListener(e -> System.exit(0));

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });

        inputField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                inputField.setText("");
            }
        });
    }

    private void cekBilanganPrima() {
        String inputText = inputField.getText();
        if (inputText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Harap masukkan angka.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int number = Integer.parseInt(inputText);
            if (number < 2) {
                resultLabel.setText(number + " bukan bilangan prima.");
                JOptionPane.showMessageDialog(this, number + " bukan bilangan prima.");
            } else {
                if (isPrime(number)) {
                    resultLabel.setText(number + " adalah bilangan prima.");
                    JOptionPane.showMessageDialog(this, number + " adalah bilangan prima.");
                } else {
                    resultLabel.setText(number + " bukan bilangan prima.");
                    JOptionPane.showMessageDialog(this, number + " bukan bilangan prima.");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Input tidak valid. Harap masukkan angka.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isPrime(int number) {
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            cek_no_ganjil_genap frame = new cek_no_ganjil_genap();
            frame.setVisible(true);
        });
    }
}
