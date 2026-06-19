package com.danikula.videocache.file;

import com.danikula.videocache.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * {@link DiskUsage} that uses LRU (Least Recently Used) strategy to trim cache.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 * <p>
 * Modified by LKY-Lockee on 2026/6/22
 */
public abstract class LruDiskUsage implements DiskUsage {

    @Override
    public void touch(File file) throws IOException {
        Files.setLastModifiedNow(file);
        File parent = file.getParentFile();
        if (parent == null) {
            throw new IOException("File " + file + " has no parent directory");
        }
        List<File> files = Files.getLruListFiles(parent);
        trim(files);
    }

    protected abstract boolean accept(File file, long totalSize, int totalCount);

    private void trim(List<File> files) {
        long totalSize = countTotalSize(files);
        int totalCount = files.size();
        for (File file : files) {
            boolean accepted = accept(file, totalSize, totalCount);
            if (!accepted) {
                long fileSize = file.length();
                boolean deleted = file.delete();
                if (deleted) {
                    totalCount--;
                    totalSize -= fileSize;
                    Logger.info("Cache file " + file + " is deleted because it exceeds cache limit");
                } else {
                    Logger.error("Error deleting file " + file + " for trimming cache");
                }
            }
        }
    }

    private long countTotalSize(List<File> files) {
        long totalSize = 0;
        for (File file : files) {
            totalSize += file.length();
        }
        return totalSize;
    }
}
