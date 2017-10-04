import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class main implements ActionListener {

    JPanel Panel1,Panel2, Panel3;
    JLabel fileLocLabel, fileLocLabe2, fileLocLabe3;
    JTextField locationField, timeField, zipTtimeField, sizeField, sizeOfZipField, reductionField;
    JTextArea dataField;
    JButton addButton0, zipButton;
    JScrollPane scrollPane;

    //gui interface design is done here
    public JPanel createContentPane () {
        JPanel totalGUI = new JPanel();
        totalGUI.setSize(800, 800);
        totalGUI.setLayout(null);
        Border border = BorderFactory.createLineBorder(Color.BLACK);

        Panel1= new JPanel();
        Panel1.setLayout(null);
        Panel1.setLocation(0, 0);
        Panel1.setBackground(Color.cyan);
        Panel1.setSize(800,120);
        Panel1.setBorder(border);
        totalGUI.add(Panel1);

        Panel3= new JPanel();
        Panel3.setLayout(null);
        Panel3.setLocation(0, 125);
        Panel3.setBackground(Color.red);
        Panel3.setSize(800,180);
        Panel3.setBorder(border);
        totalGUI.add(Panel3);

        fileLocLabel = new JLabel("Path to File:");
        fileLocLabel.setForeground(Color.BLACK);
        fileLocLabel.setLocation(50, 20);
        fileLocLabel.setSize(150, 50);
        fileLocLabel.setHorizontalAlignment(10);
        Panel1.add(fileLocLabel);

        fileLocLabe2 = new JLabel("Time to LOAD:");
        fileLocLabe2.setForeground(Color.BLACK);
        fileLocLabe2.setLocation(50, 60);
        fileLocLabe2.setSize(150, 50);
        fileLocLabe2.setHorizontalAlignment(10);
        Panel1.add(fileLocLabe2);

        fileLocLabe3 = new JLabel("Date file zipped:");
        fileLocLabe3.setForeground(Color.BLACK);
        fileLocLabe3.setLocation(50, 10);
        fileLocLabe3.setSize(150, 50);
        fileLocLabe3.setHorizontalAlignment(10);
        Panel3.add(fileLocLabe3);

        fileLocLabe3 = new JLabel("size of the original file:");
        fileLocLabe3.setForeground(Color.BLACK);
        fileLocLabe3.setLocation(50, 50);
        fileLocLabe3.setSize(150, 50);
        fileLocLabe3.setHorizontalAlignment(10);
        Panel3.add(fileLocLabe3);

        fileLocLabe3 = new JLabel("size of the ZIP file:");
        fileLocLabe3.setForeground(Color.BLACK);
        fileLocLabe3.setLocation(50, 90);
        fileLocLabe3.setSize(150, 50);
        fileLocLabe3.setHorizontalAlignment(10);
        Panel3.add(fileLocLabe3);

        fileLocLabe3 = new JLabel("% size reduction:");
        fileLocLabe3.setForeground(Color.BLACK);
        fileLocLabe3.setLocation(50, 130);
        fileLocLabe3.setSize(150, 50);
        fileLocLabe3.setHorizontalAlignment(10);
        Panel3.add(fileLocLabe3);

        //Button to launch JFilechooser
        addButton0 = new JButton("JFileChooser");
        addButton0.setLocation(600, 30);
        addButton0.setSize(180, 30);
        addButton0.addActionListener(this);
        Panel1.add(addButton0);

        //Button to Zip File
        zipButton = new JButton("Zip File");
        zipButton.setLocation(600, 110);
        zipButton.setSize(180, 30);
        zipButton.addActionListener(this);
        Panel3.add(zipButton);

        //File location textfield
        locationField = new JTextField();
        locationField.setLocation(180, 30);
        locationField.setSize(330, 30);
        locationField.setBorder(border);
        Panel1.add(locationField);

        //Time to load file textfield
        timeField = new JTextField();
        timeField.setLocation(180, 70);
        timeField.setSize(200, 30);
        timeField.setBorder(border);
        Panel1.add(timeField);

        //Date/Time of file zipped at textfield
        zipTtimeField = new JTextField();
        zipTtimeField.setLocation(180, 20);
        zipTtimeField.setSize(200, 30);
        zipTtimeField.setBorder(border);
        Panel3.add(zipTtimeField);

        //size of the original file: textfield
        sizeField = new JTextField();
        sizeField.setLocation(180, 60);
        sizeField.setSize(200, 30);
        sizeField.setBorder(border);
        Panel3.add(sizeField);

        //size of the ZIP file: textfield
        sizeOfZipField = new JTextField();
        sizeOfZipField.setLocation(180, 100);
        sizeOfZipField.setSize(200, 30);
        sizeOfZipField.setBorder(border);
        Panel3.add(sizeOfZipField);

        //% size reduction: textfield
        reductionField = new JTextField();
        reductionField.setLocation(180, 140);
        reductionField.setSize(200, 30);
        reductionField.setBorder(border);
        Panel3.add(reductionField);

        //Panel to contain text area field
        Panel2= new JPanel();
        Panel2.setLayout(null);
        Panel2.setLocation(0, 310);
        Panel2.setSize(800,650);
        Panel2.setBorder(border);
        totalGUI.add(Panel2);

        //Text content Textfield
        dataField = new JTextArea();
        dataField.setWrapStyleWord(true);
        dataField.setLocation(0, 0);
        dataField.setSize(795, 640);
        dataField.setBorder(border);

        //Scroll bar (dataField wrapped into scrollPane)
        scrollPane = new JScrollPane(dataField);
        scrollPane.setBounds(3,3,795,640);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //add text field and scrollPane
        Panel2.add(scrollPane);

        totalGUI.setOpaque(true);
        return totalGUI;
    }

    private static final Logger LOGGER = Logger.getLogger("main");

    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == addButton0) {

            readFile();
        }
        if(e.getSource() == zipButton) {

            zipFile();
        }
    }

    private void readFile()
    {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                try
                {
                    doReadFile();
                }
                catch (IOException e)
                {
                    System.out.println("Error: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done()
            {
                LOGGER.info("Finished reading file");
            }
        };
        worker.execute();
    }

    private void doReadFile() throws IOException
    {
        long startTime = 0;
        JFileChooser fi = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = fi.showOpenDialog(null);
        if(returnValue == JFileChooser.APPROVE_OPTION){
            startTime = System.currentTimeMillis();
            File selecteddFile = fi.getSelectedFile();
            try {
                LOGGER.info("Attempting to open the specified file");
                String s = selecteddFile.getAbsolutePath();
                LOGGER.info("File path = " + selecteddFile);
                FileInputStream fi1 = new FileInputStream(s);
                DataInputStream di1 = new DataInputStream(fi1);
                BufferedReader br1 = new BufferedReader(new InputStreamReader(di1));
                dataField.read(br1,null);
                locationField.setText(s);
            } catch (IOException io) {
                LOGGER.info("Failed to open the file.");
            }
        }
        long stopTime = System.currentTimeMillis();
        float elapsedTime = stopTime - startTime;
        LOGGER.info("Time taken to choose file = " + (float)elapsedTime/1000 + " seconds");
        timeField.setText(Float.toString(elapsedTime/1000) + " seconds");
    }

    private void zipFile()
    {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>()
        {
            @Override
            protected Void doInBackground() throws Exception
            {
                try
                {
                    doZipFile();
                }
                catch (IOException e)
                {
                    System.out.println("Error: " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void done()
            {
                LOGGER.info("Finished Zipping file");
            }
        };
        worker.execute();
    }

    private void doZipFile() throws IOException
    {
        JFileChooser fi = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        int returnValue = fi.showOpenDialog(null);
        if(returnValue == JFileChooser.APPROVE_OPTION) {

            File INPUT_FILE = fi.getSelectedFile();
            String zipFilePath = System.getProperty("user.home") + "\\Desktop\\bigzip";
            LOGGER.info("Path to zip file to: " + zipFilePath);

            try {
                float sizeof  = INPUT_FILE.length();
                sizeField.setText(Float.toString(sizeof) + " Bytes");

                FileOutputStream fileOutputStream = new FileOutputStream(zipFilePath);
                ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);

                ZipEntry ZipEntry = new ZipEntry(INPUT_FILE.getName());
                zipOutputStream.putNextEntry(ZipEntry);

                FileInputStream fileInputStream = new FileInputStream(INPUT_FILE);
                byte[] buf = new byte[1024];
                int bytesRead;

                while ((bytesRead = fileInputStream.read(buf)) > 0) {
                    zipOutputStream.write(buf, 0, bytesRead);
                }
                zipOutputStream.closeEntry();
                zipOutputStream.close();
                zipOutputStream.close();
                File file = new File(zipFilePath);

                float sizeofZip  = file.length();
                sizeOfZipField.setText(Float.toString(sizeofZip) + " Bytes");
                float percentZip = ((sizeof - sizeofZip) / sizeof) * 100;
                double percentZipRound = Math.round(percentZip*100.0)/100.0;
                reductionField.setText(Double.toString(percentZipRound) + "%");

                SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                LOGGER.info("Date/Time file Zipped at: " + date.format(file.lastModified()));
                zipTtimeField.setText(date.format(file.lastModified()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createAndShowGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("          Assigment 1 GUI - Open and display file contents  ");
        main demo = new main();
        frame.setContentPane(demo.createContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(812, 1000);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws IOException
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}

