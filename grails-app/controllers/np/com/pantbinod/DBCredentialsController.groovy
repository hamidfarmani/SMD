package np.com.pantbinod


import grails.transaction.Transactional

@Transactional
class DBCredentialsController {

    def uploadDumpService

    def index(){

        def credentials = DBCredentials.findById(1)
        println "params = $params"
        def domains = uploadDumpService.getTablesOfDB()
        [credentials: credentials,allDomains:domains, message: params.message, flash: params.flash]

    }

    def update(){
        def message
        boolean flash= false
        try {
            def credentials = DBCredentials.findById(1)
            println "params = $params"
            credentials.properties = params
            credentials.save(flush: true, failOnError: true)
            message="Sucessfully updated."
            flash= true

        }catch(Exception e){
            message="Oops something went wrong"
            flash=false
        }
      redirect(action: 'index', params: [message: message, flash: flash])
    }
}
