/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drera;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.script.ScriptException;
import javax.swing.JFileChooser;

public class Drera {
    public static void main(String[] args) throws FileNotFoundException, IOException, ScriptException {
        Interpreter interpreter = new Interpreter();
        
        String fileName;
        
        if (args.length == 0) {
            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(fc);
            fileName = fc.getSelectedFile().getAbsolutePath();
        } else {
            fileName = args[0];
        }
        
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            interpreter.parse(line);
        }
    }
}
