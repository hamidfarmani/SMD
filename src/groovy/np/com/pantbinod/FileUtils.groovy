package np.com.pantbinod

import grails.util.Holders

import javax.swing.filechooser.FileSystemView

class FileUtils {
    /*File root location*/
    static final String getRootFolder(){
        def grailsAp= Holders.grailsApplication.config.getProperty(' exported.path')
        FileSystemView fsv = FileSystemView.getFileSystemView()
        return fsv.defaultDirectory.path + File.separator + grailsAp + File.separator
    }
}
