/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.tools;

import java.awt.Desktop;
import java.io.File;

/**
 *
 * @author Jeremy.CHAUT
 */
public class HTMLHelper {

    public HTMLHelper(String u) {
        Desktop desktop = null;
        java.net.URI url = null;
        try {
            if (u.startsWith("http")) {
                url = new java.net.URI(u);
            } else {
                File f = new File(u);
                if (f.exists()) {
                    url = f.toURI();
                }
            }
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
                desktop.browse(url);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
