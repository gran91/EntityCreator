/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.file;

import java.io.IOException;

/**
 *
 * @author JCHAUT
 */
public interface IFileRunModel {

    public void setFileName(String s);

    public Object[][] getData();

    public void loadData() throws IOException;

    public void loadData(int linebeg, int colbeg) throws IOException;

    public void loadData(int linebeg, int colbeg, int lineend, int colend) throws IOException;

    public void launchData();
}
