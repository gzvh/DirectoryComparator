package pl.przemek.java.advanced.comparator;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.tuple.Pair;
import pl.przemek.java.advanced.comparator.difference.FileDifference;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DirectoryComparator {

    public static final int MEGA_BYTE = 1024 * 1024;
    public static final int BUFFER_SIZE = 100 * MEGA_BYTE;
    public static final int TIMEOUT_PER_RESULT_IN_SECONDS = 60;

    public static List<FileDifference> compareDirs(Path dir1, Path dir2, ExecutorService executorService) throws Exception{
        var firstDirChecksumsF = computeForFilesInDirectory(dir1, executorService);
        var secondDirChecksumsF = computeForFilesInDirectory(dir2, executorService);
        var firstDirChecksums = waitAndCollectToMap(firstDirChecksumsF);
        var secondDirChecksums = waitAndCollectToMap(secondDirChecksumsF);
        
        return findFileDifferences(firstDirChecksums, secondDirChecksums);
    }

    private static List<FileDifference> findFileDifferences(Map<Path, String> firstDirChecksums, Map<Path, String> secondDirChecksums) {
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

    private static Map<Path, String> waitAndCollectToMap(List<Future<Pair<Path, String>>> futures) throws Exception {
        var results = new ArrayList<Pair<Path, String>>();
        for (var future : futures) {
            results.add(future.get(TIMEOUT_PER_RESULT_IN_SECONDS, TimeUnit.SECONDS));
        }
        return results.stream().collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    }
}
