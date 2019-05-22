package dk.asbjoern.foto.fotoorganiser.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Consumers  {


    public static final TriConsumer<Long, File, HashMap<Long, List<File>>> addToMap = (key, file, map) -> {
        List filesOfSameSize = map.get(key);
        if (filesOfSameSize == null) {
            filesOfSameSize = new ArrayList<File>();
            filesOfSameSize.add(file);
            map.put(key, filesOfSameSize);
        } else
            filesOfSameSize.add(file);

    };
}
