package org.lnu.timetable.service.image;

import lombok.Data;
import org.lnu.timetable.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

@Service
public class FileStorageService {

    private static final int BUFFER_SIZE = 4096;

    private final Scheduler defaultScheduler;

    private final Path fileStoragePath;

    public FileStorageService(Scheduler defaultScheduler,
                              @Value("${file.storage.path}") String fileStoragePathStr) {

        this.defaultScheduler = defaultScheduler;
        fileStoragePath = Path.of(fileStoragePathStr);
    }

    public Mono<Void> saveFile(String fileName, FilePart filePart) {
        Path filePath = getFilePath(fileName);
        return filePart.transferTo(filePath);
    }

    public Flux<DataBuffer> readFile(String fileName, NotFoundException notFoundException) {
        Path filePath = getFilePath(fileName);
        return DataBufferUtils.read(filePath, new DefaultDataBufferFactory(), BUFFER_SIZE)
                .onErrorResume(NoSuchFileException.class, e -> Mono.error(notFoundException));
    }

    public Mono<Boolean> checkIfFileExists(String fileName) {
        return Mono.fromCallable(() -> checkIfExists(fileName));
    }

    public Mono<Void> checkIfFilesExists(List<FileProcessor> fileProcessors) {
        return Mono.fromRunnable(() -> {
            fileProcessors.forEach(fileProcessor -> {
                if (checkIfExists(fileProcessor.getFileName())) {
                    fileProcessor.process();
                }
            });
        });
    }

    public Mono<Boolean> removeFileIfExists(String fileName) {
        return Mono.fromCallable(() -> {
            try {
                Path filePath = getFilePath(fileName);
                return Files.deleteIfExists(filePath);
            } catch (IOException e) {
                throw new UncheckedIOException("An error occurred while trying to remove file " + fileName, e);
            }
        }).publishOn(defaultScheduler);
    }

    private boolean checkIfExists(String fileName) {
        Path filePath = getFilePath(fileName);
        return Files.exists(filePath);
    }

    private Path getFilePath(String fileName) {
        return fileStoragePath.resolve(fileName);
    }

    @Data
    public static abstract class FileProcessor {
        private final String fileName;

        public abstract void process();
    }
}
