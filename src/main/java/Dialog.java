import javax.swing.*;

class Dialog {

    private String parameter;

    void prompt(){
        JFrame frame = new JFrame();
        try {
            parameter = "SELECT";
            Object[] options = {"SELECT", "Rebuild the database", "Query the existing database"};

            String optionPane = (String) JOptionPane.showInputDialog(
                    frame,
                    "Do you want to rebuild the database or just query the existing database?",
                    "Set Parameters",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    0);

            if ((optionPane != null) && (optionPane.length() > 0)) {
                parameter = optionPane;
            }
        }
        finally {
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
    }

    String getParameter() {
        return parameter;
    }
}


