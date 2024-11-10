import javax.swing.*;
import java.awt.*;
import com.toedter.calendar.JDateChooser;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.text.SimpleDateFormat;
import java.util.Date;

public class penghitung_umur extends JFrame {
    private JPanel panel;
    private JDateChooser dateChooserLahir;
    private JLabel labelHasil;
    private JLabel labelUlangTahun;
    private JButton buttonHitung;
    private JButton buttonKeluar;

    public penghitung_umur() {
        setTitle("Penghitung Umur");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel labelLahir = new JLabel("Pilih Tanggal Lahir:");
        dateChooserLahir = new JDateChooser();
        dateChooserLahir.setDateFormatString("yyyy-MM-dd");

        labelHasil = new JLabel("Umur: ");
        labelUlangTahun = new JLabel("Hari Ulang Tahun Berikutnya: ");
        
        buttonHitung = new JButton("Hitung Umur");
        buttonKeluar = new JButton("Keluar");

        panel.add(labelLahir);
        panel.add(dateChooserLahir);
        panel.add(buttonHitung);
        panel.add(buttonKeluar);
        panel.add(labelHasil);
        panel.add(labelUlangTahun);

        add(panel);

        buttonHitung.addActionListener(e -> hitungUmur());
        buttonKeluar.addActionListener(e -> System.exit(0));
    }

    private void hitungUmur() {
        try {
            Date tanggalLahir = dateChooserLahir.getDate();
            if (tanggalLahir == null) {
                JOptionPane.showMessageDialog(this, "Harap pilih tanggal lahir!");
                return;
            }

            LocalDate birthDate = convertToLocalDate(tanggalLahir);
            LocalDate today = LocalDate.now();

            long years = ChronoUnit.YEARS.between(birthDate, today);
            long months = ChronoUnit.MONTHS.between(birthDate, today) % 12;
            long days = ChronoUnit.DAYS.between(birthDate.plusYears(years).plusMonths(months), today);

            labelHasil.setText("Umur: " + years + " tahun, " + months + " bulan, " + days + " hari");

            // Hari ulang tahun berikutnya
            LocalDate nextBirthday = birthDate.plusYears(years + 1);
            while (nextBirthday.isBefore(today)) {
                nextBirthday = nextBirthday.plusYears(1);
            }

            labelUlangTahun.setText("Hari Ulang Tahun Berikutnya: " + nextBirthday.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan dalam menghitung umur.");
        }
    }

    private LocalDate convertToLocalDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return LocalDate.parse(sdf.format(date));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new penghitung_umur().setVisible(true);
        });
    }
}
