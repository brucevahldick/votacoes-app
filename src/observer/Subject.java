package observer;

public interface Subject {
    void notifyObservers();
    void addObserver(Observer o);
}
