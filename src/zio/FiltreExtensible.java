package zio;
import java.io.File; 
import java.util.*; 
import javax.swing.filechooser.*;

public class FiltreExtensible extends FileFilter{
	//Description et extensions accept�es par le filtre
	private String description;
	private List<String> extensions;
	//Constructeur � partir de la description
	public FiltreExtensible(String description){
		if(description == null){
			throw new NullPointerException("La description ne peut �tre null.");	
		}
		this.description = description;
		this.extensions = new ArrayList<String>();
	}
	//Impl�mentation de FileFilter
	public boolean accept(File file){
		if(file.isDirectory() || extensions.size()==0) { 
			return true; 
		} 
		String nomFichier = file.getName().toLowerCase(); 
		for(String extension : extensions){
			if(nomFichier.endsWith(extension)){
				return true;
			}	
		}
		return false;
	}
	public String getDescription(){
		StringBuffer buffer = new StringBuffer(description);
		buffer.append(" (");
		for(String extension : extensions){
			buffer.append(extension).append(" ");	
		}	
		return buffer.append(")").toString();
	}	
	//Quelques m�thodes utilitaires
	public void setDescription(String description){
		if(description == null){
			throw new NullPointerException("La description ne peut �tre null.");	
		}
		this.description = description;
	}
	public void addExtension(String extension){
		if(extension == null){
			throw new NullPointerException("Une extension ne peut �tre null.");	
		}	
		extensions.add(extension);
	}
	public void removeExtension(String extension){
		extensions.remove(extension);	
	}
	public void clearExtensions(){
		extensions.clear();
	}
	public List<String> getExtensions(){
		return extensions;	
	}
	
}
