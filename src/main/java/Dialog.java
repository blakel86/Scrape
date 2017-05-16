import javax.swing.*;

/**
 * Created by laroux0b on 16/05/2017.
 */
public class Dialog {
    ImageIcon icon = null;
    static String parameter;
    public Object[] options = {"SELECT", "Rebuild the database", "Query the existing database"};

    public Dialog() {
        JFrame frame = new JFrame();
        parameter = "SELECT";

        String optionPane = (String) JOptionPane.showInputDialog(
                frame,
                "Do you want to rebuild the database or just query the existing database?",
                "Set Parameters",
                JOptionPane.PLAIN_MESSAGE,
                icon,
                options,
                0);

        if ((optionPane != null) && (optionPane.length() > 0)) {
            parameter = optionPane;
            return;
        }

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}


