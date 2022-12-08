package model;

public class Config {
    private Usuario usuario;
    private String ip;

    public Config(Usuario usuario, String ip) {
        this.usuario = usuario;
        this.ip = ip;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
