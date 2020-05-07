package np.com.pantbinod

import grails.transaction.Transactional

@Transactional
class UploadDumpService {

    String getUsername(){
        return DBCredentials.findById(1).username
    }

    String getPassword(){
        return  DBCredentials.findById(1).password
    }
}
