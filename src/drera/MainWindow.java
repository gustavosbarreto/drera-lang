/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drera;

import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLEditorKit;

public class MainWindow extends JFrame {

    JTextPane console;
    JScrollPane scroll;
    PrintWriter in;

    public MainWindow() throws IOException {
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        console = new javax.swing.JTextPane();
        console.setContentType("text/html;charset=UTF-8");
        console.setEditorKit(new HTMLEditorKit());

        scroll = new javax.swing.JScrollPane(console);

        add(scroll);

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
