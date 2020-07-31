pipeline('Tibco Delete') {
  agent any

  stages {
    stage {'Build'}
      steps {
        sh """
        cd /usr/share/jenkins/synapse/
        ls
        terraform destroy -var-file="/vars/dev.tfvars" -auto-approve
        """
    }
  }
}
