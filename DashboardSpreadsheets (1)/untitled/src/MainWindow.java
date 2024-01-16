
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;


public class MainWindow extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }

    private MainWindow()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(1200, 800));
        setTitle("Delivery service");

        scale = new Color[]{
                Color.decode("#20C300"),
                Color.decode("#FFFF00"),
                Color.decode("#FF8000")
        };

        _contentPane = new JPanel();
        _contentPane.setLayout(new GridLayout(2, 2, 2, 2));

        _titleBorder = BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.RAISED));



        getContentPane().add(_contentPane);


    }



    private void onChange(int index)
    {
        //the user could have clicked on an empty cell
        if(index >= stateNodes.getLength())
            return;
        Element state = (Element) stateNodes.item(index);

        Element emps = (Element) state.getElementsByTagName("cities").item(0);
        NodeList emp_list = emps.getElementsByTagName("city");

        for(int i = 0; i < emp_list.getLength(); i++)
        {
            Element emp_data = (Element)emp_list.item(i);
            String city_name = emp_data.getAttribute("name");
            int employeeCount = Integer.parseInt(emp_data.getAttribute("employees"));
            double coverage = Double.parseDouble(emp_data.getAttribute("coverage"));


             }
    }

    private JPanel _contentPane;
    private TitledBorder _titleBorder;
    private Color[] scale;
    private NodeList stateNodes;
    private static final long serialVersionUID = 1;


}
