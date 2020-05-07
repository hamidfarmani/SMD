package np.com.pantbinod;

class SQLShellCommandService {


    String username;
    String password;

    public SQLShellCommandService(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public List<String> showDatabases() {
        List<String> cmd = new ArrayList<>();
        cmd.add("bash");
        cmd.add("-c");
        cmd.add("mysql -u"+ username +" -p"+  password+" -e 'show databases;'");
        return refactorOutPut(ShellUtils.run(cmd),"Database","")
    }

     List<String> refactorOutPut(List<String> stringList, String prefix, String suffix) {
        List<String> tableNameList = new ArrayList<>();
        for (String table : stringList) {
            table = table.replaceAll("\n", "").replaceAll("\r", "").trim();
            if (!table.equalsIgnoreCase(prefix + suffix)) {
                tableNameList.add(table);
            }
        }

        return tableNameList;
    }

    public List<String> showTables(String database) {
        List<String> cmd = new ArrayList<>();
        cmd.add("bash");
        cmd.add("-c");
        cmd.add("mysql -u" + username + " -p" + password + " -e 'use " + database + "; show tables;'");
        return refactorOutPut(ShellUtils.run(cmd), "Tables_in_", database)
    }



    public List<String> exportDatabases(String database, String tables, String dumpLocation) {
        List<String> cmd = new ArrayList<>();
        cmd.add("bash");
        cmd.add("-c");
        cmd.add("mysqldump -u" + username + " -p" + password + " " + database + " " + tables + " --skip-add-drop-table --insert-ignore --result-file=" + dumpLocation);
        return ShellUtils.run(cmd);
    }

    public List<String> importDatabase(String database, String filePath) {

        List<String> cmd = new ArrayList<>();
        cmd.add("bash");
        cmd.add("-c");
        cmd.add("mysql -u" + username + " -p" + password + " " + database + " --force < " + filePath);
        println "cmd = $cmd"
        return ShellUtils.run(cmd);
    }

}
