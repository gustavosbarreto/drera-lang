package drera;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Interpreter {
    enum State {
        Default,
        If,
        Else,
        While
    }
    
    State state = State.Default;
    
    boolean ifIsTrue = false;
    String whileExpr;
    
    ArrayList<String> whileBlock = new ArrayList();
    
    ScriptEngineManager engineManager = new ScriptEngineManager();
    ScriptEngine engine = null;
    
    public Interpreter() throws ScriptException {
        this.engine = engineManager.getEngineByName("JavaScript");

    }
    
    public void parse(String line) throws ScriptException {
        line = line.trim();

        if (this.state == State.While) {
            if (line.startsWith("enquanto")) {
                            Matcher matcher = Pattern.compile("enquanto\\s(.*)").matcher(line);
            matcher.find();
            this.whileExpr = matcher.group(1);
                this.state = State.Default;
                
                while (this.engine.eval(this.whileExpr).toString().equals("true")) {
                    for (String s : whileBlock) {
                        parse(s);
                    }
                }
                
                whileBlock.clear();
            } else {
                whileBlock.add(line);
            }
            return;
        }
        
        if (line.startsWith("fim"))
            this.state = State.Default;

        if (this.state == State.If) {
            if (line.startsWith("senao")) {
                this.state = State.Else;
            }
            
            if (!this.ifIsTrue)
                return;
        }
        
        if (this.state == State.Else && this.ifIsTrue) {
            if (line.startsWith("fim"))
                this.state = State.Default;

            return;
        }
        
        if (line.matches("^(.*)\\s=\\s(.*)$")) {
            Matcher matcher = Pattern.compile("^(.*)\\s=\\s(.*)$").matcher(line);
            matcher.find();
            String name = matcher.group(1).trim();
            String value = this.engine.eval(matcher.group(2)).toString();
            try {
                double ret = Double.parseDouble(value);
                this.engine.put(name, ret);
            }
            catch (NumberFormatException e) {
                this.engine.put(name, value);
            }
        }
        
        if (line.startsWith("imprimir")) {
            Matcher matcher = Pattern.compile("imprimir\\s(.*)").matcher(line);
            matcher.find();
            String expression = matcher.group(1);
            
            Matcher matcher2 = Pattern.compile("\\$(\\w+)").matcher(expression);
            ArrayList<String> variables =new ArrayList();
            while (matcher2.find()) {
                variables.add(matcher2.group());
            }
            
            for (int i = 0; i < variables.size(); i++) {
                String name = variables.get(i).replace("$", "");
                expression = expression.replaceAll("\\$" + name, this.engine.getBindings(ScriptContext.ENGINE_SCOPE).get(name).toString());
            }
            
            System.out.println(expression);
        }
        
        if (line.startsWith("ler")) {
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            try {
                double value = Double.parseDouble(str);
                this.engine.put(line.split(" ")[1], value);
            }
            catch (NumberFormatException e) {
                this.engine.put(line.split(" ")[1], str);
            }
        }
        
        if (line.startsWith("fazer")) {
            this.state = State.While;
        }
        
        if (line.startsWith("senao")) {
        } else if (line.startsWith("se")) {
            Matcher matcher = Pattern.compile("se\\s(.*)").matcher(line);
            matcher.find();
            String expression = matcher.group(1);
            this.ifIsTrue = this.engine.eval(expression).toString().equals("true");
            this.state = State.If;
        }
    }
}
