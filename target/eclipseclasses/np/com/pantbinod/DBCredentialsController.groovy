package np.com.pantbinod



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional
class DBCredentialsController {



    def index(){

        def credentials = DBCredentials.findById(1)
        println "params = $params"
        [credentials: credentials, message: params.message, flash: params.flash]

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
