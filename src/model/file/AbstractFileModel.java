/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.file;

import java.io.IOException;
import java.util.Observable;

/**
 *
 * @author JCHAUT
 */
public abstract class AbstractFileModel extends Observable implements IFileRunModel{
    @Override
    public void loadData()throws IOException{
        loadData(0, 0, -1, -1);
    }

    @Override
    public void loadData(int linebeg, int colbeg) throws IOException{
        loadData(linebeg, colbeg, -1, -1);
    }
}
