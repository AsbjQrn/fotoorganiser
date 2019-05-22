package dk.asbjoern.foto.fotoorganiser.helpers;

public interface TriConsumer<X, Y, Z> {

    void accept(X x, Y y, Z z);
    
}
