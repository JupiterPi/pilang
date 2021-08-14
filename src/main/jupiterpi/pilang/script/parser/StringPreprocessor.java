package jupiterpi.pilang.script.parser;

import jupiterpi.tools.util.AppendingList;

public class StringPreprocessor {
    private String content;

    public StringPreprocessor(String content) {
        this.content = processStrings(content);
    }

    public String getContent() {
        return content;
    }

    /* string preprocessor */

    private boolean insideString = false;
    private String buffer = "";

    private String processStrings(String oldContent) {
        String content = "";

        for (String c : oldContent.split("")) {
            if (c.equals("\"")) {
                if (insideString) {
                    content += generateStringCode();
                    insideString = false;
                } else {
                    insideString = true;
                }
            } else {
                if (insideString) {
                    buffer += c;
                } else {
                    content += c;
                }
            }
        }

        return content;
    }

    private String generateStringCode() {
        AppendingList str = new AppendingList();
        for (String c : buffer.split("")) {
            str.add(String.format("'%s'", c));
        }
        return "[" + str.render(", ") + "]";
    }
}
