

import np.com.pantbinod.DBCredentials

class BootStrap {

    def init = { servletContext ->
        DBCredentials dbCredentials = DBCredentials.findById(1);
        if(!dbCredentials){
            dbCredentials= new DBCredentials(username: "root", password: "Hf123!@#")
            dbCredentials.save(flush: true, failOnError: true)
        }
    }
    def destroy = {
    }
}
