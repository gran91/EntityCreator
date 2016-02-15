/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.entity;

/**
 *
 * @author Jeremy.CHAUT
 */
public interface IUILinkEntity extends IUIEntity {

    public void enabledLink(boolean b);
    public String getLink();
    public void setLink(String s);
}
