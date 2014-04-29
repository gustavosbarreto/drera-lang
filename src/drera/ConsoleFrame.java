/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drera;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author gustavo
 */
public class ConsoleFrame extends javax.swing.JPanel {

    /**
     * Creates new form ConsoleFrame
     */
    public ConsoleFrame() throws IOException {
        initComponents();

        final PrintWriter in;

        console.setContentType("text/html;charset=UTF-8");
        console.setEditorKit(new HTMLEditorKit());
         ((HTMLEditorKit)console.getEditorKit()).getStyleSheet().addRule("body { font-size: serif; font-size: 20 }");

        PipedInputStream inPipe = new PipedInputStream();
        in = new PrintWriter(new PipedOutputStream(inPipe), true);

        System.setIn(inPipe);
        System.setOut(new PrintStream(new JTextAreaOutputStream(console), true, "UTF-8"));

        console.addKeyListener(new KeyAdapter() {
            private StringBuffer line = new StringBuffer();

            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (c == KeyEvent.VK_ENTER) {
                    in.println(line);
                    line.setLength(0);
                } else if (c == KeyEvent.VK_BACK_SPACE) {
                    line.setLength(line.length() - 1);
                } else if (!Character.isISOControl(c)) {
                    line.append(e.getKeyChar());
                }
            }
        });
    }
    
    @Override
    public void requestFocus() {
        this.console.requestFocus();
    }
    
    public void clear() {
        this.console.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        console = new javax.swing.JTextPane();

        jScrollPane1.setViewportView(console);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane console;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

class JTextAreaOutputStream extends OutputStream {

    JTextPane textArea;
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    public JTextAreaOutputStream(JTextPane textArea) {
        super();
        this.textArea = textArea;
    }

    @Override
    public void write(int i) {
        buffer.write(i);
    }

    @Override
    public void flush() throws UnsupportedEncodingException {
        try {
            textArea.getStyledDocument().insertString(textArea.getStyledDocument().getLength(), buffer.toString("UTF-8"), null);
        } catch (BadLocationException ex) {
        }

        textArea.setCaretPosition(textArea.getStyledDocument().getLength());
        buffer.reset();
    }
}