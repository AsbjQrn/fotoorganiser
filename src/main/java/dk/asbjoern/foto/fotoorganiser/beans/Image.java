package dk.asbjoern.foto.fotoorganiser.beans;

import com.drew.metadata.Metadata;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;

public class Image {

    private Optional<Metadata> metadata;

    private Optional<LocalDate> dateTaken = Optional.ofNullable(null);

    private String md5sum;

    /*
    * Top folder(s) original location*/
    private Path basePath;

    private Path pathOriginalLocation;

    private Path pathToNewLocation;

    /*
    filnavn.jpg
     */
    private Path filename;


    public Optional<Metadata> getMetadata() {
        return metadata;
    }

    public void setMetadata(Optional<Metadata> metadata) {
        this.metadata = metadata;
    }

    public Optional<LocalDate> getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Optional<LocalDate> dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getMd5sum() {
        return md5sum;
    }

    public void setMd5sum(String md5sum) {
        this.md5sum = md5sum;
    }

    public Path getPathOriginalLocation() {
        return pathOriginalLocation;
    }


    public String getFilePathOriginalLocationAsString() {
        return pathOriginalLocation.resolve(this.filename).toAbsolutePath().toString();
    }

    public void setPathOriginalLocation(Path pathOriginalLocation) {
        this.pathOriginalLocation = pathOriginalLocation;
    }


    public void setFilename(Path filename) {
        this.filename = filename;
    }

    public Path createAndGetNewLocation(){
        return pathToNewLocation.resolve(filename);
    }

    public Path getBasePath() {
        return basePath;
    }

    public void setBasePath(Path basePath) {
        this.basePath = basePath;
    }

    public Path getPathToNewLocation() {
        return pathToNewLocation;
    }

    public void setPathToNewLocation(Path pathToNewLocation) {
        this.pathToNewLocation = pathToNewLocation;
    }

    public Path getPathOriginalLocationAndFilename(){
        return pathOriginalLocation.resolve(filename);
    }

    public Path getPathToNewLocationAndFilename(){
        return pathToNewLocation.resolve(filename);
    }

    public Path getFilename() {
        return filename;
    }

    @Override
    public String toString() {
        return "Image{" +
                "md5sum='" + md5sum + '\'' +
                ", pathOriginalLocation=" + pathOriginalLocation.toString() +
                ", filename=" + filename +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        return md5sum.equals(image.md5sum);
    }

    @Override
    public int hashCode() {
        return md5sum.hashCode();
    }
}
