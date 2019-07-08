package dk.asbjoern.foto.fotoorganiser.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logging is a cross-cutting concern
 *
 * @author asbjorn
 **/
public interface Loggable {
  default Logger logger() {
    return LoggerFactory.getLogger(this.getClass());
  }
}
