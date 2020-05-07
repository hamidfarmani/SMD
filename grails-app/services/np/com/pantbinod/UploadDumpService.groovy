package np.com.pantbinod

import grails.transaction.Transactional

@Transactional
class UploadDumpService {

    def sessionFactory

    String getUsername(){
        return DBCredentials.findById(1).username
    }

    String getPassword(){
        return  DBCredentials.findById(1).password
    }

    def getTablesOfDB(String dbName){

        def currentSession = sessionFactory.currentSession
        def q = "SELECT table_name FROM information_schema.tables WHERE table_schema = '${dbName}';"
        def data = currentSession.createSQLQuery(q)
        println q
        List<Object[]> result = data.list()
        return result
    }


    def getAllDatabases(){

        def currentSession = sessionFactory.currentSession
        def q = "show databases"
        def data = currentSession.createSQLQuery(q)
        println q
        List<Object[]> result = data.list()
        return result
    }
}
