package np.com.pantbinod;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShellUtils {
    public static List<String> run(List<String> command) {
        List<String> errorList = new ArrayList<>();
        try {
//            ProcessBuilder processBuilder = new ProcessBuilder();
//            processBuilder.command("cmd.exe", "/c", "cd C://\"Program files\"/MySQL/\"MySQL Server 8.0\"/bin/mysql -uroot -pHf123!@# -e \"show databases;\"");
//            Process p = processBuilder.start();
            Process p = Runtime.getRuntime().exec("cmd /c C://\"Program files\"/MySQL/\"MySQL Server 8.0\"/bin/mysql -u root -pHf123!@# -e \"show databases;\"");


            System.out.println(p);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

//            ProcessBuilder coolBuilder = new ProcessBuilder(command);
//            System.out.println(command);
//            Process process = coolBuilder.start();
            int errCode = p.waitFor();
            if (errCode != 0) {
                System.out.println("errCode = " + errCode);
                errorList.add(ErrorEnum.ERROR_OCCURED_WHILE_INSIDE_SHELL.toString());
            }

            for (String s : output(p.getErrorStream())) {
                if (!s.contains("Warning")) {
                    errorList.add(s);
                    return errorList;
                }
            }

            return output(p.getInputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static List<String> output(InputStream inputStream) throws IOException {
        List<String> sb = new ArrayList<String>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.add(line + System.getProperty("line.separator"));
            }
        } finally {
            br.close();
        }
        return sb;
    }
}
