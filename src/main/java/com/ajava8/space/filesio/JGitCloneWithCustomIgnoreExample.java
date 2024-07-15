package com.ajava8.space.filesio;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

public class JGitCloneWithCustomIgnoreExample {
    public static void main(String[] args) {
        try {
            // Local directory path where you want to clone the repository
            File localPath = new File("/path/to/local/directory");

            // Git repository URL to clone
            String remoteUrl = "https://github.com/example/repository.git";

            // Clone repository
            CloneCommand cloneCommand = Git.cloneRepository()
                    .setURI(remoteUrl)
                    .setDirectory(localPath)
                    .setProgressMonitor(new SimpleProgressMonitor())
                    .setCloneAllBranches(true)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider("username", "password"));

            // Perform the clone operation
            Git git = cloneCommand.call();

            // Optionally perform custom logic to ignore specific files or directories
            // For example, delete .git directory after cloning
            deleteGitDirectory(localPath);

            System.out.println("Repository cloned successfully to: " + localPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete .git directory after cloning
    private static void deleteGitDirectory(File localPath) throws IOException {
        File gitDir = new File(localPath, ".git");
        if (gitDir.exists()) {
            deleteDirectory(gitDir);
        }
    }

    // Method to recursively delete a directory
    private static void deleteDirectory(File directory) throws IOException {
        File[] contents = directory.listFiles();
        if (contents != null) {
            for (File file : contents) {
                if (!file.delete()) {
                    throw new IOException("Failed to delete file: " + file);
                }
            }
        }
        if (!directory.delete()) {
            throw new IOException("Failed to delete directory: " + directory);
        }
    }

    // Progress monitor implementation (optional)
    private static class SimpleProgressMonitor implements org.eclipse.jgit.lib.ProgressMonitor {
        @Override
        public void start(int totalTasks) {
            System.out.println("Starting clone...");
        }

        @Override
        public void beginTask(String title, int totalWork) {
            System.out.println("Task: " + title);
        }

        @Override
        public void update(int completed) {
            // Update progress if needed
        }

        @Override
        public void endTask() {
            System.out.println("Task finished.");
        }

        @Override
        public boolean isCancelled() {
            return false;  // Implement cancellation logic if needed
        }

        @Override
        public void showDuration(boolean b) {

        }
    }
}