job('Seed All') {
    
      triggers {
        cron("H/15 * * * *")
    }

  scm {
    git ('https://github.com/tranquilitybase-io/tb-houston-orchestration.git')
  }

  steps {
    dsl {
      external('jenkins/JCasC/jobs/*.groovy')  
      // default behavior
      // removeAction('IGNORE')      
      removeAction('DELETE')
    }
  }
}