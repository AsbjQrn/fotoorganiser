package dk.asbjoern.foto.fotoorganiser.helpers;

import java.util.List;
import java.util.function.Predicate;

public class Predikater {

    public static final Predicate<List> sizeGreaterThanOne = (list) -> {
        return list.size() > 1;
    };
}
