package caso3;

public class Rastreador {

    private Boolean encontrado;

    private String password;

    public Rastreador ()
    {
        encontrado = false;
    }

    public synchronized Boolean getRastreador ()
    {
        return encontrado;
    }

    public synchronized void setRastreador (Boolean encontrado)
    {
        this.encontrado = encontrado;
    }

    public synchronized String getPassword ()
    {
        return password;
    }

    public synchronized void setPassword (String password)
    {
        this.password = password;
    }
    
}
