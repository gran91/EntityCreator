/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.tools;

/**
 *
 * @author sNiPeR91
 */
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * TextField that can be limited in size (max number of characters typed in)
 *
 * @author Jérémy Chaut
 *
 */
public class LimitedTextField extends JTextField {

    public static final int ALPHA = 0;
    public static final int ALPHA_WITHOUT_NUMERIC = 1;
    public static final int ALPHA_WITHOUT_SPECHAR = 2;
    public static final int ALPHA_WITHOUT_NUM_SPE = 3;
    public static final int NUMERIC = 4;
    public static final int IP = 5;
    private int type;
    private boolean up;
    private String[] charSpeWindows = {"\\", "/", ":", "*", "?", "\"", "<", ">", "|"};

    public LimitedTextField(int maxLength, int cas) {
        super();
        type = cas;
        up = false;
        AbstractDocument doc = (AbstractDocument) getDocument();
        if (cas == ALPHA || cas == ALPHA_WITHOUT_NUMERIC || cas == ALPHA_WITHOUT_NUM_SPE || cas == ALPHA_WITHOUT_SPECHAR) {
            doc.setDocumentFilter(new TextLimiter(maxLength));
        }
        if (cas == NUMERIC) {
            doc.setDocumentFilter(new TextLimiterNumeric(maxLength));
        }
        if (cas == IP) {
            doc.setDocumentFilter(new TextLimiterIP(maxLength));
        }
    }

    public LimitedTextField(int maxLength, int cas, boolean upper) {
        super();
        type = cas;
        up = upper;
        AbstractDocument doc = (AbstractDocument) getDocument();
        if (cas == ALPHA || cas == ALPHA_WITHOUT_NUMERIC || cas == ALPHA_WITHOUT_NUM_SPE || cas == ALPHA_WITHOUT_SPECHAR) {
            doc.setDocumentFilter(new TextLimiter(maxLength));
            /*
             * if (upper) { doc.setDocumentFilter(new
             * TextLimiterUpper(maxLength)); } else { doc.setDocumentFilter(new
             * TextLimiter(maxLength)); }
             */
        }
        if (cas == NUMERIC) {
            doc.setDocumentFilter(new TextLimiterNumeric(maxLength));
        }
        if (cas == IP) {
            doc.setDocumentFilter(new TextLimiterIP(maxLength));
        }
    }

    /**
     * Text limiter used to limit the number of characters of text fields Should
     * be used this way :
     *
     * AbstractDocument doc = (AbstractDocument) myTextComponent.getDocument();
     * doc.setDocumentFilter(new TextLimiter(maxLength));
     *
     * @author oma
     *
     */
    private class TextLimiter extends DocumentFilter {

        private int max;
        private ArrayList speChar = tools.Tools.convertArrayToArrayList(charSpeWindows, null);

        public TextLimiter(int max) {
            this.max = max;
        }

        public void insertString(DocumentFilter.FilterBypass fb, int offset, String str, AttributeSet attr) throws BadLocationException {

            replace(fb, offset, 0, str, attr);
        }

        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String str, AttributeSet attrs) throws BadLocationException {
            int newLength = fb.getDocument().getLength() - length + str.length();
            boolean isNum = false;
            boolean isSpe = false;
            if (newLength <= max) {
                switch (type) {
                    case ALPHA:
                        fb.replace(offset, length, str = (up) ? str.toUpperCase() : str, attrs);
                        break;
                    case ALPHA_WITHOUT_NUMERIC:
                        try {
                            if (!str.trim().equals("")) {
                                Integer.parseInt(str.trim());
                                isNum = true;
                            } else {
                                isNum = true;
                            }
                        } catch (Exception e) {
                            isNum = false;
                        }
                        if (!isNum) {
                            fb.replace(offset, length, str = (up) ? str.toUpperCase() : str, attrs);
                        }
                        break;
                    case ALPHA_WITHOUT_NUM_SPE:
                        try {
                            if (!str.trim().equals("")) {
                                Integer.parseInt(str.trim());
                                isNum = true;
                            } else {
                                isNum = true;
                            }
                        } catch (Exception e) {
                            isNum = false;
                        }
                        isSpe = (speChar.contains(str)) ? true : false;
                        if (!isNum && !isSpe) {
                            fb.replace(offset, length, str = (up) ? str.toUpperCase() : str, attrs);
                        }
                        break;
                    case ALPHA_WITHOUT_SPECHAR:
                        isSpe = (speChar.contains(str)) ? true : false;
                        if (!isSpe) {
                            fb.replace(offset, length, str = (up) ? str.toUpperCase() : str, attrs);
                        }
                        break;
                }
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    private class TextLimiterUpper extends DocumentFilter {

        private int max;

        public TextLimiterUpper(int max) {
            this.max = max;
        }

        public void insertString(DocumentFilter.FilterBypass fb, int offset, String str, AttributeSet attr) throws BadLocationException {

            replace(fb, offset, 0, str, attr);
        }

        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String str, AttributeSet attrs) throws BadLocationException {
            int newLength = fb.getDocument().getLength() - length + str.length();

            if (newLength <= max) {
                fb.replace(offset, length, str.toUpperCase(), attrs);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    private class TextLimiterNumeric extends DocumentFilter {

        private int max;

        public TextLimiterNumeric(int max) {
            this.max = max;
        }

        public void insertString(DocumentFilter.FilterBypass fb, int offset, String str, AttributeSet attr) throws BadLocationException {

            replace(fb, offset, 0, str, attr);
        }

        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String str, AttributeSet attrs) throws BadLocationException {
            int newLength = fb.getDocument().getLength() - length + str.length();
            boolean isNum = false;
            try {
                if (!str.trim().equals("")) {
                    Integer.parseInt(str.trim());
                    isNum = true;
                } else {
                    isNum = true;
                }
            } catch (Exception e) {
                isNum = false;
            }

            if (newLength <= max && isNum) {
                fb.replace(offset, length, str, attrs);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }

    private class TextLimiterIP extends DocumentFilter {

        private int max;

        public TextLimiterIP(int max) {
            this.max = max;
        }

        public void insertString(DocumentFilter.FilterBypass fb, int offset, String str, AttributeSet attr) throws BadLocationException {

            replace(fb, offset, 0, str, attr);
        }

        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String str, AttributeSet attrs) throws BadLocationException {
            int newLength = fb.getDocument().getLength() - length + str.length();
            boolean isNum = false;
            try {
                Integer.parseInt(str.trim());
                isNum = true;
            } catch (Exception e) {
                if (str.trim().contains(".")|| str.trim().isEmpty()) {
                    isNum = true;
                } else {
                    isNum = false;
                }
            }

            if (newLength <= max && isNum) {
                fb.replace(offset, length, str, attrs);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
}