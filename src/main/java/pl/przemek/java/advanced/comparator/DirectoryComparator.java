package pl.przemek.java.advanced.comparator;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.tuple.Pair;
import pl.przemek.java.advanced.comparator.difference.FileDifference;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class DirectoryComparator {

    public static final int MEGA_BYTE = 1024 * 1024;
    public static final int BUFFER_SIZE = 100 * MEGA_BYTE;

    public static List<FileDifference> compareDirs(Path dir1, Path dir2, ExecutorService executorService) {
        var firstDirChecksumF = computeForFilesInDirectory(dir1, executorService);
        return null;
    }

    private static List<Future<Pair<Path, String>>> computeForFilesInDirectory(Path root, ExecutorService executorService) {
        getAllRegularFiles(root)
                .stream()
                .map(path -> executorService.submit(() -> {
                    byte[] bytes = DirectoryComparator.computeFileMD5(path);
                    return Pair.of(root.relativize(path), Hex.encodeHexString(bytes));
                }))
                .collect(Collectors.toList());
        return null;
    }

    private static List<Path> getAllRegularFiles(Path root) {
        try {
            return Files.walk(root)
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Exception during gathering files from dir: " + root, e);
        }
    }

    private static byte[] computeFileMD5(Path path) {
        try {
            var md = MessageDigest.getInstance("MD5");
            try (InputStream inputStream = Files.newInputStream(path)) {
                var buffer = new byte[BUFFER_SIZE];
                var read = 0;
                while ((read = inputStream.read(buffer)) != -1) {
                    md.update(buffer, 0, read);
                }
            }
            return md.digest();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
