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
    private JTextPane classificationField;
    private JButton correctButton;
    private JButton wrongButton;
    private JPanel mainPanel;
    private Trainer trainer;

    private Integer currFile;
    private File[] files;
    private String currClass;

    public IntLearner() {
        this.classifyProgress = new JProgressBar(0, 4);
        this.classifyProgress.setValue(0);
        this.classifyProgress.setStringPainted(true);
        this.classifyProgress.setVisible(true);
        ClickListener listener = new ClickListener();
        this.folderConfirm.addActionListener(listener);
        this.correctButton.addActionListener(listener);
        this.wrongButton.addActionListener(listener);
    }

    public void update (Observable observable, Object update) {
        if (update instanceof String) {
            String[] updateS = ((String) update).split(" -//- ");
            System.out.println(updateS[0]);
            if (updateS[0].equals("progress")) {
                System.out.println(update);
                this.classifyProgress.setString("aaaaaaaaa");
                this.classifyProgress.setValue(this.classifyProgress.getValue()+1);
            } else if (updateS[0].equals("class")) {
                System.out.println(updateS[1]);
                this.currClass = updateS[1];
                this.DocumentText.setText(files[currFile].getName());
                this.classificationField.setText(updateS[1]);
            }
        } else if (update instanceof File[]) {
            System.out.println("files");
            this.currFile = 0;
            this.files = (File[]) update;
            classifyNext();
        }
    }

    public void classifyNext () {
        currFile++;
        if (currFile < files.length) {
            trainer.classifyFile(files[currFile]);
        } else {
            this.DocumentText.setText("Done");
            this.classificationField.setText("Done");
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
                that.trainer.readDocuments();
                that.classifyNext();
            } else if (e.getSource() == correctButton) {
                that.trainer.addDocument(that.files[currFile], currClass);
                that.classifyNext();
            } else if (e.getSource() == wrongButton) {
                that.trainer.addDocument(that.files[currFile], that.trainer.getOpp(currClass));
                that.classifyNext();
            } else if (e.getSource() == "folderBrowse") {
                that.folderField.setText(getDirectory());
            }
        }
    }
}
