package tools;

public class Variable implements java.lang.Comparable {

	String variable = "";
	int numeroLigne = 0;
	
    public Variable(String s, int i) { 
    	variable = s; 
        numeroLigne = i; 
    } 

    public int getNumeroLigne()  { return numeroLigne;  }
    public String getVariable()  { return variable;  }

    public void setVariable(String variable) {
		this.variable = variable;
	}

	public void setNumeroLigne(int numeroLigne) {
		this.numeroLigne = numeroLigne;
	}

	public int compareTo(Object other) { 
        int nombre1 = ((Variable) other).getNumeroLigne(); 
        int nombre2 = this.getNumeroLigne(); 
        if (nombre1 > nombre2)  return -1; 
        else if(nombre1 == nombre2) return 0; 
        else return 1; 
     }
   
}
