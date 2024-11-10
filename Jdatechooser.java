import javax.swing.JFrame;
import com.toedter.calendar.JDateChooser;

public class Jdatechooser extends JFrame{
   
   private JDateChooser calender = new JDateChooser();
   
   public Jdatechooser(){
   setTitle("Contoh JdateChooser");
   setSize(200,200);
   setLayout(null);
   
   calender.setBounds(20,40,150,25);
   getContentPane().add(calender);
   show();
      
   }
   
   public static void main(String[] args){
      Jdatechooser objDate = new Jdatechooser();
   }
}
