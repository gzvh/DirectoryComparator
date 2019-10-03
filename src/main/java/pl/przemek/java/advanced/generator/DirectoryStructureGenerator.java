package pl.przemek.java.advanced.generator;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryStructureGenerator {

    private static final long MEGA_BYTE = 1024 * 1024;
    private static Path root1 = Paths.get("dir1");
    private static Path root2 = Paths.get("dir2");

    public static void main(String[] args) throws IOException {
        generateStructure(root1, 5);
        generateStructure(root2, 5);
    }

    /**
     * check if this way works for windows (sparse file creation)
     * https://stackoverflow.com/questions/245251/create-file-with-given-size-in-java
     * https://en.wikipedia.org/wiki/Sparse_file
     */
    private static void generateStructure(Path root, double dirsNumber) throws IOException {
        Files.createDirectories(root);

        createDummyFiles(root, 10, 50 * MEGA_BYTE);
        for (int i = 0; i < dirsNumber; i++) {
            Path innerDirPath = root.resolve("dir" + i);
            Files.createDirectories(innerDirPath);
            createDummyFiles(innerDirPath, 10, 50 * MEGA_BYTE);
        }
    }

    private static void createDummyFiles(Path root, int filesNumber, long fileSize) throws IOException {
        for (int i = 0; i < filesNumber; i++) {
            try (RandomAccessFile raf = new RandomAccessFile(root.resolve("file" + i).toFile(), "rw")) {
                raf.setLength(fileSize);
                raf.seek(fileSize);
                raf.writeChars("dummy" + i);
            }
        }
    }

}
