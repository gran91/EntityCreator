/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package management.process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;
import zio.XMLManage;

/**
 *
 * @author Jeremy.CHAUT
 */
public class CreateEntity {

    private ArrayList header;
    private Object[][] field;
    private XMLManage xml;
    private String[] headerElmt = {"name", "idlng", "linkentity"};
    private String[] fieldElmt = {"name", "idlng", "type", "length", "mandatory", "exist", "editable", "case","table"};
    private String[] linkElmt = {"name", "column", "editable"};

    public CreateEntity(ArrayList h, Object[][] f) {
        header = h;
        field = f;
        run();
    }

    private boolean run() {
        xml = new XMLManage("entity", "information");
        xml.add(new ArrayList(Arrays.asList(headerElmt)), header);
        for (int i = 0; i < field.length; i++) {
            xml.add("entity", "fields", null, null);
            Element eField=xml.add("fields", "field", new ArrayList(Arrays.asList(fieldElmt)), new ArrayList(Arrays.asList(tools.Tools.extractPart(field[i], 0, 8))));
            eField.setAttribute("id", ""+i);
            if (!field[i][9].toString().isEmpty()) {
                xml.add(eField, "linkentity", new ArrayList(Arrays.asList(linkElmt)), new ArrayList(Arrays.asList(tools.Tools.extractPart(field[i], 9, 11))));
            }
        }
        try {
            xml.save("xmlModel" + System.getProperty("file.separator") + header.get(0).toString().trim().toLowerCase() + ".xml");
        } catch (IOException ex) {
            Logger.getLogger(CreateEntity.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
