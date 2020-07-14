job('Seed All') {
    
    parameters {
        stringParam('BRANCH', 'master', 'The branch (used for testing)')
    }

      triggers {
        cron("H/15 * * * *")
    }

  scm {
    git {
      remote {
        github ('tranquilitybase-io/tb-houston-orchestration')
      }
      branches('${BRANCH}', 'master')
    }
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