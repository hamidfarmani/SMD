package np.com.pantbinod

class DBCredentials {
    String username
    String password
    static constraints = {
        username()
        password()
    }
    static mapping = {
        version false
    }
}
