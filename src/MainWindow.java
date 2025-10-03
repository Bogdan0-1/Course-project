import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JPanel MainPanel;
    private JButton Button1;
    private JButton Button2;

    public MainWindow(Register reg) {

        this.setContentPane(MainPanel);
        this.setTitle("Cinema");
        this.setSize(600,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        Button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CreateOrder(MainWindow.this,reg);
                setVisible(false);
            }
        });
        Button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Statistic(MainWindow.this,reg);
                setVisible(false);
            }
        });
    }
}
