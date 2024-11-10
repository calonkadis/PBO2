import javax.swing.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class perhitungan_diskon {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Perhitungan Diskon");
        JPanel panel = new JPanel();
        
        JLabel labelHarga = new JLabel("Masukkan Harga Asli:");
        JTextField textFieldHarga = new JTextField(10);
        
        JLabel labelDiskon = new JLabel("Pilih Persentase Diskon:");
        JComboBox<String> comboBoxDiskon = new JComboBox<>(new String[]{"5%", "10%", "15%", "20%"});
        
        JButton hitungButton = new JButton("Hitung");
        
        JLabel labelHasil = new JLabel("Harga Akhir: ");
        JLabel labelPenghematan = new JLabel("Jumlah Penghematan: ");
        
        JButton keluarButton = new JButton("Keluar");
        
        DecimalFormat df = new DecimalFormat("#.##");
        
        hitungButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    double hargaAsli = Double.parseDouble(textFieldHarga.getText());
                    int persentaseDiskon = Integer.parseInt(comboBoxDiskon.getSelectedItem().toString().replace("%", ""));
                    
                    double diskon = hargaAsli * persentaseDiskon / 100;
                    double hargaAkhir = hargaAsli - diskon;
                    
                    labelHasil.setText("Harga Akhir: " + df.format(hargaAkhir));
                    labelPenghematan.setText("Jumlah Penghematan: " + df.format(diskon));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Masukkan harga yang valid!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        keluarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        panel.add(labelHarga);
        panel.add(textFieldHarga);
        panel.add(labelDiskon);
        panel.add(comboBoxDiskon);
        panel.add(hitungButton);
        panel.add(labelHasil);
        panel.add(labelPenghematan);
        panel.add(keluarButton);
        
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
