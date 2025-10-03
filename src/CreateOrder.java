import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class CreateOrder extends JFrame {
    private JFrame parent;
    private Register reg;
    private JPanel MainPanel;
    private JTextField FilmTextField;
    private JTextField DataTextField;
    private JTextField TimeTextField;
    private JComboBox <Session> sessionList;
    private JComboBox <Seat> seatList;
    private JTextField FullnameTextField;
    private JTextField EmailtextField;
    private JButton Button;

    private List<Session> sessions = new ArrayList<>();

    public CreateOrder(JFrame parent,Register reg){
        this.reg=reg;
        this.parent = parent;
        this.setContentPane(MainPanel);
        this.setTitle("Cinema");
        this.setSize(1200,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.sessionList.setFont(new Font("Arial",Font.PLAIN, 18));
        this.seatList.setFont(new Font("Arial",Font.PLAIN,18));
        this.FilmTextField.setFont(new Font("Arial",Font.PLAIN,18));
        this.DataTextField.setFont(new Font("Arial",Font.PLAIN,18));
        this.TimeTextField.setFont(new Font("Arial",Font.PLAIN,18));
        this.FullnameTextField.setFont(new Font("Arial",Font.PLAIN,18));
        this.EmailtextField.setFont(new Font("Arial",Font.PLAIN,18));
        this.Button.setFont(new Font("Arial",Font.PLAIN,18));
        this.loadSession();
        this.updateSeats();

        this.setVisible(true);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                parent.setVisible(true);
            }
        });

        sessionList.addActionListener(e -> {
            Session selectedSession = (Session) sessionList.getSelectedItem();
            if (selectedSession != null) {
                updateSeats();
            }
        });

        DocumentListener docListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateList(); }
            public void removeUpdate(DocumentEvent e) { updateList(); }
            public void changedUpdate(DocumentEvent e) { updateList(); }
        };

        FilmTextField.getDocument().addDocumentListener(docListener);
        DataTextField.getDocument().addDocumentListener(docListener);
        TimeTextField.getDocument().addDocumentListener(docListener);

        Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (FullnameTextField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Заповніть поле ПІБ!", "Помилка", JOptionPane.WARNING_MESSAGE);}
                else if (EmailtextField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Заповніть поле Email!", "Помилка", JOptionPane.WARNING_MESSAGE);}
                else {
                    int price = reg.getPrice( (Session) sessionList.getSelectedItem(),(Seat) seatList.getSelectedItem());

                    String inputpricetxt = JOptionPane.showInputDialog(null, "Необхідна сума оплати "+price +" грн", "Платіж", JOptionPane.QUESTION_MESSAGE);

                    if (inputpricetxt != null) {
                        Integer inputprice = Integer.parseInt(inputpricetxt);
                        if(inputprice<price){
                            JOptionPane.showMessageDialog(null,
                                    "Введена сума недостатня. Необхідно: " + (price-inputprice) + " грн.",
                                    "Помилка",JOptionPane.ERROR_MESSAGE);
                        }else{
                            int rest = reg.makePayment(inputprice);
                            JOptionPane.showMessageDialog(null,"Решта "+rest,"Решта",JOptionPane.INFORMATION_MESSAGE);

                            String Fullname = FullnameTextField.getText().trim();
                            String Email = EmailtextField.getText().trim();
                            reg.setCinemagoer(Fullname,Email);
                            FullnameTextField.setText("");
                            EmailtextField.setText("");

                            reg.save((Seat) seatList.getSelectedItem(),(Session) sessionList.getSelectedItem(),price);
                            updateSeats();
                            JOptionPane.showMessageDialog(null,"Замовлення успішно створено !","Результа",JOptionPane.INFORMATION_MESSAGE);

                        }
                    }else {
                        System.out.println("Введення скасовано.");
                    }
                }


            }
        });
    }

    private void updateList() {
        String filmText = FilmTextField.getText().trim();
        String dateText = DataTextField.getText().trim();
        String timeText = TimeTextField.getText().trim();



        List<Session> filteredByFilm = reg.isFilm(filmText);
        List<Session> filteredByDateTime = reg.isDate(dateText, timeText);

        // Об’єднуємо списки - беремо перетин (сеанси, які є в обох списках)
        List<Session> filteredSessions = new ArrayList<>();
        for (Session session : filteredByFilm) {
            if (filteredByDateTime.contains(session)) {
                filteredSessions.add(session);
            }
        }

        sessionList.removeAllItems();
        for (Session session : filteredSessions) {
            sessionList.addItem(session);
        }
    }

    public void loadSession()   {
        sessionList.removeAllItems();
        sessions = reg.loadSessions();
        for (Session session : sessions) {
            sessionList.addItem(session);
        }
    }

    public void updateSeats(){
        seatList.removeAllItems();
        List<Seat> seats = reg.isSeat((Session) sessionList.getSelectedItem());
        for (Seat seat : seats) {
            seatList.addItem(seat);
        }
    }


}
