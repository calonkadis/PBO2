import javax.swing.*;
import java.awt.event.*;

public class KonversiSuhu {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Konversi Suhu");
        JPanel panel = new JPanel();
        
        JLabel labelInput = new JLabel("Masukkan Suhu:");
        JTextField textField = new JTextField(10);
        
        JLabel labelHasil = new JLabel("Hasil Konversi: ");
        
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Celsius to Fahrenheit", "Fahrenheit to Celsius"});
        
        JButton konversiButton = new JButton("Konversi");
        JButton keluarButton = new JButton("Keluar");
        
        konversiButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                double suhu = Double.parseDouble(input);
                
                String selectedConversion = (String) comboBox.getSelectedItem();
                double hasilKonversi = 0;
                
                if (selectedConversion.equals("Celsius to Fahrenheit")) {
                    hasilKonversi = (suhu * 9/5) + 32;
                } else if (selectedConversion.equals("Fahrenheit to Celsius")) {
                    hasilKonversi = (suhu - 32) * 5/9;
                }
                
                labelHasil.setText("Hasil Konversi: " + hasilKonversi);
            }
        });
        
        keluarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        textField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                }
            }
        });
        
        panel.add(labelInput);
        panel.add(textField);
        panel.add(comboBox);
        panel.add(konversiButton);
        panel.add(labelHasil);
        panel.add(keluarButton);
        
        frame.add(panel);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
