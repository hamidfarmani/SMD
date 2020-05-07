package np.com.pantbinod

import org.springframework.util.FileCopyUtils
import org.springframework.web.multipart.MultipartFile
import java.text.SimpleDateFormat

class UploadDumpController {

    UploadDumpService uploadDumpService

    //render upload dump page
    def index() {
        def isDBError = false
        SQLShellCommandService shellCommandService = new SQLShellCommandService(uploadDumpService.getUsername(), uploadDumpService.getPassword())
        List<String> databaseList = shellCommandService.showDatabases()
        if (databaseList.contains(ErrorEnum.ERROR_OCCURED_WHILE_INSIDE_SHELL.toString())) {
            isDBError = true;
            databaseList.remove(ErrorEnum.ERROR_OCCURED_WHILE_INSIDE_SHELL.toString())
        }
        def domains = uploadDumpService.getTablesOfDB()
        [isDBError: isDBError,allDomains:domains, databaseList: databaseList, message: params.message, flash: params.flash]
    }

    def uploadOnFly(){
        MultipartFile file = request.getFile('dumpsql')
        String line = null
        List<String> tableNames = new ArrayList<>()
        BufferedReader br = new BufferedReader(new InputStreamReader(file.inputStream, "UTF-8"))
        while ((line = br.readLine()) != null) {
            if(line.contains("CREATE TABLE `")) {
                tableNames.add(line.substring(line.indexOf(" `")+2,line.indexOf("` ")))
            }
        }
        render(view: 'index', model: [allTableNames:tableNames, dumpsql: file])
    }

    //upload new sql dump
    def uploadDump() {

        SQLShellCommandService shellCommandService = new SQLShellCommandService(uploadDumpService.getUsername(), uploadDumpService.getPassword())
        MultipartFile file = request.getFile('dumpsql')
        def isDBError = false, flash = false
        def message, importedData,databaseList

        try {
            def filePath = "/Users/kemia/importsql/" + UUID.randomUUID().toString() + ".sql";
            FileCopyUtils.copy(file.getBytes(), new File(filePath));
            String dbname = params.dbname
            importedData = shellCommandService.importDatabase(dbname, filePath)
           databaseList = shellCommandService.showDatabases()

            if (importedData.contains(ErrorEnum.ERROR_OCCURED_WHILE_INSIDE_SHELL.toString())) {
                isDBError = true;
                importedData.remove(ErrorEnum.ERROR_OCCURED_WHILE_INSIDE_SHELL.toString())
                databaseList= importedData
                message = "Oos something went wrong";
                flash = false
            }else {
               flash=true
               message = "Sucessfully inserted";
            }

        } catch (Exception e) {
            message = "Oos something went wrong";
            flash = false
        }

        render(view: 'index', model: [flash: flash, message: message, isDBError: isDBError,
                                      databaseList: databaseList
        ])
    }

    //show list of tables of specific databases in viewTable.gsp file
    def viewTables() {

        boolean isDBError = false
//        SQLShellCommandService shellCommandService = new SQLShellCommandService(uploadDumpService.getUsername(), uploadDumpService.getPassword())
        List<String> tables = uploadDumpService.getTablesOfDB(params.name)//shellCommandService.showTables(params.name)
        if (tables.contains(ErrorEnum.ERROR_OCCURED_WHILE_INSIDE_SHELL.toString())) {
            isDBError = true
            tables.remove(ErrorEnum.ERROR_OCCURED_WHILE_INSIDE_SHELL.toString())
        }
        [tableList: tables, isDBError: isDBError, databasename: params.name, message: params.message, flash: params.flash]
    }

    //show list of tables of specific databases in viewDatabase.gsp file

    def viewDatabase() {
        boolean isDBError = false
//        SQLShellCommandService shellCommandService = new SQLShellCommandService(uploadDumpService.getUsername(), uploadDumpService.getPassword())
        List<String> databaseList = uploadDumpService.getAllDatabases()// shellCommandService.showDatabases()
        println databaseList
        if (databaseList.contains(ErrorEnum.ERROR_OCCURED_WHILE_INSIDE_SHELL.toString())) {
            isDBError = true;
            databaseList.remove(ErrorEnum.ERROR_OCCURED_WHILE_INSIDE_SHELL.toString())
        }

        [databaseList: databaseList, isDBError: isDBError, message: params.message, flash: params.flash]

    }

    String getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        return simpleDateFormat.format(new Date()).replaceAll(" ", "-")
    }

    //exorts select tables
    def exportTables() {

        SQLShellCommandService shellCommandService = new SQLShellCommandService(uploadDumpService.getUsername(), uploadDumpService.getPassword())
        def tableList = params.list('tableList')

        if (!tableList) {
            redirect(action: 'viewTables', params: [name: params.currentDatabase, message: "Please select at least one tables", flash: false])
            return
        }

        String fileDirPath = "",tablename = "", message
        def databaseList;

        boolean isFirst = true, flash = false, isDBError= false

        for (String s : tableList) {
            s = s.replaceAll("\n", "")
                    .replaceAll("\r", "").trim()

            if (isFirst) {
                tablename += s;
                fileDirPath += s;
                isFirst = false;
            } else {
                tablename += " " + s;
                fileDirPath += "_" + s;

            }
        }

        if(tableList.size()>3){
            fileDirPath= tableList.first()+"-"+tableList.last()
        }

        def tempdb = params.currentDatabase as String
        def db = tempdb.replaceAll("\n", "").replaceAll("\r", "").trim()

        try {
            def fileDir = "/Users/kemia/export/" + File.separator + fileDirPath + getCurrentDate();

            File exportFileDir = new File(fileDir);
            exportFileDir.mkdir();
            def filePath = fileDir + File.separator + getCurrentDate() + ".sql";
            def exportTables = shellCommandService.exportDatabases(db, tablename, filePath)
              databaseList = shellCommandService.showDatabases()

            if (exportTables.contains(ErrorEnum.ERROR_OCCURED_WHILE_INSIDE_SHELL.toString())) {
                isDBError = true;
                exportTables.remove(ErrorEnum.ERROR_OCCURED_WHILE_INSIDE_SHELL.toString())
                databaseList= exportTables
            }
            flash = true
            message = "Sucessfully exported";
        } catch (Exception e) {
            flash = false
            message = "Oops something went wrong"
        }
        render(view: 'viewDatabase',model: [databaseList: databaseList, flash: flash, message: message])

    }
}
