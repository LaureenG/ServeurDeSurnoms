package sds;
/**
 * 
 * @author Laureen Ginier
 * @version 1.0
 *
 */
public class Query {
    // Numero unique du service demande
    private int service;
    // Premier argument de la requete
    private String arg1;
    // Deuxieme argument de la requete
    private String arg2;
    // Id unique de la requete
    private static int queryID=0;
    
    /**
     * Instancie une requete pour le service dont le numero est passe en parametre.
     * Les arguments peuvent etre null, en fonction du service demande.
     * @param service numero du service demande
     * @param arg1 premier argument de la requete
     * @param arg2 deuxieme argument de la requete
     */
    public Query(int service, String arg1, String arg2){
        this.service = service;
        this.arg1 = arg1;
        this.arg2 = arg2;
        Query.queryID++;
    }
    
    /**
     * Retourne le numero du service demande
     * @return int no du service
     */
    public int getService(){
        return this.service;
    }
    
    /**
     * Retourne le premier argument de la requete
     * @return String arg1
     */
    public String getArg1(){
        return this.arg1;
    }
    
    /**
     * Retourne le deuxieme argument de la requete
     * @return String arg2
     */
    public String getArg2(){
        return this.arg2;
    }
    
    /**
     * Retourne l'id de la requete
     * @return int id unique de la requete
     */
    public int getQueryID(){
        return Query.queryID;
    }    
}
