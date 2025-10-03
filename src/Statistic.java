import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class Statistic extends JFrame {
    JFrame parent;
    Register reg;
    private JPanel MainPanel;
    private JButton Button1;
    private JButton Button2;
    private JButton Button3;
    private JButton Button4;
    private JButton Button5;
    private JButton Button6;
    private JButton Button7;
    private JButton Button8;
    private JButton Button9;

    public Statistic(JFrame parent,Register reg) {
        this.parent = parent;
        this.reg = reg;
        this.setContentPane(MainPanel);
        this.setTitle("Cinema");
        this.setSize(700, 600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                parent.setVisible(true);
            }
        });


        Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox<Integer> comboBox = new JComboBox<>(new Integer[]{1, 2, 3});
                comboBox.setFont(new Font("Arial", Font.BOLD, 20));
                int result = JOptionPane.showConfirmDialog(null, comboBox, "Оберіть номер залу",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    int selectedHall = (Integer) comboBox.getSelectedItem();
                    List<Session> session_hall = reg.List_Session_Hall(selectedHall);

                    if (session_hall.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Сеансів для залу " + selectedHall + " не знайдено.");
                    } else {
                        StringBuilder sb = new StringBuilder("<html>Сеанси залу " + selectedHall + ":<br><br>");
                        for (Session s : session_hall) {
                            sb.append(s.toString()).append("<br><br>");
                        }
                        sb.append("</html>");
                        JLabel label = new JLabel(sb.toString());
                        label.setFont(new Font("Arial", Font.BOLD, 20));

                        JOptionPane.showMessageDialog(null, label, "Список сеансів", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        Button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField textField = new JTextField();
                textField.setFont(new Font("Arial", Font.BOLD, 20));
                int result = JOptionPane.showConfirmDialog(
                        null,
                        textField,
                        "Пошук ",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (result == JOptionPane.OK_OPTION) {
                    String userInput = textField.getText();
                    String message = "Фільмів по пошуку " + userInput + " не знайдено.";
                    JLabel label = new JLabel(message);
                    label.setFont(new Font("Arial", Font.BOLD, 20));

                    List<String> films = reg.Search_film_like(userInput);
                    if (films.isEmpty()) {
                        JOptionPane.showMessageDialog(null, label);
                    } else {
                        StringBuilder sb = new StringBuilder("<html>Фільми які починаються на  " + userInput + ":<br><br>");
                        for (String s : films) {
                            sb.append(s).append("<br><br>");
                        }
                        sb.append("</html>");
                        JLabel label_ = new JLabel(sb.toString());
                        label_.setFont(new Font("Arial", Font.BOLD, 20));
                        JOptionPane.showMessageDialog(null, label_, "Список сеансів", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        Button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField startTimeField = new JTextField("14:00:00");
                JTextField endTimeField = new JTextField("18:00:00");
                startTimeField.setFont(new Font("Arial", Font.PLAIN, 16));
                endTimeField.setFont(new Font("Arial", Font.PLAIN, 16));
                startTimeField.setPreferredSize(new Dimension(200, 30));
                endTimeField.setPreferredSize(new Dimension(200, 30));

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                JLabel lb1 = new JLabel("Початковий час (HH:MM:SS):");
                lb1.setFont(new Font("Arial", Font.PLAIN, 18));
                panel.add(lb1);
                panel.add(Box.createVerticalStrut(5));
                panel.add(startTimeField);

                JLabel lb2 = new JLabel("Кінцевий час (HH:MM:SS):");
                lb2.setFont(new Font("Arial", Font.PLAIN, 18));
                panel.add(Box.createVerticalStrut(10));
                panel.add(lb2);
                panel.add(Box.createVerticalStrut(5));
                panel.add(endTimeField);

                int result = JOptionPane.showConfirmDialog(
                        null, panel, "Введіть інтервал часу",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
                );

                if (result == JOptionPane.OK_OPTION) {
                    String startTimeStr = startTimeField.getText().trim();
                    String endTimeStr = endTimeField.getText().trim();

                    java.sql.Time startTime;
                    java.sql.Time endTime;

                    try {
                        // Спроба конвертувати строки у java.sql.Time
                        startTime = java.sql.Time.valueOf(startTimeStr);
                        endTime = java.sql.Time.valueOf(endTimeStr);
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, "Неправильний формат часу! Використовуйте формат HH:MM:SS.", "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (startTime.after(endTime)) {
                        JOptionPane.showMessageDialog(null, "Початковий час не може бути більшим за кінцевий!", "Помилка", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Виклик процедури / методу отримання сеансів
                    List<String> film = reg.Session_by_time_between(startTimeStr, endTimeStr);

                    if (film.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Сеансів у вказаному інтервалі не знайдено.");
                    } else {
                        StringBuilder sb = new StringBuilder("<html><body style='width: 500px;'>");
                        sb.append("<b>Сеанси з ").append(startTimeStr).append(" по ").append(endTimeStr).append(":</b><br><br>");
                        int count = 0;
                        for (String s : film) {
                            sb.append(s).append("  ");
                            count++;
                            if (count % 4 == 0) {
                                sb.append("<br>");
                            }
                        }
                        sb.append("</body></html>");

                        JLabel label = new JLabel(sb.toString());
                        label.setFont(new Font("Arial", Font.PLAIN, 20));

                        JPanel outputPanel = new JPanel();
                        outputPanel.add(label);

                        JScrollPane scrollPane = new JScrollPane(outputPanel);
                        scrollPane.setPreferredSize(new Dimension(600, 400));

                        JOptionPane.showMessageDialog(null, scrollPane, "Список сеансів", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        Button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int money = reg.All_money();
                String message = "Загальна сума прибутку від проданих квитків: " + money + " грн.";

                JLabel label = new JLabel(message);
                label.setFont(new Font("Arial", Font.BOLD, 20)); // Змінити розмір шрифту

                JOptionPane.showMessageDialog(null, label, "Прибуток", JOptionPane.INFORMATION_MESSAGE);

            }
        });
        Button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = reg.How_many_tickets_sold_movie();
                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setFont(new Font("Arial", Font.BOLD, 20));
                JOptionPane.showMessageDialog(null, textArea, "Характеристика", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        Button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String film = reg.Most_Popular_Film();
                JLabel lb1 = new JLabel("Найпопулярніший фільм "+film);
                lb1.setFont(new Font("Arial", Font.PLAIN, 18));
                JOptionPane.showMessageDialog(null, lb1, "Найпопулярніший фільм", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        Button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder  sb = reg.For_every_janr_popular_movie();
                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setFont(new Font("Arial", Font.BOLD, 20));
                JOptionPane.showMessageDialog(null, textArea, "Статистика", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        Button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder  sb = reg.All_film_dont_sale();
                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setFont(new Font("Arial", Font.BOLD, 20));
                JOptionPane.showMessageDialog(null, textArea, "<UNK>", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        Button9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder  sb = reg.Popular_and_not_sales();
                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setFont(new Font("Arial", Font.BOLD, 20));
                JOptionPane.showMessageDialog(null, textArea, "<UNK>", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}

