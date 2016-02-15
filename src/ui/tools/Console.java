package ui.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.JTextPane;
import javax.swing.text.*;
import org.jdesktop.swingx.JXPanel;

public class Console extends JXPanel implements ActionListener, WindowListener, Runnable {
    private Thread reader;
    private Thread reader2;
    private boolean quit;
    private JScrollPane scroll;
    private final PipedInputStream pin = new PipedInputStream();
    private final PipedInputStream pin2 = new PipedInputStream();
    private JTextPane textPane;
    private StyledDocument textArea;
    private SimpleAttributeSet red;
    private Thread errorThrower;
    
    public Console() {
        super(new BorderLayout(),true);
        textPane = new JTextPane();
        textArea = textPane.getStyledDocument();
        red = new SimpleAttributeSet();
        StyleConstants.setForeground(red, Color.red);
        textPane.setEditable(false);
        textPane.setFont(new Font("Arial", Font.PLAIN, 10));
        textPane.setCaretPosition(textPane.getDocument().getLength());
        JButton button = new JButton(i18n.Language.getLabel(16));
        scroll = new JScrollPane(textPane);
        scroll.setAutoscrolls(true);
        this.add(scroll, BorderLayout.CENTER);
        this.add(button, BorderLayout.SOUTH);
        setVisible(false);
        button.addActionListener(this);
        redirect();
    }

    public void setSize(int w, int h) {
        this.setSize(w, h);
    }

    public void quitter() {
        this.setVisible(false);
    }

    public void windowDeactivated(java.awt.event.WindowEvent e) {
    }

    public void windowDeiconified(java.awt.event.WindowEvent e) {
    }

    public void windowIconified(java.awt.event.WindowEvent e) {
    }

    public void windowOpened(java.awt.event.WindowEvent e) {
    }

    public void windowActivated(java.awt.event.WindowEvent e) {
    }

    public void windowClosed(java.awt.event.WindowEvent e) {
    }

    public void windowClosing(java.awt.event.WindowEvent e) {
        quitter();
    }

    /**
     * Redirection des flux
     *
     */
    private void redirect() {
        try {
            PipedOutputStream pout = new PipedOutputStream(pin);
            System.setOut(new PrintStream(pout, true));
        } catch (java.io.IOException io) {
            error(io, null);
        } catch (SecurityException se) {
            error(null, se);
        }

        try {
            PipedOutputStream pout2 = new PipedOutputStream(pin2);
            System.setErr(new PrintStream(pout2, true));
        } catch (java.io.IOException io) {
            error(io, null);

        } catch (SecurityException se) {
            error(null, se);
        }
        quit = false;
        reader = new Thread(this);
        reader.setDaemon(true);
        reader.start();
        reader2 = new Thread(this);
        reader2.setDaemon(true);
        reader2.start();
        errorThrower = new Thread(this);
        errorThrower.setDaemon(true);
        errorThrower.start();
    }

    public void error(java.io.IOException io, SecurityException se) {
        try {
            textArea.insertString(textArea.getLength(), "Redirection de la sortie standard out impossible dans la console\n" + io.getMessage(), null);
        } catch (BadLocationException exception) {
        }

        try {
            textArea.insertString(textArea.getLength(), "Redirection de la sortie standard out impossible dans la console\n" + se.getMessage(), null);
        } catch (BadLocationException exception) {
        }

        try {
            textArea.insertString(textArea.getLength(), "Redirection de la sortie standard error impossible dans la console\n" + io.getMessage(), null);
        } catch (BadLocationException exception) {
        }

        try {
            textArea.insertString(textArea.getLength(), "Redirection de la sortie standard out impossible dans la console\n" + se.getMessage(), null);
            textPane.setCaretPosition(textPane.getDocument().getLength());
        } catch (BadLocationException exception) {
        }
    }

    public synchronized void actionPerformed(ActionEvent evt) {
        try {
            textArea.remove(0, textArea.getLength());
            textPane.setCaretPosition(textPane.getDocument().getLength());
        } catch (BadLocationException exception) {
        }
    }

    /**
     * Lancement des threads
     */
    public synchronized void run() {
        try {
            while (Thread.currentThread() == reader) {
                try {
                    this.wait(100);
                } catch (InterruptedException ie) {
                }
                if (pin.available() != 0) {
                    String input = this.readLine(pin);
                    textArea.insertString(textArea.getLength(), input, null);
                    textPane.setCaretPosition(textPane.getDocument().getLength());
                }
                if (quit) {
                    return;
                }
            }

            while (Thread.currentThread() == reader2) {
                try {
                    this.wait(100);
                } catch (InterruptedException ie) {
                }
                if (pin2.available() != 0) {
                    String input = this.readLine(pin2);
                    textArea.insertString(textArea.getLength(), input, red);
                    textPane.setCaretPosition(textPane.getDocument().getLength());
                }
                if (quit) {
                    return;
                }
            }
        } catch (Exception e) {
            try {
                textArea.insertString(textArea.getLength(), "\nLa console a relevé une erreur interne.", null);
                textArea.insertString(textArea.getLength(), "L'erreur est: " + e, null);
                textPane.setCaretPosition(textPane.getDocument().getLength());
            } catch (BadLocationException exception) {
            }
        }
    }

    /**
     * Lecture de la sortie standard
     */
    protected synchronized String readLine(PipedInputStream in) throws IOException {
        String input = "";
        do {
            int available = in.available();
            if (available == 0) {
                break;
            }
            byte b[] = new byte[available];
            in.read(b);
            input = input + new String(b, 0, b.length);
        } while (!input.endsWith("\n") && !input.endsWith("\r\n") && !quit);
        return input;
    }

    /**
     * Est appel� automatiquement a la sortie du programme
     */
    public void internalFrameClosed(InternalFrameEvent e) {

        quit = true;
        this.notifyAll(); // stop all threads
        try {
            reader.join(1000);
            pin.close();
        } catch (Exception ex) {
        }
        try {
            reader2.join(1000);
            pin2.close();
        } catch (Exception ex) {
        }
    }

    public void internalFrameIconified(InternalFrameEvent e) {
    }

    public void internalFrameDeiconified(InternalFrameEvent e) {
    }

    public void internalFrameActivated(InternalFrameEvent e) {
    }

    public void internalFrameDeactivated(InternalFrameEvent e) {
    }
}