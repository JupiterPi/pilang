package jupiterpi.pilang.script;

import jupiterpi.pilang.script.instructions.Instruction;
import jupiterpi.pilang.script.parser.*;
import jupiterpi.pilang.script.runtime.Scope;
import jupiterpi.pilang.script.runtime.Variable;

import java.util.ArrayList;
import java.util.List;

public class Script extends Scope {
    private String name;

    public String getName() {
        return name;
    }

    /* constructor */

    public Script(String name, String content) {
        this.name = name;
        generateInstructions(content);
    }

    public Script(String name) {
        this.name = name;
    }

    //region generate instructions

    private List<Instruction> instructions;

    private void generateInstructions(String content) {
        content = applyPreprocessors(content);
        List<TokenSequence> lines = applyLexer(content);
        instructions = applyParser(lines);
    }

    private String applyPreprocessors(String oldContent) {
        String content = new CommentPreprocessor(name, oldContent).getContent();
        content = new StringPreprocessor(content).getContent();
        return content;
    }

    private List<TokenSequence> applyLexer(String content) {
        List<TokenSequence> lines = new ArrayList<>();
        for (String line : content.split(";")) {
            Lexer lexer = new Lexer(line);
            TokenSequence tokens = new TokenSequence(lexer.getTokens());
            if (tokens.size() == 0) continue;
            lines.add(tokens);
        }
        return lines;
    }

    private List<Instruction> applyParser(List<TokenSequence> lines) {
        Parser parser = new Parser(lines);
        return parser.getInstructions();
    }

    //endregion

    /* execute */

    private List<Script> availableScripts;
    private List<Script> noticedScripts = new ArrayList<>();
    private List<Script> integratedScripts = new ArrayList<>();

    public void run(List<Script> availableScripts) {
        this.availableScripts = new ArrayList<>(availableScripts);
        super.execute(instructions);

        System.out.println("variables in script " + name + ": ");
        for (Variable variable : getVariables()) {
            System.out.println(variable);
        }
    }

    @Override
    public Variable getVariable(String name) {
        if (name.contains(".")) {
            String[] parts = name.split("\\.");
            if (parts[0].equals("self")) {
                return getLocalVariable(parts[1]);
            }
            for (Script script : noticedScripts) {
                if (script.getName().equals(parts[0])) {
                    return script.getVariable(parts[1]);
                }
            }
        }
        Variable variable = getLocalVariable(name);
        if (variable != null) {
            return variable;
        } else {
            for (Script integratedScript : integratedScripts) {
                Variable v = integratedScript.getLocalVariable(name);
                if (v != null) return v;
            }
        }

        new Exception("couldn't find variable " + name).printStackTrace();
        return null;
    }
    private Variable getLocalVariable(String name) {
        return super.getVariable(name);
    }

    public void importScript(boolean integrate, String scriptName) {
        for (Script availableScript : availableScripts) {
            if (availableScript.getName().equals(scriptName)) {
                if (integrate) addScript(integratedScripts, "integrated", availableScript);
                addScript(noticedScripts, "noticed", availableScript);
                return;
            }
        }
        new Exception("tried to import script " + scriptName + ", but it doesn't exist!").printStackTrace();
    }
    private void addScript(List<Script> list, String listName, Script script) {
        for (Script s : list) {
            if (s.getName().equals(script.getName())) {
                new Exception("tried to add script " + script.getName() + " to " + listName + " scripts, but it's already there!").printStackTrace(); return;
            }
        }
        list.add(script);
    }
}
