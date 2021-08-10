package jupiterpi.pilang.script.parser;

public class CommentPreprocessor {
    private String content;

    public CommentPreprocessor(String scriptName, String content) {
        for (String invalidCharacter : new String[]{"ä", "ö", "ü", "ß"}) {
            if (content.contains(invalidCharacter)) {
                new Exception("script " + scriptName + " contains invalid character " + invalidCharacter).printStackTrace();
            }
        }
        this.content = removeComments(content);
    }

    public String getContent() {
        return content;
    }

    /* comment preprocessor */

    private final String LINE_COMMENT = "ä";
    private final String BLOCK_COMMENT_START = "ö";
    private final String BLOCK_COMMENT_END = "ü";

    private boolean insideLineComment = false;
    private boolean insideBlockComment = false;

    private String removeComments(String oldContent) {
        String content = "";

        oldContent = oldContent.replace("//", LINE_COMMENT);
        oldContent = oldContent.replace("/*", BLOCK_COMMENT_START);
        oldContent = oldContent.replace("*/", BLOCK_COMMENT_END);

        for (String c : oldContent.split("")) {
            if (c.equals("\n")) {
                insideLineComment = false;
            }
            if (insideBlockComment) {
                if (c.equals(BLOCK_COMMENT_END)) {
                    insideBlockComment = false;
                }
                continue;
            } else {
                if (insideLineComment) {
                    continue;
                }
                if (c.equals(LINE_COMMENT)) {
                    insideLineComment = true;
                    continue;
                }
                if (c.equals(BLOCK_COMMENT_START)) {
                    insideBlockComment = true;
                    continue;
                }
            }
            if (c.equals(BLOCK_COMMENT_END)) c = "*/";
            content += c;
        }

        return content;
    }
}
