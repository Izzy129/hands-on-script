import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.ArrayList;

public class Script {
    public static void main(String[] args) throws IOException {
        String directoryPath = "."; // specify your directory path here

        ArrayList<String> output = new ArrayList<>();

        // walk through the directory and get all the file names
        try (Stream<Path> walk = Files.walk(Paths.get(directoryPath))) {
            List<String> result = walk
                    .filter(Files::isRegularFile) // checks if is a file
                    .map(Object::toString) // get the file name
                    .filter(f -> f.endsWith(".dat")) // checks if is a .dat file
                    .collect(Collectors.toList()); // collect file names to list

            output.addAll(result); // add the file names to the ArrayList
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < output.size(); i++) {

            output.set(i, output.get(i).replaceAll("(\\.\\\\)|(.dat)", ""));
            // System.out.println(output.get(i));
            try {
                Files.createFile(Paths.get("src\\" + output.get(i) + ".java"));
                System.out.println("File " + output.get(i) + ".java created successfully\n");
            } catch (IOException e) {
                System.out.println("An error occurred while making file " + output.get(i) + ".java");
                System.out.println(e + "\n");
            }

        }

    }

}