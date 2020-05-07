

import np.com.pantbinod.DBCredentials

class BootStrap {

    def init = { servletContext ->
        DBCredentials dbCredentials = DBCredentials.findById(1);
        if(!dbCredentials){
            dbCredentials= new DBCredentials(username: "root")
            dbCredentials.save(flush: true, failOnError: true)
        }
    }
    def destroy = {
    }
}
