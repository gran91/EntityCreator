package ui.tools;

import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import javax.swing.*;
import main.Ressource;

/**
 *
 * @author Jeremy.CHAUT
 */
public class MenuBarDefault extends JMenuBar {

    protected JMenu mainMenu, editMenu, lngMenu, uiMenu, encodingMenu, helpMenu;
    protected ButtonGroup encodingGroup, languageGroup, lngGroup;
    protected JMenuItem itemNew, itemOpen, itemSave, itemSaveAs, itemSaveAll, itemQuit;
    protected JMenuItem itemCut, itemCopy, itemPaste;
    protected JMenuItem itemAbout, itemTip;
    protected JMenuItem itemFR, itemEN;
    protected List<JMenuItem> listItem;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public MenuBarDefault() {
        listItem = new ArrayList<>();
    }

    public void allMenu() {
        mainMenu();
        editMenu();
        helpMenu();
    }

    public void mainMenu() {
        mainMenu = new JMenu(i18n.Language.getLabel(34));
        addNewMenu(mainMenu);
        addOpenMenu(mainMenu);
        addSaveMenu(mainMenu);
        addSaveAsMenu(mainMenu);
        addSaveAllMenu(mainMenu);
        addQuitMenu(mainMenu);
        this.add(mainMenu);
    }

    public void addNewMenu(JMenu menu) {
        itemNew = new JMenuItem(i18n.Language.getLabel(139));
        itemNew.setActionCommand("new");
        itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        menu.add(itemNew);
        listItem.add(itemNew);
    }

    public void addOpenMenu(JMenu menu) {
        itemOpen = new JMenuItem(i18n.Language.getLabel(140));
        itemOpen.setActionCommand("open");
        itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        menu.add(itemOpen);
        listItem.add(itemOpen);
    }

    public void addSaveMenu(JMenu menu) {
        itemSave = new JMenuItem(i18n.Language.getLabel(141));
        itemSave.setActionCommand("save");
        itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        menu.add(itemSave);
        listItem.add(itemSave);
    }

    public void addSaveAsMenu(JMenu menu) {
        itemSaveAs = new JMenuItem(i18n.Language.getLabel(142));
        itemSaveAs.setActionCommand("saveas");
        itemSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK + KeyEvent.ALT_MASK));
        menu.add(itemSaveAs);
        listItem.add(itemSaveAs);
    }

    public void addSaveAllMenu(JMenu menu) {
        itemSaveAll = new JMenuItem(i18n.Language.getLabel(143));
        itemSaveAll.setActionCommand("saveall");
        itemSaveAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK + KeyEvent.SHIFT_MASK));
        menu.add(itemSaveAll);
        listItem.add(itemSaveAll);
    }

    public void addQuitMenu(JMenu menu) {
        itemQuit = new JMenuItem(i18n.Language.getLabel(35));
        itemQuit.setActionCommand("quit");
        itemQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_MASK));
        menu.add(itemQuit);
        listItem.add(itemQuit);
    }

    public void editMenu() {
        editMenu = new JMenu(i18n.Language.getLabel(150));
        itemCopy = new JMenuItem(i18n.Language.getLabel(151));
        itemCopy.setActionCommand("copy");
        itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
        itemCopy.setEnabled(false);
        editMenu.add(itemCopy);
        listItem.add(itemCopy);
        itemCut = new JMenuItem(i18n.Language.getLabel(152));
        itemCut.setActionCommand("cut");
        itemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
        itemCut.setEnabled(false);
        editMenu.add(itemCut);
        listItem.add(itemCut);
        itemPaste = new JMenuItem(i18n.Language.getLabel(153));
        itemPaste.setActionCommand("paste");
        itemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
        editMenu.add(itemPaste);
        listItem.add(itemPaste);
        this.add(editMenu);
    }

    public void encodingMenu() {
        encodingMenu = new JMenu(i18n.Language.getLabel(154));
        new MenuScroller(encodingMenu);
        JRadioButtonMenuItem item;
        JMenu typeitem = null;
        encodingGroup = new ButtonGroup();
        ArrayList<String> exclu = new ArrayList<String>();
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        for (String nom : charsets.keySet()) {
            if (!nom.startsWith("x") && !nom.startsWith("X")) {
                String typeCharset = nom.substring(0, 3);
                if (!exclu.contains(typeCharset)) {
                    if (typeitem != null) {
                        encodingMenu.add(typeitem);
                    }
                    typeitem = new JMenu(typeCharset);
                    listItem.add(typeitem);
                    exclu.add(typeCharset);
                }
                item = new JRadioButtonMenuItem(nom);
                item.setActionCommand("encoding_" + nom);
                typeitem.add(item);
                encodingGroup.add(item);
            }
        }
        this.add(encodingMenu);
    }

    public void uiMenu() {
        String s=Ressource.conf.getConfig().getProperty("config.ui");
        if(s==null)s="";
        uiMenu = new JMenu("UI");
        new MenuScroller(uiMenu);
        JRadioButtonMenuItem item;
        ButtonGroup uiGroup = new ButtonGroup();
        item = new JRadioButtonMenuItem(i18n.Language.getLabel(230));
        item.setActionCommand("ui_" + 0);
        uiGroup.add(item);
        uiMenu.add(item);
        listItem.add(item);
        item.setSelected(s.equals("0")||s.isEmpty());
        item = new JRadioButtonMenuItem(i18n.Language.getLabel(231));
        item.setActionCommand("ui_" + 1);
        uiGroup.add(item);
        uiMenu.add(item);
        listItem.add(item);
        item.setSelected(s.equals("1"));
        this.add(uiMenu);
    }

    public void lngMenu() {
        lngMenu = new JMenu(i18n.Language.getLabel(156));
        new MenuScroller(lngMenu);
        JRadioButtonMenuItem item;
        lngGroup = new ButtonGroup();
        ArrayList<File> l = new ArrayList<File>();
        l = zio.ToolsFile.listFiles(Ressource.XML_LNG, ".xml", l);
        if (l != null) {
            for (int i = 0; i < l.size(); i++) {
                String s = l.get(i).getName().substring(8, 10);
                item = new JRadioButtonMenuItem(s);
                item.setActionCommand("lng_" + s);
                if (s.equals(Ressource.conf.getConfig().getProperty("config.lang").toUpperCase())) {
                    item.setSelected(true);
                }
                lngGroup.add(item);
                lngMenu.add(item);
                listItem.add(item);
            }
            this.add(lngMenu);
        }
    }

    public void helpMenu() {
        helpMenu = new JMenu("?");
        itemAbout = new JMenuItem("About");
        itemAbout.setActionCommand("about");
        itemAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, KeyEvent.CTRL_MASK));
        helpMenu.add(itemAbout);
        listItem.add(itemAbout);
        itemTip = new JMenuItem(i18n.Language.getLabel(171));
        itemTip.setActionCommand("tip");
        itemTip.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        helpMenu.add(itemTip);
        listItem.add(itemTip);
        this.add(helpMenu);
    }

    public JMenu getEncodingMenu() {
        return encodingMenu;
    }

    public void setEncodingMenu(JMenu encodingMenu) {
        this.encodingMenu = encodingMenu;
    }

    public ButtonGroup getEncodingGroup() {
        return encodingGroup;
    }

    public void setEncodingGroup(ButtonGroup encodingGroup) {
        this.encodingGroup = encodingGroup;
    }

    public ButtonGroup getLanguageGroup() {
        return languageGroup;
    }

    public void setLanguageGroup(ButtonGroup languageGroup) {
        this.languageGroup = languageGroup;
    }

    public JMenuItem getItemCopy() {
        return itemCopy;
    }

    public void setItemCopy(JMenuItem itemCopy) {
        this.itemCopy = itemCopy;
    }

    public JMenuItem getItemCut() {
        return itemCut;
    }

    public void setItemCut(JMenuItem itemCut) {
        this.itemCut = itemCut;
    }

    public JMenuItem getItemNew() {
        return itemNew;
    }

    public void setItemNew(JMenuItem itemNew) {
        this.itemNew = itemNew;
    }

    public JMenuItem getItemOpen() {
        return itemOpen;
    }

    public void setItemOpen(JMenuItem itemOpen) {
        this.itemOpen = itemOpen;
    }

    public JMenuItem getItemPaste() {
        return itemPaste;
    }

    public void setItemPaste(JMenuItem itemPaste) {
        this.itemPaste = itemPaste;
    }

    public JMenuItem getItemQuit() {
        return itemQuit;
    }

    public void setItemQuit(JMenuItem itemQuit) {
        this.itemQuit = itemQuit;
    }

    public JMenuItem getItemSave() {
        return itemSave;
    }

    public void setItemSave(JMenuItem itemSave) {
        this.itemSave = itemSave;
    }

    public JMenuItem getItemSaveAll() {
        return itemSaveAll;
    }

    public void setItemSaveAll(JMenuItem itemSaveAll) {
        this.itemSaveAll = itemSaveAll;
    }

    public JMenuItem getItemSaveAs() {
        return itemSaveAs;
    }

    public void setItemSaveAs(JMenuItem itemSaveAs) {
        this.itemSaveAs = itemSaveAs;
    }

    public JMenu getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(JMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    public JMenuItem getItemFR() {
        return itemFR;
    }

    public void setItemFR(JMenuItem itemFR) {
        this.itemFR = itemFR;
    }

    public JMenuItem getItemEN() {
        return itemEN;
    }

    public void setItemEN(JMenuItem itemEN) {
        this.itemEN = itemEN;
    }

    public List<JMenuItem> getListItem() {
        return listItem;
    }

    public void setListItem(List<JMenuItem> listItem) {
        this.listItem = listItem;
    }
}
