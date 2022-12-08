package model;

public class Vote {
    private boolean parecer;
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isParecer() {
        return parecer;
    }

    public void setParecer(boolean parecer) {
        this.parecer = parecer;
    }

    public String render()
    {
        return usuario.getNome() + ": " + (isParecer() ? "favor" : "contra");
    }

    public String getVoteStructure()
    {
        return usuario.getNome() + ":" + (isParecer() ? "favor" : "contra");
    }
}
