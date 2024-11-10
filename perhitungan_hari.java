import javax.swing.*;
import com.toedter.calendar.JCalendar;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

public class perhitungan_hari {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Jumlah Hari dalam Bulan");
        JPanel panel = new JPanel();
        
        JLabel labelBulan = new JLabel("Pilih Bulan:");
        JComboBox<String> comboBoxBulan = new JComboBox<>(new String[]{"Januari", "Februari", "Maret", "April", "Mei", "Juni", 
                                                                     "Juli", "Agustus", "September", "Oktober", "November", "Desember"});
        
        JLabel labelTahun = new JLabel("Masukkan Tahun:");
        JSpinner spinnerTahun = new JSpinner(new SpinnerNumberModel(2024, 1900, 2100, 1));
        
        JCalendar calendar = new JCalendar();
        
        JButton hitungButton = new JButton("Hitung");
        
        JLabel labelHasil = new JLabel("Jumlah Hari: ");
        JLabel labelHariPertama = new JLabel("Hari Pertama: ");
        JLabel labelHariTerakhir = new JLabel("Hari Terakhir: ");
        
        hitungButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int bulanIndex = comboBoxBulan.getSelectedIndex();
                int tahun = (Integer) spinnerTahun.getValue();
                
                YearMonth yearMonth = YearMonth.of(tahun, bulanIndex + 1); // bulanIndex dimulai dari 0, jadi +1 untuk YearMonth
                int jumlahHari = yearMonth.lengthOfMonth();
                
                LocalDate firstDay = yearMonth.atDay(1);
                LocalDate lastDay = yearMonth.atEndOfMonth();
                
                labelHasil.setText("Jumlah Hari: " + jumlahHari);
                labelHariPertama.setText("Hari Pertama: " + firstDay.getDayOfWeek());
                labelHariTerakhir.setText("Hari Terakhir: " + lastDay.getDayOfWeek());
            }
        });
        
        spinnerTahun.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int tahun = (Integer) spinnerTahun.getValue();
                // Cek apakah tahun kabisat
                boolean isLeapYear = YearMonth.of(tahun, 2).isLeapYear();
                // Tampilkan pesan atau lakukan apa pun berdasarkan tahun kabisat jika diperlukan
            }
        });
        
        panel.add(labelBulan);
        panel.add(comboBoxBulan);
        panel.add(labelTahun);
        panel.add(spinnerTahun);
        panel.add(calendar);
        panel.add(hitungButton);
        panel.add(labelHasil);
        panel.add(labelHariPertama);
        panel.add(labelHariTerakhir);
        
        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
