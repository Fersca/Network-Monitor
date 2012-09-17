dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop','update'
            url = "jdbc:h2:mem:devDb"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:devDb"
        }
    }
    production {

		dataSource {
			username = "pse"
			//password = "fersca13"
			password = "fersca"
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:mysql://ec2-174-129-96-72.compute-1.amazonaws.com/pse" //"jdbc:hsqldb:mem:devDB"
			driverClassName = "org.gjt.mm.mysql.Driver"
			properties {
				maxActive = 50
				maxIdle = 25
				minIdle = 5
				initialSize = 5
				minEvictableIdleTimeMillis = 60000
				timeBetweenEvictionRunsMillis = 60000
				maxWait = 10000
				validationQuery = "select 1 from dual"
			}
		}
		
		
    }
}
