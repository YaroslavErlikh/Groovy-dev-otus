name = "open"
description = "Apache Tomcat"

http {
    host = "127.0.0.1"
    port = 80
    secure = false
}

mappings = [
    {
        endpoint = '/'
        method = 'GET'
    },
    {
            endpoint = '/login'
            method = 'POST'
    }
]