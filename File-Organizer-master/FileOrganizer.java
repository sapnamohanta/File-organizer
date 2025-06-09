// Main Java Program File
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileOrganizer {
    private static final Map<String, String> CATEGORY_MAP = new HashMap<>();
    private static final String LOG_FILE = "log.txt";

    // Mapping extensions to Folder Types
    static {
        CATEGORY_MAP.put("jpg", "Images");
        CATEGORY_MAP.put("png", "Images");
        CATEGORY_MAP.put("gif", "Images");
        CATEGORY_MAP.put("pdf", "Documents");
        CATEGORY_MAP.put("pptx", "Documents");
        CATEGORY_MAP.put("xlsx", "Documents");
        CATEGORY_MAP.put("xls", "Documents");
        CATEGORY_MAP.put("csv", "Documents");
        CATEGORY_MAP.put("docx", "Documents");
        CATEGORY_MAP.put("txt", "Documents");
        CATEGORY_MAP.put("mp4", "Videos");
        CATEGORY_MAP.put("avi", "Videos");
    }

    // main function
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the folder path to organize or type 'current' to use the current directory:");
        String inputPath = scanner.nextLine();
        String folderPath = inputPath.equalsIgnoreCase("current") ? System.getProperty("user.dir") : inputPath;
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid folder path.");
            scanner.close();
            return;
        }

        System.out.println("Start organizing? (y/n):");
        if (!scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.println("Operation canceled.");
            scanner.close();
            return;
        }

        organizeFiles(folder);
        System.out.println("File organization completed.");
        scanner.close();
    }

    private static void organizeFiles(File folder) {
        File[] files = folder.listFiles();
        if (files == null) return;

        Map<File, File> movedFiles = new HashMap<>();
        
        for (File file : files) {
            if (file.isFile()) {
                String extension = getFileExtension(file);
                String category = CATEGORY_MAP.getOrDefault(extension, extension);
                
                if (!confirmMove(file, category)) continue;
                
                File destination = moveFileToSubfolder(file, folder, category);
                if (destination != null) {
                    movedFiles.put(file, destination);
                    logFileMove(file, destination);
                }
            }
        }
    }

    private static boolean confirmMove(File file, String category) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Move '" + file.getName() + "' to '" + category + "'? (y/n):");
        return scanner.nextLine().equalsIgnoreCase("y");
    }

    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndex = name.lastIndexOf('.');
        return (lastIndex == -1) ? "others" : name.substring(lastIndex + 1);
    }

    private static File moveFileToSubfolder(File file, File parentFolder, String category) {
        File subfolder = new File(parentFolder, category);
        if (!subfolder.exists()) {
            subfolder.mkdir();
        }
        
        Path sourcePath = file.toPath();
        Path destinationPath = new File(subfolder, file.getName()).toPath();

        try {
            if (Files.exists(destinationPath)) {
                destinationPath = handleDuplicateFile(destinationPath);
            }
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Moved: " + file.getName() + " -> " + subfolder.getName());
            return destinationPath.toFile();
        } catch (IOException e) {
            System.out.println("Failed to move: " + file.getName());
            e.printStackTrace();
            return null;
        }
    }

    private static Path handleDuplicateFile(Path destinationPath) {
        String newFileName = destinationPath.getFileName().toString();
        String baseName = newFileName.contains(".") ? newFileName.substring(0, newFileName.lastIndexOf('.')) : newFileName;
        String extension = newFileName.contains(".") ? newFileName.substring(newFileName.lastIndexOf('.')) : "";
        int counter = 1;

        while (Files.exists(destinationPath)) {
            destinationPath = destinationPath.resolveSibling(baseName + "_" + counter + extension);
            counter++;
        }
        return destinationPath;
    }

    private static void logFileMove(File source, File destination) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write("Moved: " + source.getName() + " -> " + destination.getParentFile().getName());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Failed to log move: " + source.getName());
        }
    }
}
