job('Build Terraform') {
    
    parameters {
        stringParam('REPO', 'https://github.com/Tirano2150/aws-sample-tf', 'The branch where Terraform is stored')
        stringParam('BRANCH', 'master', 'The branch (used for testing)')
    }

  scm {
    git {
      remote {
        github ('${REPO}')
      }
      branches('${BRANCH}', 'master')
    }
  }

  steps {
    script {
        terraform init
    }
  }
}