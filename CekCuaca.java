import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CekCuaca {
    private static final String API_KEY = "your_api_key_here"; // Gantilah dengan API Key OpenWeatherMap Anda

    public static void main(String[] args) {
        JFrame frame = new JFrame("Aplikasi Cek Cuaca");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelKota = new JLabel("Pilih Kota:");
        JComboBox<String> comboBoxKota = new JComboBox<>(new String[]{"Jakarta", "Bandung", "Surabaya", "Yogyakarta"});
        
        JButton cekCuacaButton = new JButton("Cek Cuaca");
        
        JLabel labelSuhu = new JLabel("Suhu: - °C");
        JLabel labelKondisi = new JLabel("Kondisi Cuaca: ");
        JLabel labelGambarCuaca = new JLabel();
        
        // ActionListener untuk tombol Cek Cuaca
        cekCuacaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String kota = (String) comboBoxKota.getSelectedItem();
                String cuacaData = getCuaca(kota);
                if (cuacaData != null) {
                    updateUI(cuacaData, labelSuhu, labelKondisi, labelGambarCuaca);
                }
            }
        });

        panel.add(labelKota);
        panel.add(comboBoxKota);
        panel.add(cekCuacaButton);
        panel.add(labelSuhu);
        panel.add(labelKondisi);
        panel.add(labelGambarCuaca);
        
        frame.add(panel);
        frame.setSize(300, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Fungsi untuk mengambil data cuaca dari OpenWeatherMap API
    private static String getCuaca(String kota) {
        try {
            // Membuat URL untuk request ke OpenWeatherMap API
            String urlString = "http://api.openweathermap.org/data/2.5/weather?q=" + kota + "&appid=" + API_KEY + "&units=metric";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            
            // Membaca response dari API
            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            StringBuilder response = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                response.append((char) i);
            }
            reader.close();
            
            // Parsing JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONObject main = jsonResponse.getJSONObject("main");
            JSONObject weather = jsonResponse.getJSONArray("weather").getJSONObject(0);
            
            // Mengambil data suhu dan kondisi cuaca
            double suhu = main.getDouble("temp");
            String kondisi = weather.getString("description");
            String iconCode = weather.getString("icon");
            
            return suhu + "," + kondisi + "," + iconCode; // Mengembalikan suhu, kondisi cuaca, dan icon cuaca
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Fungsi untuk memperbarui UI dengan data cuaca
    private static void updateUI(String cuacaData, JLabel labelSuhu, JLabel labelKondisi, JLabel labelGambarCuaca) {
        if (cuacaData != null) {
            String[] data = cuacaData.split(",");
            double suhu = Double.parseDouble(data[0]);
            String kondisi = data[1];
            String iconCode = data[2];
            
            labelSuhu.setText("Suhu: " + suhu + " °C");
            labelKondisi.setText("Kondisi Cuaca: " + kondisi);
            
            // Mengambil gambar cuaca berdasarkan icon code
            String iconURL = "http://openweathermap.org/img/wn/" + iconCode + "@2x.png";
            try {
                ImageIcon icon = new ImageIcon(new URL(iconURL));
                labelGambarCuaca.setIcon(icon);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
