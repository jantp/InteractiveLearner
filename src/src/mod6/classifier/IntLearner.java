package mod6.classifier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Jan on 1/1/2016.
 */
public class IntLearner implements Observer {
    private JTextField folderField;
    private JButton folderConfirm;
    private JTextPane DocumentText;
    private JProgressBar classifyProgress;
    private JButton correctButton;
    private JPanel mainPanel;
    private JButton browseButton;
    private JComboBox classesDropdown;
    private Trainer trainer;

    private Integer currFile;
    private File[] files;

    public IntLearner() {
        this.classifyProgress.setValue(0);
        this.classifyProgress.setStringPainted(true);
        this.classifyProgress.setVisible(true);
        ClickListener listener = new ClickListener();
        this.folderConfirm.addActionListener(listener);
        this.correctButton.addActionListener(listener);
        this.browseButton.addActionListener(listener);
    }

    public void update (Observable observable, Object update) {
        if (update instanceof String) {
            String[] updateS = ((String) update).split(" -//- ");
            if (updateS[0].equals("progress")) {
                this.DocumentText.setText(updateS[1]);
                int progress = Integer.parseInt(updateS[2]);
                if (progress > 0 && progress < 100) {
                    this.classifyProgress.setValue(this.classifyProgress.getValue() + progress);
                }
            } else if (updateS[0].equals("class")) {
                this.DocumentText.setText(files[currFile].getName());
                this.classesDropdown.setSelectedItem(updateS[1]);
            }
        } else if (update instanceof File[]) {
            this.currFile = 0;
            this.files = (File[]) update;
            this.classifyProgress.setValue(100);
            classesDropdown.setModel(new DefaultComboBoxModel(this.trainer.getClasses()));
            classifyNext();
        }
    }

    public void classifyNext () {
        currFile++;
        if (currFile < files.length) {
            while (files[currFile].isDirectory()) {
                currFile++;
            }
            trainer.classifyFile(files[currFile]);
        } else {
            this.DocumentText.setText("Done");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        JFrame frame = new JFrame("IntLearner");
        frame.setContentPane(new IntLearner().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public String getDirectory() {
        JFileChooser fc = new JFileChooser("./");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }

    private class ClickListener implements ActionListener {
        private IntLearner that = IntLearner.this;
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == folderConfirm) {
                that.folderField.getText();
                that.trainer = new Trainer(that.folderField.getText());
                that.trainer.addObserver(that);
                //that.trainer.readDocuments();
                (new Thread(that.trainer)).start();
            } else if (e.getSource() == correctButton) {
                that.trainer.addDocument(that.files[currFile], (String) that.classesDropdown.getSelectedItem());
                that.classifyNext();
            } else if (e.getSource() == browseButton) {
                that.folderField.setText(getDirectory());
            }
        }
    }
}
