package pl.przemek.java.advanced.comparator.difference;

import java.nio.file.Path;

public abstract class FileDifference implements Comparable<FileDifference>{

    protected abstract Path getDifferentFile();

    public abstract String getMessage();

    @Override
    public int compareTo(FileDifference o) {
        return this.getDifferentFile().compareTo(o.getDifferentFile());
    }

}
