import com.mindfusion.spreadsheet.*;
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

        states = new WorkbookView();
        states.setBorder(_titleBorder);
        states.setColumnHeaderHeight(new Measure(28, MeasureUnit.Pixel));

        Workbook states_book = new Workbook();
        states.setWorkbook(states_book);

        states.setShowTabs(false);
        states.setShowCreateNewTab(false);
        states.setShowFormulaBar(false);
        states.setShowTabCloseButtons(false);
        states.setShowTabNavigationButtons(false);
        states.setShowVerticalScrollBar(true);
        states.setEnabled(false);
        states.setVisible(true);

        states.addViewListener(new WorkbookViewAdapter() {
            @Override
            public void rowClicked(RowMouseEvent e) {
                onChange(e.getRow().getIndex());

            }
            @Override
            public void cellClicked(CellMouseEvent e) {

                if(e.getCell().getColumn()==0)
                    onChange(e.getCell().getRow());


            }
        });

        Worksheet states_data = states_book.getWorksheets().add("States");

        states_data.setMaxColumns(20);
        states_data.setMaxRows(100);
        states_data.getColumns().get(0).setTitle("State");
        states_data.getColumns().get(1).setTitle("Offices");
        states_data.getColumns().get(2).setTitle("Rating");
        states_data.getColumns().get(0).setWidth(new Measure(150));

        employees = new WorkbookView();

        employees = new WorkbookView();
        employees.setBorder(_titleBorder);
        employees.setColumnHeaderHeight(new Measure(28, MeasureUnit.Pixel));

        Workbook employees_book = new Workbook();
        employees.setWorkbook(employees_book);
        employees.setShowTabs(false);
        employees.setShowCreateNewTab(false);
        employees.setShowFormulaBar(false);
        employees.setShowTabCloseButtons(false);
        employees.setShowTabNavigationButtons(false);
        employees.setShowVerticalScrollBar(true);
        employees.setEnabled(false);
        employees.setVisible(true);

        Worksheet employees_data = employees_book.getWorksheets().add("Offices");
        employees_data.setMaxColumns(20);
        employees_data.setMaxRows(100);
        employees_data.getColumns().get(0).setTitle("City");
        employees_data.getColumns().get(1).setTitle("Employees");
        employees_data.getColumns().get(2).setTitle("Coverage");
        employees_data.getColumns().get(0).setWidth(new Measure(150));

        assignData();

        _contentPane.add(states);
        _contentPane.add(employees);
        getContentPane().add(_contentPane);


    }

    private void assignData()
    {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document = null;
        try {
            builder = docFactory.newDocumentBuilder();
            File file = new File(getClass().getResource("Delivery.xml").toURI());
            document = builder.parse(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Element element = document.getDocumentElement();
        stateNodes = element.getElementsByTagName("state");

        for( int i = 0; i < stateNodes.getLength(); i++)
        {
            Element state = (Element)stateNodes.item(i);
            String name = state.getAttribute("name");
            int offices = Integer.parseInt(state.getAttribute("offices"));
            double rating = Double.parseDouble(state.getAttribute("rating"));

            states.getActiveWorksheet().getCells().get(0, i).setData(name);
            states.getActiveWorksheet().getCells().get(1, i).setData(offices);
            states.getActiveWorksheet().getCells().get(2, i).setData(rating);

            Element employeesEl = (Element)state.getElementsByTagName("cities").item(0);
            NodeList employeesList = employeesEl.getElementsByTagName("city");
            if(i == 0) {
                for (int j = 0; j < employeesList.getLength(); j++) {
                    Element employeesData = (Element) employeesList.item(j);
                    String city = employeesData.getAttribute("name");
                    int employeeCount = Integer.parseInt(employeesData.getAttribute("employees"));
                    double coverage = Double.parseDouble(employeesData.getAttribute("coverage"));

                    employees.getActiveWorksheet().getCells().get(0, j).setData(city);
                    employees.getActiveWorksheet().getCells().get(1, j).setData(employeeCount);
                    employees.getActiveWorksheet().getCells().get(2, j).getStyle().setFormat("0.00%");
                    employees.getActiveWorksheet().getCells().get(2, j).setData(coverage);

                }
            }
        }
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

            employees.getActiveWorksheet().getCells().get(0, i).setData(city_name);
            employees.getActiveWorksheet().getCells().get(1, i).setData(employeeCount);
            employees.getActiveWorksheet().getCells().get(2, i).getStyle().setFormat("0.00%");
            employees.getActiveWorksheet().getCells().get(2, i).setData(coverage);
        }
    }

    private WorkbookView states;
    private WorkbookView employees;

    private JPanel _contentPane;
    private TitledBorder _titleBorder;
    private Color[] scale;
    private NodeList stateNodes;
    private static final long serialVersionUID = 1;


}
