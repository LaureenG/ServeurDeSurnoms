import java.util.List;

/**
 * 
 * @author Laureen Ginier
 * @version 1.0
 *
 */
public class Reply {
    // Numero unique du service consulte
    private int service;
    // Code d'erreur
    private int codeError;
    // Précise le type de donnée retournée
    private String dataType; 
    // Donnée envoyée : liste d’objets (le type de l’objet est précisé par la variable “dataType”)
    private List<Object> data;
    // Identifiant de la requete
    private int queryID;
    
    /**
     * Instancie une reponse a une requete pour le service dont le numero est passe en parametre
     * @param service numero du service consulte
     * @param codeError int code de retour du service effectue
     * @param dataType String type de données envoyées
     * @param data List de données
     * @param queryID int numero de la requete a laquelle on repond
     */
    public Reply(int service, int codeError, String dataType, List<Object> data, int queryID){
        this.service = service;
        this.codeError = codeError;
        this.dataType = dataType;
        this.data = data;
        this.queryID = queryID;
    }
    
    /**
     * Retourne le numero du service consulte
     * @return int no du service
     */
    public int getService(){
        return this.service;
    }
    
    /**
     * Retourne le premier argument de la requete
     * @return String arg1
     */
    public int getCodeError(){
        return this.codeError;
    }
    
    /**
     * Retourne le deuxieme argument de la requete
     * @return String arg2
     */
    public String getDataType(){
        return this.dataType;
    }
    
    /**
     * Retourne les données à envoyer
     * @return List d'objects
     */
    private List<Object> getData(){
        return this.data;
    }
    
    /**
     * Retourne l'id de la requete
     * @return int id unique de la requete
     */
    public int getQueryID(){
        return this.queryID;
    }    
}
