/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tabpane;

import java.awt.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;
import javax.swing.*;
import com.l2fprod.gui.plaf.skin.Skin;
import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;
/**
 * @author sNiPeR91
 */


public final class SkinTabbedPaneUI extends BasicTabbedPaneUI {

  private Skin skin = SkinLookAndFeel.getSkin();

	

  /**
   * Description of the Method
   *
   * @param g             Description of Parameter
   * @param tabPlacement  Description of Parameter
   * @param tabIndex      Description of Parameter
   * @param x             Description of Parameter
   * @param y             Description of Parameter
   * @param w             Description of Parameter
   * @param h             Description of Parameter
   * @param isSelected    Description of Parameter
   */


  protected void paintTabBorder(Graphics g, int tabPlacement,
      int tabIndex,
      int x, int y, int w, int h,
      boolean isSelected) {
    skin.getTab().paintTab(g, tabPlacement, isSelected, x, y, w, h);
  }
 

  protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex)
	

 {
    int width = tabPane.getWidth();
    int height = tabPane.getHeight();
    Insets insets = tabPane.getInsets();

    int x = insets.left;
    int y = insets.top;
    int w = width - insets.right - insets.left;
    int h = height - insets.top - insets.bottom;

    switch (tabPlacement) {
      case LEFT:
        x += calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
        w -= (x - insets.left);
        break;
      case RIGHT:
        w -= calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
        break;
      case BOTTOM:
        h -= calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
        break;
      case TOP:
      default:
        y += calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
        h -= (y - insets.top);
    }
    if (skin.getTab().paintContent(g, tabPlacement, selectedIndex, x, y, w, h) == false) {
      paintContentBorderTopEdge(g, tabPlacement, selectedIndex, x, y, w, h);
      paintContentBorderLeftEdge(g, tabPlacement, selectedIndex, x, y, w, h);
      paintContentBorderBottomEdge(g, tabPlacement, selectedIndex, x, y, w, h);
      paintContentBorderRightEdge(g, tabPlacement, selectedIndex, x, y, w, h);
    }
  }

 

  protected void paintTabBackground(Graphics g, int tabPlacement,
      int tabIndex,
      int x, int y, int w, int h,
      boolean isSelected) {
  }

  public static ComponentUI createUI(JComponent c)
	

 {
    return new SkinTabbedPaneUI();
  }



}