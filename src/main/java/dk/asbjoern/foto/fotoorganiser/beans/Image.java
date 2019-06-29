package dk.asbjoern.foto.fotoorganiser.beans;

import com.drew.metadata.Metadata;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;

public class Image {

    private Optional<Metadata> metadata;

    private Optional<LocalDate> dateTaken = Optional.ofNullable(null);

    private String md5sum;

    private String sourceBibliotek;

    private String originalLocation;

    private String parentPathToNewLocation;

    /*
    Stien før filnavnet /bib/bib1/bib2
     */
    private Path parentPathOriginalLocation;

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

    public String getOriginalLocation() {
        return originalLocation;
    }

    public void setOriginalLocation(String originalLocation) {
        this.originalLocation = originalLocation;
    }

    public String getParentPathToNewLocation() {
        return parentPathToNewLocation;
    }

    public void setParentPathToNewLocation(String parentPathToNewLocation) {
        this.parentPathToNewLocation = parentPathToNewLocation;
    }

    public Path getParentPathOriginalLocation() {
        return parentPathOriginalLocation;
    }

    public void setParentPathOriginalLocation(Path parentPathOriginalLocation) {
        this.parentPathOriginalLocation = parentPathOriginalLocation;
    }


    public void setFilename(Path filename) {
        this.filename = filename;
    }

    public String createAndGetNewLocation(){
        return getParentPathToNewLocation().toString() + "/" +  filename.toString();
    }

    public String getSourceBibliotek() {
        return sourceBibliotek;
    }

    public void setSourceBibliotek(String sourceBibliotek) {
        this.sourceBibliotek = sourceBibliotek;
    }
}
