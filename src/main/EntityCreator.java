package main;

import controler.AbstractControler;
import controler.entities.DefaultControlerEntities;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import management.ui.UICreateEntity;
import management.ui.UIManageLanguage;
import model.entities.XMLEntitiesModel;
import ui.entities.XMLUIChooseEntity;
import ui.entities.XMLUILink;
import ui.entities.XMLUITableEntities;
import ui.popup.UIPopup;

/**
 *
 * @author jchaut
 */
public class EntityCreator {

    private String dir;
    
    public EntityCreator() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                init();
                start();

                UICreateEntity crt = new UICreateEntity();
                JFrame f = ui.tools.UITools.createFrame("Create Entity", crt);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//                UICreateRibbonTask crtrt = new UICreateRibbonTask();
//                JFrame frt = ui.tools.UITools.createFrame("Create RibbonTask", crtrt);
//                frt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                UIManageLanguage lng = new UIManageLanguage();
                JFrame flng = ui.tools.UITools.createFrame("Manage Language", lng);
                flng.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//                XMLEntityModel m = new XMLEntityModel("environnment");
//                m.loadData(1);
//                XMLUIEntity v = new XMLUIEntity(m);
//               XMLControlerEntity c = new XMLControlerEntity(v);
//                
//                XMLEntitiesModel modelLink = new XMLEntitiesModel("customer");
//                XMLEntitiesModel modeltype = new XMLEntitiesModel("wifi");
//                XMLEntitiesModel model = new XMLEntitiesModel("environnment");
//
//                XMLUITableEntities viewTable = new XMLUITableEntities(model);
//                viewTable.setPopup(new UIPopup());
//
//                XMLUIChooseEntity view = new XMLUIChooseEntity(model);
//                view.setContainerType(-1);
//                XMLUIChooseEntity view1 = new XMLUIChooseEntity(modeltype);
//                view1.setContainerType(-1);
//                ArrayList<AbstractControler> lstView = new ArrayList<>();
//                lstView.add(new DefaultControlerEntities(view));
//                lstView.add(new DefaultControlerEntities(view1));
//                XMLUILink viewLink = new XMLUILink(modelLink, lstView);
//                DefaultControlerEntities control = new DefaultControlerEntities(view);
//                new DefaultControlerEntities(viewLink);

                //new DefaultControlerEntities(viewTable);

//                DefaultEntitiesModel m = new DefaultEntitiesModel("", Ressource.pathCustFile, Ressource.xmlCust, new DefaultEntityModel(Ressource.pathCustFile, Ressource.xmlCust));
//                m.setColumnsHeader(Ressource.headerDefault);
//                m.loadData();
//                DefaultUITableEntities v = new DefaultUITableEntities(m, new DefaultUIEntity(new DefaultEntityModel(Ressource.pathCustFile, Ressource.xmlCust)));
//                new DefaultControlerEntities(m, v);
//              

//                EnvironmentModel model=new EnvironmentModel("ALEHOS");
//                model.loadData(1);
//                UIEnvironment view=new UIEnvironment(model);
//                DefaultControlerEntity control=new DefaultControlerEntity(model, view);
//                Pmain p = new Pmain();
//                p.load();
//                p.showInFrame();
//                p.addMenu(new ui.menu.Menu(p));
            }
        });
    }

    public static void main(String[] args) {
        new EntityCreator();
    }

    public void init() {
        dir = Ressource.conf.getConfig().getProperty("config.skin");
    }

    private void start() {
        try {
//            File f = new File(dir);
//            if (f.exists()) {
//                Skin theSkinToUse = SkinLookAndFeel.loadThemePack(dir);
//                SkinLookAndFeel.setSkin(theSkinToUse);
//                UIManager.setLookAndFeel(new SkinLookAndFeel());
//            } else {
//                UIManager.setLookAndFeel(new NimbusLookAndFeel());
//            }
            
            for (UIManager.LookAndFeelInfo laf :
                    UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(laf.getName())) {
                    try {
                        UIManager.setLookAndFeel(laf.getClassName());
                    } catch (Exception e) {
                        // TODO: handle exception
                    }

                }
            }


//            try {
//                UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
//            } catch (Exception e) {
//            }
//            Plastic3DLookAndFeel.setPlasticTheme(new DesertBlue());



        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Skin: " + dir + " Error", "Skin", JOptionPane.ERROR_MESSAGE);
        }
    }
}
